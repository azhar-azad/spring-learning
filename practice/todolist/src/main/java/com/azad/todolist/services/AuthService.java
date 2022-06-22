package com.azad.todolist.services;

import com.azad.todolist.models.Roles;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.entities.AppUserEntity;
import com.azad.todolist.repos.AppUserRepo;
import com.azad.todolist.security.JWTUtil;
import com.azad.todolist.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    private final AppUserRepo appUserRepo;

    private final AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AppUserRepo appUserRepo, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUserDto getRegisteredAppUser(AppUserDto appUserDto) {

        // Encoding password using bcrypt
        String encodedPass = passwordEncoder.encode(appUserDto.getPassword());

        // Setting the encoded password
        appUserDto.setPassword(encodedPass);

        if (appUserDto.getRole() == null || !Roles.isValidRole(appUserDto.getRole())) {
            appUserDto.setRole("ROLE_USER");
        }

        appUserDto.setUserId(appUtils.getUserId(appUserDto.getEmail()));

        // Persisting the User Entity to db
        AppUserEntity appUserEntity = appUserRepo.save(modelMapper.map(appUserDto, AppUserEntity.class));

        return modelMapper.map(appUserEntity, AppUserDto.class);
    }

    public void authenticateAppUser(String email, String password) {

        // Creating the authentication token which will contain the credentials for authentication.
        // This token is used as input to the authentication process
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(email, password);

        // Authenticating the login credentials
        authManager.authenticate(authInputToken);
    }
}
