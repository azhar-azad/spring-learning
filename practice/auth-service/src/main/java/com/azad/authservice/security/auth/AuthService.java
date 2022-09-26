package com.azad.authservice.security.auth;

import com.azad.authservice.models.dtos.AppUserDto;
import com.azad.authservice.models.entities.AppUserEntity;
import com.azad.authservice.models.entities.RoleEntity;
import com.azad.authservice.repos.AppUserRepository;
import com.azad.authservice.repos.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(AppUserRepository appUserRepository, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    public AppUserDto registerUser(AppUserDto appUserDto) throws RuntimeException {

        String roleName = appUserDto.getRoleName();

        if (roleName.equalsIgnoreCase("ADMIN")) { // Someone is trying to create an Admin user.
            if (!loggedInUserIsAdmin()) {
                // But he/she is not an Admin
                throw new RuntimeException("Only admins can create a new Admin User");
            }
        }

        String encodedPass = passwordEncoder.encode(appUserDto.getPassword());
        appUserDto.setPassword(encodedPass);

        RoleEntity role = roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Role not found with name: " + roleName));

        AppUserEntity user = modelMapper.map(appUserDto, AppUserEntity.class);
        user.setRole(role);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return modelMapper.map(appUserRepository.save(user), AppUserDto.class);
    }

    public void authenticateUser(String email, String password) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        authenticationManager.authenticate(authenticationToken);
    }

    public AppUserEntity getLoggedInUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return appUserRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found with email: " + email));
    }

    public boolean loggedInUserIsAdmin() {
        AppUserEntity loggedInUser = getLoggedInUser();
        String roleName = loggedInUser.getRole().getRoleName();
        return roleName.equalsIgnoreCase("ADMIN");
    }

}
