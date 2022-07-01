package com.azad.todolist.controllers;

import com.azad.todolist.exceptions.RequestBodyEmptyException;
import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.responses.ApiResponse;
import com.azad.todolist.models.responses.AppUserResponse;
import com.azad.todolist.services.AppUserService;
import com.azad.todolist.services.AuthService;
import com.azad.todolist.utils.AppUtils;
import com.azad.todolist.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/users")
public class AppUserController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    private final AppUserService appUserService;
    private final String resourceName;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
        this.resourceName = "AppUser";
    }

    /**
     * @Name: getUserInfo
     * @Desc: Get the information of the logged-in user.
     * @Route: http://localhost:8080/api/users/info
     * @Method: GET
     * @Authenticated: YES
     * @Authorized: NO (Any logged-in user can view his/her info)
     * */
    @GetMapping("/info")
    public ResponseEntity<ApiResponse> getUserInfo() {

        appUtils.printControllerMethodInfo("GET", "/api/users/info", "createAppUser",
                false, "ADMIN, USER");

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserDto appUserDto = appUserService.getByEmail(email);

        return new ResponseEntity<>(new ApiResponse(true, "User Info Fetched",
                Collections.singletonList(modelMapper.map(appUserDto, AppUserResponse.class))),
                HttpStatus.OK);
    }

    /**
     * @Name: updateUserInfo
     * @Desc: Update the logged-in user's info.
     * @Route: http://localhost:8080/api/users/info
     * @Method: PUT
     * @Authenticated: YES
     * @Authorized: NO (Any logged-in user can view his/her info)
     * */
    @PutMapping("/info")
    public ResponseEntity<ApiResponse> updateUserInfo(@RequestBody AppUser updatedRequest) {

        appUtils.printControllerMethodInfo("PUT", "/api/users/info", "updateUserInfo",
                false, "ADMIN, USER");

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserDto appUserDto = appUserService.getByEmail(email);

        appUserDto = appUserService.updateByEntityId(appUserDto.getId(), modelMapper.map(updatedRequest, AppUserDto.class));

        return new ResponseEntity<>(
                new ApiResponse(true, "Updated User",
                        Collections.singletonList(modelMapper.map(appUserDto, AppUserResponse.class))),
                HttpStatus.OK);
    }

    /**
     * @Name: deleteUserInfo
     * @Desc: Delete the logged-in user's info.
     * @Route: http://localhost:8080/api/users/info
     * @Method: DELETE
     * @Authenticated: YES
     * @Authorized: NO (Any logged-in user can view his/her info)
     * */
    @DeleteMapping("/info")
    public ResponseEntity<ApiResponse> deleteUserInfo() {

        appUtils.printControllerMethodInfo("DELETE", "/api/users/info", "deleteUserInfo",
                false, "ADMIN, USER");

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AppUserDto appUserDto = appUserService.getByEmail(email);

        appUserService.deleteByEntityId(appUserDto.getId());

        return ResponseEntity.noContent().build();
    }

    /**
     * @Name: createAppUser
     * @Desc: Create a new user.
     * @Route: http://localhost:8080/api/users
     * @Method: POST
     * @Authenticated: YES
     * @Authorized: YES (Only Admins can create a new user with this endpoint)
     * */
    @PostMapping
    public ResponseEntity<ApiResponse> createAppUser(@Valid @RequestBody AppUser appUserRequest) {

        appUtils.printControllerMethodInfo("POST", "/api/users", "createAppUser", false, "ADMIN");

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        AppUserDto savedAppUserDto = appUserService.create(modelMapper.map(appUserRequest, AppUserDto.class));

        return new ResponseEntity<>(new ApiResponse(true, "User Created",
                Collections.singletonList(modelMapper.map(savedAppUserDto, AppUserResponse.class))),
                HttpStatus.CREATED);
    }

    /**
     * @Name: getAllUsers
     * @Desc: Get the list of all users with pagination, sorting and ordering support
     * @Route: http://localhost:8080/api/users?page=1&limit=5&sort=firstName&order=desc
     * @Method: GET
     * @Authenticated: YES
     * @Authorized: YES (Only Admins can create a new user with this endpoint)
     * */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        appUtils.printControllerMethodInfo("GET", "/api/users", "getAllUsers", false, "ADMIN");

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        if (page > 0) page--;

        PagingAndSorting ps = new PagingAndSorting(page, limit, sort, order);
        List<AppUserDto> appUserDtos = appUserService.getAll(ps);

        if (appUserDtos == null || appUserDtos.size() == 0) {
            return new ResponseEntity<>(appUtils.getApiResponse(true, "No User", null), HttpStatus.OK);
        }

        List<AppUserResponse> appUserResponses = appUserDtos.stream()
                .map(appUserDto -> modelMapper.map(appUserDto, AppUserResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ApiResponse(true, "All Users", appUserResponses),
                HttpStatus.OK);
    }

    /**
     * @Name: searchUser
     * @Desc: Search user with the properties (firstName, lastName, email, role)
     * @Route: http://localhost:8080/api/users/search?key=firstName&value=Azhar
     * @Method: GET
     * @Authenticated: YES
     * @Authorized: YES (Only Admins can search for a user by properties)
     * */
    @GetMapping(path = "/search")
    public ResponseEntity<ApiResponse> searchUser(
            @Valid @RequestParam(value = "key", defaultValue = "") String key,
            @Valid @RequestParam(value = "value", defaultValue = "") String value) {

        appUtils.printControllerMethodInfo("GET", "/api/users/search", "searchUser",
                false, "ADMIN");

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        List<AppUserDto> appUserDtos = appUserService.getSearchResult(key, value);

        if (appUserDtos == null || appUserDtos.size() == 0) {
            return new ResponseEntity<>(appUtils.getApiResponse(true, "No User Found", null), HttpStatus.OK);
        }

        List<AppUserResponse> appUserResponses = appUserDtos.stream()
                .map(appUserDto -> modelMapper.map(appUserDto, AppUserResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ApiResponse(true, "Search Result", appUserResponses),
                HttpStatus.OK);
    }

    /**
     * @Name: getUserByUserId
     * @Desc: Get user by entity id
     * @Route: http://localhost:8080/api/users/{id}
     * @Method: GET
     * @Authenticated: YES
     * @Authorized: YES (Only Admins can search for a user by his/her userId)
     * */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getUserById(@Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("GET", "/api/users/"+id, "getUserById",
                false, "ADMIN");

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        AppUserDto appUserDto = appUserService.getByEntityId(id);

        return new ResponseEntity<>(
                new ApiResponse(true, "Fetch User",
                        Collections.singletonList(modelMapper.map(appUserDto, AppUserResponse.class))),
                HttpStatus.OK);
    }

    /**
     * @Name: updateUserById
     * @Desc: Update user by entity id
     * @Route: http://localhost:8080/api/users/{id}
     * @Method: PUT
     * @Authenticated: YES
     * @Authorized: YES (Only Admins and the owner can update)
     * */
    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateUserById(
            @Valid @PathVariable Long id,
            @RequestBody AppUser updateUserRequest) {

        appUtils.printControllerMethodInfo("PUT", "/api/users/"+id, "updateUserById",
                false, "ADMIN");

        if (updateUserRequest == null)
            throw new RequestBodyEmptyException("Update request body is empty");

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        AppUserDto appUserDto = appUserService.updateByEntityId(id, modelMapper.map(updateUserRequest, AppUserDto.class));

        return new ResponseEntity<>(
                new ApiResponse(true, "Updated User",
                        Collections.singletonList(modelMapper.map(appUserDto, AppUserResponse.class))),
                HttpStatus.OK);
    }

    /**
     * @Name: deleteUserById
     * @Desc: Delete user by entity id
     * @Route: http://localhost:8080/api/users/{id}
     * @Method: DELETE
     * @Authenticated: YES
     * @Authorized: YES (Only Admins and the owner can delete)
     * */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable Long id) {

        appUtils.printControllerMethodInfo("DELETE", "/api/users/"+id, "deleteUserById",
                false, "ADMIN");

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        appUserService.deleteByEntityId(id);

        return ResponseEntity.noContent().build();
    }
}
