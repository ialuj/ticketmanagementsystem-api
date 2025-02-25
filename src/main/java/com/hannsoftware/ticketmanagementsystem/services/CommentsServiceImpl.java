package com.hannsoftware.ticketmanagementsystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hannsoftware.ticketmanagementsystem.domain.AuditLog;
import com.hannsoftware.ticketmanagementsystem.domain.Comments;
import com.hannsoftware.ticketmanagementsystem.domain.Ticket;
import com.hannsoftware.ticketmanagementsystem.domain.User;
import com.hannsoftware.ticketmanagementsystem.dto.CommentsDTO;
import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;
import com.hannsoftware.ticketmanagementsystem.repository.AuditLogRepository;
import com.hannsoftware.ticketmanagementsystem.repository.CommentsRepository;
import com.hannsoftware.ticketmanagementsystem.repository.TicketRepository;
import com.hannsoftware.ticketmanagementsystem.repository.UserRepository;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;
import com.hannsoftware.ticketmanagementsystem.utils.DateUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class CommentsServiceImpl implements CommentsService {
	
	@Autowired
	private CommentsRepository commentsRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuditLogRepository auditLogRepository;

	@Override
	public CommentsDTO createComment(Long userId, Long ticketId, CommentsDTO commentsDTO) throws BusinessException {
		if(StringUtils.isBlank(commentsDTO.getComment())) {
			throw new BusinessException("Comment is required!");
		}
		if(ticketId == null) {
			throw new BusinessException("Ticket ID is required!");
		}
		if(userId == null) {
			throw new BusinessException("User ID is required!");
		}
		
		Comments comments = new Comments(commentsDTO);
		
		Optional<User> optUser = userRepository.findById(userId);
		if(optUser.isEmpty()) {
			throw new BusinessException("IT Supporter with ID: "+userId+" was not found!");
		}
		User user = optUser.get();
		comments.setUser(user);
		
		Optional<Ticket> optTicket = ticketRepository.findById(ticketId);
		if(optTicket.isEmpty()) {
			throw new BusinessException("Ticket with ID: "+ticketId+" was not found!");
		}
		Ticket ticket = optTicket.get();
		comments.setTicket(ticket);
		comments.setCreationDate(DateUtils.getCurrentDate());
		
		commentsRepository.save(comments);
		
		AuditLog auditLog = new AuditLog("Ticket Comment", ticket, user);
		auditLog.setCreationDate(DateUtils.getCurrentDate());
		auditLogRepository.save(auditLog);
		
		return new CommentsDTO(comments);
	}

	@Override
	public List<CommentsDTO> getCommentsByUserIdAndTicketId(Long userId, Long ticketId) throws BusinessException {
		List<Comments> commentsList = commentsRepository.findByUserIdAndTicketId(userId, ticketId);
		return convertCommentsToDTOs(commentsList);
	}
	
	private List<CommentsDTO> convertCommentsToDTOs(List<Comments> commentsList) {
		List<CommentsDTO> commentsDTOs = new ArrayList<>(commentsList.size());
		for(Comments comments: commentsList) {
			CommentsDTO commentsDTO = new CommentsDTO(comments);
			UserDTO userDTO = new UserDTO(comments.getUser());
			commentsDTO.setUser(userDTO);
			commentsDTOs.add(commentsDTO);
		}
		return commentsDTOs;
    }

	@Override
	public List<CommentsDTO> getCommentsByTicketId(Long ticketId) throws BusinessException {
		List<Comments> commentsList = commentsRepository.findByTicketId(ticketId);
		return convertCommentsToDTOs(commentsList);
	}

}
