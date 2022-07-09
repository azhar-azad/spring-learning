package com.azad.productmanager;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private final UserRepository appUserRepo;

    private final AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository appUserRepo, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.initRouteAccessMap();
    }

    public User getRegisteredAppUser(User user) {

        // Encoding password using bcrypt
        String encodedPass = passwordEncoder.encode(user.getPassword());

        // Setting the encoded password
        user.setPassword(encodedPass);

//        if (user.getRole() == null || !Roles.isValidRole(appUserDto.getRole())) {
//            appUserDto.setRole("ROLE_USER");
//        }

        // Persisting the User Entity to db
        User appUserEntity = appUserRepo.save(user);

        return appUserEntity;
    }

    public void authenticateAppUser(String username, String password) {

        // Creating the authentication token which will contain the credentials for authentication.
        // This token is used as input to the authentication process
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // Authenticating the login credentials
        authManager.authenticate(authInputToken);
    }

//    public boolean notAuthorizedForThisResource(String resourceName) {
//
//        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String userRole = appUserRepo.findByEmail(email).orElseThrow(
//                () -> new ResourceNotFoundException("AppUser", "email")).getRole();
//
//        if (ROUTE_ACCESS_MAP.containsKey(resourceName)) {
//            List<String> eligibleRoles = ROUTE_ACCESS_MAP.get(resourceName);
//            return !eligibleRoles.contains(userRole);
//        }
//
//        return true;
//    }

    private void initRouteAccessMap() {
//        ROUTE_ACCESS_MAP.put("Auth", new ArrayList<String>() {{
//            add(Roles.ROLE_ADMIN.name());
//            add(Roles.ROLE_USER.name());
//        }});
//        ROUTE_ACCESS_MAP.put("AppUser", new ArrayList<String>() {{
//            add(Roles.ROLE_ADMIN.name());
//        }});
//        ROUTE_ACCESS_MAP.put("Task", new ArrayList<String>() {{
//            add(Roles.ROLE_ADMIN.name());
//            add(Roles.ROLE_USER.name());
//        }});
    }
}
