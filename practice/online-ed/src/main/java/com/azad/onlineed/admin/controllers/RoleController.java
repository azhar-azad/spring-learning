package com.azad.onlineed.admin.controllers;

import com.azad.onlineed.admin.services.RoleService;
import com.azad.onlineed.models.dtos.RoleDto;
import com.azad.onlineed.models.requests.RoleRequest;
import com.azad.onlineed.models.responses.ApiResponse;
import com.azad.onlineed.models.responses.RoleResponse;
import com.azad.onlineed.repos.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/api/admin/roles")
public class RoleController {

    @Autowired
    private ModelMapper modelMapper;

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createRole(@Valid @RequestBody RoleRequest request) {

        if (request.getAuthorityNames() == null) {
            throw new RuntimeException("No authorities passed for this role. Create role failed");
        }

        RoleDto roleDto = roleService.create(modelMapper.map(request, RoleDto.class));
        RoleResponse roleResponse = modelMapper.map(roleDto, RoleResponse.class);

        return new ResponseEntity<>(new ApiResponse(true, "Role Created",
                Collections.singletonMap("roles", Collections.singletonList(roleResponse))),
                HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getRole(@Valid @PathVariable Integer id) {

        RoleDto roleDto = roleService.getById(id);

        return new ResponseEntity<>(new ApiResponse(true, "Role Fetched",
                Collections.singletonMap("roles", Collections.singletonList(modelMapper.map(roleDto, RoleResponse.class)))),
                HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateRole(@Valid @PathVariable Integer id, @RequestBody RoleRequest updatedRequest) {

        RoleDto roleDto = roleService.updateById(id, modelMapper.map(updatedRequest, RoleDto.class));

        return new ResponseEntity<>(new ApiResponse(true, "Role Updated",
                Collections.singletonMap("roles", Collections.singletonList(modelMapper.map(roleDto, RoleResponse.class)))),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRole(@Valid @PathVariable Integer id) {

        roleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
