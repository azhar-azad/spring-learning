package com.azad.onlineed.security.auth;

import com.azad.onlineed.models.dtos.UserDto;
import com.azad.onlineed.models.entities.InstructorEntity;
import com.azad.onlineed.models.entities.StudentEntity;
import com.azad.onlineed.repos.InstructorRepo;
import com.azad.onlineed.repos.RoleRepo;
import com.azad.onlineed.repos.StudentRepo;
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

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private InstructorRepo instructorRepo;

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

        Set<String> rolesFromReq = userDto.getRoleNames();
        rolesFromReq.add("USER"); // by default, USER role is added.

        Set<RoleEntity> rolesFromDb = rolesFromReq.stream()
                .map(roleName -> roleRepo.findByRoleName(roleName.toUpperCase()).orElseThrow(
                        () -> new RuntimeException("Role not found with name: " + roleName)))
                .collect(Collectors.toSet());

        if (rolesFromReq.contains("STUDENT")) {
            StudentEntity studentEntity = modelMapper.map(userDto, StudentEntity.class);
            studentEntity.setEnabled(true);
            studentEntity.setRoles(rolesFromDb);

            return modelMapper.map(studentRepo.save(studentEntity), UserDto.class);
        }

        if (rolesFromReq.contains("INSTRUCTOR")) {
            InstructorEntity instructorEntity = modelMapper.map(userDto, InstructorEntity.class);
            instructorEntity.setEnabled(true);
            instructorEntity.setRoles(rolesFromDb);

            return modelMapper.map(instructorRepo.save(instructorEntity), UserDto.class);
        }

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

    public UserDto getLoggedInUserInfo() {
        return null;
    }
}
