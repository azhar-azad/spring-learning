package com.azad.todolist.services;

import com.azad.todolist.exceptions.ResourceNotFoundException;
import com.azad.todolist.models.Roles;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.entities.AppUserEntity;
import com.azad.todolist.models.responses.ApiResponse;
import com.azad.todolist.repos.AppUserRepo;
import com.azad.todolist.security.JWTUtil;
import com.azad.todolist.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final Map<String, List<String>> ROUTE_ACCESS_MAP = new HashMap<>();

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
        this.initRouteAccessMap();
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

    public boolean notAuthorizedForThisResource(String resourceName) {

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userRole = appUserRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", "email")).getRole();

        if (ROUTE_ACCESS_MAP.containsKey(resourceName)) {
            List<String> eligibleRoles = ROUTE_ACCESS_MAP.get(resourceName);
            return !eligibleRoles.contains(userRole);
        }

        return true;
    }

    private void initRouteAccessMap() {
        ROUTE_ACCESS_MAP.put("Auth", new ArrayList<String>() {{
            add(Roles.ROLE_ADMIN.name());
            add(Roles.ROLE_USER.name());
        }});
        ROUTE_ACCESS_MAP.put("AppUser", new ArrayList<String>() {{
            add(Roles.ROLE_ADMIN.name());
        }});
        ROUTE_ACCESS_MAP.put("Task", new ArrayList<String>() {{
            add(Roles.ROLE_ADMIN.name());
            add(Roles.ROLE_USER.name());
        }});
    }
}
