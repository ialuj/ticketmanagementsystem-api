package com.hannsoftware.ticketmanagementsystem.services;

import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

public interface UserService {
	
	public UserDTO createUser(UserDTO userDTO) throws BusinessException;

	public UserDTO getUserByUsername(String username);

	UserDTO login(String username, String password);

	public void updateStatus(Long userId, String newPassord) throws BusinessException;

}
