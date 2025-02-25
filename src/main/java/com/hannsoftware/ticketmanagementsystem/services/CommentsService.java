package com.hannsoftware.ticketmanagementsystem.services;

import java.util.List;

import com.hannsoftware.ticketmanagementsystem.dto.CommentsDTO;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

public interface CommentsService {

	public CommentsDTO createComment(Long userId, Long ticketId, CommentsDTO commentsDTO) throws BusinessException;
	
	public List<CommentsDTO> getCommentsByTicketId(Long ticketId) throws BusinessException;
	
	public List<CommentsDTO> getCommentsByUserIdAndTicketId(Long userId, Long ticketId) throws BusinessException;
	
}
