package com.azad.online_shop.service;

import com.azad.online_shop.exception.UnauthorizedAccessException;
import com.azad.online_shop.model.contant.RoleType;
import com.azad.online_shop.model.dto.users.LoginRequest;
import com.azad.online_shop.model.dto.users.UserDto;
import com.azad.online_shop.model.entity.RoleEntity;
import com.azad.online_shop.model.entity.UserEntity;
import com.azad.online_shop.repository.RoleRepository;
import com.azad.online_shop.repository.UserRepository;
import com.azad.online_shop.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Service
public class AuthService {

    private ModelMapper modelMapper;

    private SecurityUtils securityUtils;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto registerUser(UserDto dto) {

        if (dto.getRoleName() == null || dto.getRoleName().isEmpty())
            dto.setRoleName(RoleType.USER.name().toUpperCase());

        if (dto.getRoleName().equalsIgnoreCase(RoleType.ADMIN.name())) {
            if (!loggedInUserIsAdmin())
                throw new UnauthorizedAccessException("Only admins can create a new admin user");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        RoleEntity role = roleRepository.findByRoleName(dto.getRoleName().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + dto.getRoleName()));

        UserEntity user = modelMapper.map(dto, UserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setLocked(false);
        user.setExpired(false);
        user.setUserFrom(LocalDate.now());

        UserEntity registeredUser = userRepository.save(user);

        return modelMapper.map(registeredUser, UserDto.class);
    }

    public String authenticateUserAndGetEmail(LoginRequest request) throws RuntimeException {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authToken);

        UserEntity loggedInUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
        loggedInUser.setLastLoginAt(LocalDateTime.now());
        userRepository.save(loggedInUser);

        return loggedInUser.getEmail();
    }

    public void deleteUser() {
        UserEntity loggedInUser = getLoggedInUser();
        userRepository.delete(loggedInUser);
    }

    public void resetPassword(String updatedPassword) {
        UserEntity loggedInUser = getLoggedInUser();

        String encodedPass = passwordEncoder.encode(updatedPassword);
        loggedInUser.setPassword(encodedPass);

        userRepository.save(loggedInUser);
    }

    public UserEntity getLoggedInUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity loggedInUser;
        try {
            loggedInUser = securityUtils.getUserByEmail(email);
        } catch (UsernameNotFoundException e) {
            throw new UnauthorizedAccessException("User not found with email: " + email);
        }
        return loggedInUser;
    }

    public String generateAndGetJwtToken(String email) {
        return securityUtils.generateJwtToken(email);
    }

    public void authInit() {
        BiFunction<RoleType, Integer, RoleEntity> roleCreator = (roleName, id) -> {
            RoleEntity role = new RoleEntity();
            role.setId(id);
            role.setRoleName(roleName.name());
            return roleRepository.save(role);
        };

        RoleEntity adminRole = roleRepository.findByRoleName(RoleType.ADMIN.name())
                .orElse(roleCreator.apply(RoleType.ADMIN, 1));
        roleRepository.findByRoleName(RoleType.USER.name())
                .orElse(roleCreator.apply(RoleType.USER, 2));

        Supplier<UserEntity> superAdminCreator = () -> {
            UserEntity admin = new UserEntity();
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setEmail("superadmin@gmail.com");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setRole(adminRole);
            admin.setUserFrom(LocalDate.now());
            admin.setEnabled(true);
            return userRepository.save(admin);
        };

        userRepository.findByEmail("superadmin@gmail.com").orElseGet(superAdminCreator);
    }

    private boolean loggedInUserIsAdmin() {
        UserEntity loggedInUser;
        try {
            loggedInUser = getLoggedInUser();
        } catch (UnauthorizedAccessException e) {
            return false;
        }
        if (loggedInUser == null)
            return false;
        return loggedInUser.getRole().getRoleName().equalsIgnoreCase(RoleType.ADMIN.name());
    }
}
