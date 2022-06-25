package com.azad.todolist.controllers;

import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.Roles;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.responses.ApiResponse;
import com.azad.todolist.models.responses.AppUserResponse;
import com.azad.todolist.services.AppUserService;
import com.azad.todolist.services.AuthService;
import com.azad.todolist.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/api/users") // both USER and ADMIN can access
public class AppUserController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiResponse apiResponse;

    @Autowired
    private AuthService authService;

    private final AppUserService appUserService;
    private final String routePath;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
        this.routePath = "/api/users";
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse> getUserInfo() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserDto appUserDto = appUserService.getByEmail(email);

        apiResponse = appUtils.getApiResponse(true, "User Info Fetched",
                Collections.singletonList(modelMapper.map(appUserDto, AppUserResponse.class)));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createAppUser(@Valid @RequestBody AppUser appUserRequest) {

        appUtils.printControllerMethodInfo("POST", "/api/users", "createAppUser", false, "ADMIN");

        if (authService.notAuthorizedForThisRoute(routePath)) {
            return new ResponseEntity<>(
                    appUtils.getApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        AppUserDto savedAppUserDto = appUserService.create(modelMapper.map(appUserRequest, AppUserDto.class));

        apiResponse = appUtils.getApiResponse(true, "User Created",
                Collections.singletonList(modelMapper.map(savedAppUserDto, AppUserResponse.class)));

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        appUtils.printControllerMethodInfo("GET", "/api/users", "getAllUsers", false, "ADMIN");

        if (authService.notAuthorizedForThisRoute(routePath)) {
            return new ResponseEntity<>(
                    appUtils.getApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        return null;
    }



    private ResponseEntity<ApiResponse> blockNonAdmins() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserDto appUserDto = appUserService.getByEmail(email);

        if (!appUserDto.getRole().equals(Roles.ROLE_ADMIN.name())) {
            return new ResponseEntity<>(
                    appUtils.getApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }
        return null;
    }
}
