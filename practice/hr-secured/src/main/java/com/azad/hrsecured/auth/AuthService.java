package com.azad.hrsecured.auth;

import com.azad.hrsecured.models.Role;
import com.azad.hrsecured.models.dtos.UserDto;
import com.azad.hrsecured.models.entities.UserEntity;
import com.azad.hrsecured.repos.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final Map<String, List<String>> RESOURCE_ACCESS_MAP = new HashMap<>();

    @Autowired
    private ModelMapper modelMapper;

    private final UserRepo userRepo;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepo userRepo, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.initResourceAccessMap();
    }

    public UserDto getRegisteredUser(UserDto userDto) {

        // Encoding password using bcrypt
        String encodedPass = passwordEncoder.encode(userDto.getPassword());

        // Setting the encoded password to object
        userDto.setPassword(encodedPass);

        if (userDto.getRole() == null || !Role.isValidRole(userDto.getRole())) {
            userDto.setRole("ROLE_USER");
        }

        // Persisting the User Entity to db
        UserEntity userEntity = userRepo.save(modelMapper.map(userDto, UserEntity.class));

        return modelMapper.map(userEntity, UserDto.class);
    }

    public void authenticateUser(String email, String password) {

        // Creating the authentication token which will contain the credentials for authentication.
        // This token is used as input to the authentication process.
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(email, password);

        // Authentication the login credentials
        authManager.authenticate(authInputToken);
    }

    private void initResourceAccessMap() {

    }
}
