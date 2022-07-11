package com.azad.onlineed.security.auth;

import com.azad.onlineed.models.dtos.UserDto;
import com.azad.onlineed.repos.RoleRepo;
import com.azad.onlineed.repos.UserRepo;
import com.azad.onlineed.security.entities.RoleEntity;
import com.azad.onlineed.security.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public AuthService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public UserDto getRegisteredUser(UserDto userDto) {

        String encodedPass = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPass);

        Set<String> rolesFromReq = new HashSet<>();
        if (userDto.getRoleNames() == null)
            rolesFromReq.add("USER"); // by default, USER role is added.

        Set<RoleEntity> rolesFromDb = rolesFromReq.stream()
                .map(roleName -> roleRepo.findByRoleName(roleName.toUpperCase()).orElseThrow(
                        () -> new RuntimeException("Role not found with name: " + roleName)))
                .collect(Collectors.toSet());

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEnabled(true);
        userEntity.setRoles(rolesFromDb);

        return modelMapper.map(userRepo.save(userEntity), UserDto.class);
    }

    public void authenticateUser(String email, String password) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        authenticationManager.authenticate(authenticationToken);
    }
}
