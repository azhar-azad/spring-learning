package com.azad.userservice.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.azad.userservice.entities.Role;
import com.azad.userservice.entities.User;
import com.azad.userservice.services.UserService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class UserResource {

	private final UserService userService;
	
	@GetMapping(path = "/users")
	public ResponseEntity<List<User>> getUsers() {
		
		return ResponseEntity.ok().body(userService.getUsers());
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users").toUriString());
		return ResponseEntity.created(uri).body(userService.createUser(user));
	}
	
	@PostMapping(path = "/roles")
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/roles").toUriString());
		return ResponseEntity.created(uri).body(userService.createRole(role));
	}
	
	@PostMapping(path = "/users/addRole")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
		
}

@Data
class RoleToUserForm {
	private String username;
	private String roleName;
}
