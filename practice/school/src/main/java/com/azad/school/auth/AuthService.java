package com.azad.school.auth;

import com.azad.school.models.Role;
import com.azad.school.models.Student;
import com.azad.school.models.dtos.StudentDto;
import com.azad.school.models.dtos.TeacherDto;
import com.azad.school.models.dtos.UserDto;
import com.azad.school.models.entities.StudentEntity;
import com.azad.school.models.entities.TeacherEntity;
import com.azad.school.models.entities.UserEntity;
import com.azad.school.models.requests.RegistrationRequest;
import com.azad.school.repos.StudentRepo;
import com.azad.school.repos.TeacherRepo;
import com.azad.school.repos.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final Map<String, List<String>> RESOURCE_ACCESS_MAP = new HashMap<>();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.initResourceAccessMap();
    }

    public void authenticateUser(String email, String password) {

        // Creating the authentication token which will contain the credentials for authentication.
        // This token is used as input to the authentication process.
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(email, password);

        // Authentication the login credentials
        authManager.authenticate(authInputToken);
    }

    public String getRegisteredEmail(RegistrationRequest request) {

        // Encoding password using bcrypt
        String encodedPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPass);

        if (request.getRole().equalsIgnoreCase(Role.ROLE_STUDENT.name())) {
            return getRegisteredStudentEmail(request);
        } else if (request.getRole().equalsIgnoreCase(Role.ROLE_TEACHER.name())) {
            return getRegisteredTeacherEmail(request);
        } else {
            return getRegisteredUserEmail(request);
        }
    }

    private String getRegisteredStudentEmail(RegistrationRequest request) {

        StudentEntity studentEntity = modelMapper.map(request, StudentEntity.class);

        return modelMapper
                .map(studentRepo.save(studentEntity), StudentDto.class)
                .getEmail();
    }

    private String getRegisteredTeacherEmail(RegistrationRequest request) {

        TeacherEntity teacherEntity = modelMapper.map(request, TeacherEntity.class);

        return modelMapper
                .map(teacherRepo.save(teacherEntity), TeacherDto.class)
                .getEmail();
    }

    private String getRegisteredUserEmail(RegistrationRequest request) {

        UserEntity userEntity = modelMapper.map(request, UserEntity.class);
        userEntity.setRole(Role.ROLE_USER.name());

        return modelMapper
                .map(userRepo.save(userEntity), UserDto.class)
                .getEmail();
    }

    private void initResourceAccessMap() {

    }
}
