package com.azad.loginrolejwt.auth;

import com.azad.loginrolejwt.entities.Authority;
import com.azad.loginrolejwt.entities.Role;
import com.azad.loginrolejwt.entities.User;
import com.azad.loginrolejwt.repos.AuthorityRepo;
import com.azad.loginrolejwt.repos.RoleRepo;
import com.azad.loginrolejwt.repos.UserRepo;
import com.azad.loginrolejwt.requests.AuthRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public AuthService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public User getRegisteredUser(AuthRequest request) {

        String encodedPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPass);

        Set<String> rolesFromReq = request.getRoles();

        Set<Role> rolesFromDb = rolesFromReq.stream()
                .map(role -> roleRepo.findByName(role.toUpperCase()).orElseThrow(
                        () -> new RuntimeException("Role not found with name: USER")))
                .collect(Collectors.toSet());

        if (rolesFromDb.size() < 1) {
            throw new RuntimeException("Size check. Role not found with name: USER");
        }

        User user = modelMapper.map(request, User.class);
        user.setEnabled(true);
        user.setRoles(rolesFromDb);

        return userRepo.save(user);
    }

    public void authenticateUser(String email, String password) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, password);

        authManager.authenticate(authToken);
    }
}
