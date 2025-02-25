package com.hannsoftware.ticketmanagementsystem.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hannsoftware.ticketmanagementsystem.api.base.BaseController;
import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;
import com.hannsoftware.ticketmanagementsystem.services.UserService;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		try {
			UserDTO dto = userService.login(authRequest.getUsername(), authRequest.getPassword());
			return ResponseEntity.ok(dto);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
