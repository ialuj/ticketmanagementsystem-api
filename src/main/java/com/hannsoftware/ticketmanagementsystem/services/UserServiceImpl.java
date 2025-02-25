package com.hannsoftware.ticketmanagementsystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hannsoftware.ticketmanagementsystem.domain.User;
import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;
import com.hannsoftware.ticketmanagementsystem.repository.UserRepository;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;
import com.hannsoftware.ticketmanagementsystem.utils.Utilities;

import io.micrometer.common.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) throws BusinessException {
		if(StringUtils.isBlank(userDTO.getFullName())) {
			throw new BusinessException("Full Name is required!");
		}
		if(StringUtils.isBlank(userDTO.getUsername())) {
			throw new BusinessException("Username is required!");
		}
		if(StringUtils.isBlank(userDTO.getPassword())) {
			throw new BusinessException("Password is required!");
		}
		if(StringUtils.isBlank(userDTO.getRole())) {
			throw new BusinessException("Role is required!");
		}
		Optional<User> optUser = userRepository.findByUsername(userDTO.getUsername());
		if(optUser.isPresent()) {
			throw new BusinessException("User with Username: " + userDTO.getUsername() + " already exists!");
		}
		User user = new User(userDTO);
		String salt = Utilities.generateSalt();
		user.setSalt(salt);
		user.setPassword(Utilities.encryptPassword(user.getPassword(), salt));
		userRepository.save(user);
		return new UserDTO(user);
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		Optional<User> optUser = userRepository.findByUsername(username);
		if(optUser.isPresent()) return new UserDTO(optUser.get());
		throw new BusinessException("User with username: "+username+" was not found!");
	}
	
	@Override
	public UserDTO login(String username, String password) {
		Optional<User> optUser = userRepository.findByUsername(username);
		if(optUser.isEmpty())
		throw new BusinessException("User with username: "+username+" was not found!");
		User user = optUser.get();
		String salt = user.getSalt();
		String userInputPassword = Utilities.encryptPassword(password, salt);
		String savedPassword = user.getPassword();
		if(userInputPassword.equals(savedPassword)) {
			return new UserDTO(optUser.get());
		}
		throw new BusinessException("Username or Password is incorrect!");
	}

	@Override
	public void updateStatus(Long userId, String newPassord) throws BusinessException {
		if(StringUtils.isBlank(newPassord)) {
			throw new BusinessException("Password is required!");
		}
		Optional<User> optUser = userRepository.findById(userId);
		if(optUser.isEmpty()) {
			throw new BusinessException("User with ID: " + userId + " was not found!");
		}
		User user = optUser.get();
		String salt = user.getSalt();
		String encryptedPassword = Utilities.encryptPassword(newPassord, salt);
		user.setPassword(encryptedPassword);
		userRepository.save(user);
	}
}
