package com.azad.ecommerce.security.auth;

import com.azad.ecommerce.models.dtos.UserDto;
import com.azad.ecommerce.models.entities.RoleEntity;
import com.azad.ecommerce.models.entities.UserEntity;
import com.azad.ecommerce.repos.RoleRepository;
import com.azad.ecommerce.repos.UserRepository;
import com.azad.ecommerce.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

//    @Value("${authentication_base}")
//    private String authenticationBase;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto registerUser(UserDto userDto) throws RuntimeException {

        String roleName = userDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) { // Someone is trying to create an Admin user
            UserEntity loggedInUser = getLoggedInUser(); // An admin user has to be logged-in to make such request.
            if (!loggedInUser.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
                throw new RuntimeException("Only admins can create a new Admin user. '"
                        + loggedInUser.getUsername() + "' is not an Admin.");
            }
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);;

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public void authenticateUser(String usernameOrEmail, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password);

        authManager.authenticate(authenticationToken);
    }

    public UserEntity getLoggedInUser() {
        String usernameOrEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (securityUtils.isUsernameBasedAuth()) {
            return userRepository.findByUsername(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with username: " + usernameOrEmail));
        } else if (securityUtils.isEmailBasedAuth()) {
            return userRepository.findByEmail(usernameOrEmail).orElseThrow(
                    () -> new RuntimeException("User not found with email: " + usernameOrEmail));
        }
        throw new RuntimeException("Unknown Authentication base. Valid authentication bases are USERNAME or EMAIL");
    }

    public boolean loggedInUserIsAdmin() {

        return getLoggedInUser().getRole().getRoleName().equalsIgnoreCase("ADMIN");
    }
}
