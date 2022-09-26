package com.azad.userauthservice.security.auth;

import com.azad.userauthservice.models.dtos.AppUserDto;
import com.azad.userauthservice.models.entities.AppUserEntity;
import com.azad.userauthservice.models.entities.RoleEntity;
import com.azad.userauthservice.repos.AppUserRepository;
import com.azad.userauthservice.repos.RoleRepository;
import com.azad.userauthservice.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(AppUserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public AppUserDto registerUser(AppUserDto userDto) throws RuntimeException {

        String roleName = userDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) { // someone is trying to create an Admin user
            AppUserEntity loggedInUser = getLoggedInUser(); // An admin user has to be logged-in to make such request.
            if (!loggedInUser.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
                throw new RuntimeException("Only admins can create a new Admin user. '"
                        + loggedInUser.getUsername() + "' is not an Admin");
            }
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        AppUserEntity user = modelMapper.map(userDto, AppUserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        AppUserEntity registeredUser = userRepository.save(user);

        return modelMapper.map(registeredUser, AppUserDto.class);
    }

    public void authenticateUser(String usernameOrEmail, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password);

        authenticationManager.authenticate(authenticationToken);
    }

    public AppUserEntity getLoggedInUser() {

         SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        String usernameOrEmail = (String) principal;

        if (securityUtils.isUsernameBasedAuth()) {
            return userRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            return userRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        }
        throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME and EMAIL");
    }
}
