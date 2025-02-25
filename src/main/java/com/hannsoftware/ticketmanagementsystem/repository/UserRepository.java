package com.hannsoftware.ticketmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hannsoftware.ticketmanagementsystem.domain.User;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username) throws BusinessException;

}
