package com.hannsoftware.ticketmanagementsystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hannsoftware.ticketmanagementsystem.api.base.BaseController;
import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;
import com.hannsoftware.ticketmanagementsystem.services.UserService;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		try {
			UserDTO dto = userService.createUser(userDTO);
			return ResponseEntity.ok(dto);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/{userId}/update-password")
	public ResponseEntity<?> updateStatus(@PathVariable Long userId, @PathParam(value = "newPassword") String newPassword) {
		try {
			userService.updateStatus(userId, newPassword);
			return ResponseEntity.ok("Password updated successful!");
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
