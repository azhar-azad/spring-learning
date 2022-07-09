package com.azad.loginrolejwt.admin;

import com.azad.loginrolejwt.entities.Authority;
import com.azad.loginrolejwt.entities.Role;
import com.azad.loginrolejwt.requests.RoleRequest;
import com.azad.loginrolejwt.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(path = "/create/role")
    public ResponseEntity<ApiResponse> createRole(@Valid @RequestBody RoleRequest request) {

        if (request == null) {
            throw new RuntimeException("Invalid request body for Role");
        } else if (request.getAuthorities().size() == 0) {
            throw new RuntimeException("Invalid request body for Role: No authorities are passed");
        }

        Role role = adminService.saveRole(request);

        return new ResponseEntity<>(new ApiResponse(true, "Role Created", Collections.singletonList(role)),
                HttpStatus.CREATED);
    }

    @PostMapping(path = "/create/authority")
    public ResponseEntity<ApiResponse> createAuthority(@Valid @RequestBody Authority request) {

        if (request == null) {
            throw new RuntimeException("Invalid request body for Authority");
        }

        Authority authority = adminService.saveAuthority(request);

        return new ResponseEntity<>(new ApiResponse(true, "Authority Created", Collections.singletonList(authority)),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/user/disable")
    public ResponseEntity<ApiResponse> disableUser() {

        return null;
    }
}
