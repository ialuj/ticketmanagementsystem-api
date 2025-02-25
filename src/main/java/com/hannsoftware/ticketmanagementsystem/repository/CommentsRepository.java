package com.hannsoftware.ticketmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hannsoftware.ticketmanagementsystem.domain.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
	
	public List<Comments> findByTicketId(Long ticketId);

	public List<Comments> findByUserIdAndTicketId(Long userId, Long ticketId);
	
}
