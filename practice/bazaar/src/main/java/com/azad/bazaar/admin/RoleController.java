package com.azad.bazaar.admin;

import com.azad.bazaar.models.Role;
import com.azad.bazaar.models.dtos.RoleDto;
import com.azad.bazaar.models.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

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
    public ResponseEntity<ApiResponse> createRole(@Valid @RequestBody Role request) {

        RoleDto roleDto = roleService.create(modelMapper.map(request, RoleDto.class));

        return new ResponseEntity<>(new ApiResponse(true, "Role Created",
                Collections.singletonMap("roles", Collections.singletonList(modelMapper.map(roleDto, Role.class)))),
                HttpStatus.CREATED);
    }
}
