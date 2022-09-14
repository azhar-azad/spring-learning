package com.azad.imdbapi.security.auth;

import com.azad.imdbapi.dtos.ImdbUserDto;
import com.azad.imdbapi.entities.AuthRoleEntity;
import com.azad.imdbapi.entities.ImdbUserEntity;
import com.azad.imdbapi.repos.AuthRoleRepository;
import com.azad.imdbapi.repos.ImdbUserRepository;
import com.azad.imdbapi.security.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private AuthenticationManager authManager;

    private final ImdbUserRepository userRepository;
    private final AuthRoleRepository roleRepository;

    @Autowired
    public AuthService(ImdbUserRepository userRepository, AuthRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ImdbUserDto registerUser(ImdbUserDto userDto) throws RuntimeException {

        String roleName = userDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) { // Someone is trying to create an Admin user
            ImdbUserEntity loggedInUser = getLoggedInUser(); // An admin user has to be logged-in to make such request.
            if (!loggedInUser.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
                throw new RuntimeException("Only admins can create a new Admin user. '"
                        + loggedInUser.getUsername() + "' is not an Admin.");
            }
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);;

        AuthRoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        ImdbUserEntity user = modelMapper.map(userDto, ImdbUserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        ImdbUserEntity savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, ImdbUserDto.class);
    }

    public void authenticateUser(String usernameOrEmail, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password);

        authManager.authenticate(authenticationToken);
    }

    public ImdbUserEntity getLoggedInUser() {
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
