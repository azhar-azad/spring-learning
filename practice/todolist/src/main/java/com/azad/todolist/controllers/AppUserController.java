package com.azad.todolist.controllers;

import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.Roles;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.entities.AppUserEntity;
import com.azad.todolist.models.responses.ApiResponse;
import com.azad.todolist.models.responses.AppUserResponse;
import com.azad.todolist.repos.AppUserRepo;
import com.azad.todolist.services.AppUserService;
import com.azad.todolist.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "/api/users") // both USER and ADMIN can access
public class AppUserController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    private ApiResponse apiResponse = new ApiResponse();

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/info")
    public ResponseEntity<AppUserResponse> getUserInfo() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserDto appUserDto = appUserService.getByEmail(email);

        return new ResponseEntity<>(modelMapper.map(appUserDto, AppUserResponse.class), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createAppUser(@Valid @RequestBody AppUser appUserRequest) {

        appUtils.printControllerMethodInfo("POST", "/api/users", "createAppUser");

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserDto appUserDto = appUserService.getByEmail(email);

        if (!appUserDto.getRole().equals(Roles.ROLE_ADMIN.name())) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Unauthorized Request");
            return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
        }

        AppUserDto savedAppUserDto = appUserService.create(modelMapper.map(appUserRequest, AppUserDto.class));

        apiResponse.setSuccess(true);
        apiResponse.setMessage("User Created");
        apiResponse.setData(Collections.singletonList(modelMapper.map(savedAppUserDto, AppUserResponse.class)));

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


}
