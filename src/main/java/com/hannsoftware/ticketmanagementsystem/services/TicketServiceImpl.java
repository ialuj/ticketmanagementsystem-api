package com.hannsoftware.ticketmanagementsystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hannsoftware.ticketmanagementsystem.domain.AuditLog;
import com.hannsoftware.ticketmanagementsystem.domain.Comments;
import com.hannsoftware.ticketmanagementsystem.domain.Ticket;
import com.hannsoftware.ticketmanagementsystem.domain.User;
import com.hannsoftware.ticketmanagementsystem.domain.util.Status;
import com.hannsoftware.ticketmanagementsystem.dto.CommentsDTO;
import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;
import com.hannsoftware.ticketmanagementsystem.repository.AuditLogRepository;
import com.hannsoftware.ticketmanagementsystem.repository.CommentsRepository;
import com.hannsoftware.ticketmanagementsystem.repository.TicketRepository;
import com.hannsoftware.ticketmanagementsystem.repository.UserRepository;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;
import com.hannsoftware.ticketmanagementsystem.utils.DateUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuditLogRepository auditLogRepository;
	
	@Autowired
	private CommentsRepository commentsRepository;

	@Override
	public TicketDTO createTicket(Long employeedId, TicketDTO ticketDTO) throws BusinessException {
		if(StringUtils.isBlank(ticketDTO.getTitle())) {
			throw new BusinessException("Title is required!");
		}
		if(StringUtils.isBlank(ticketDTO.getPriority())) {
			throw new BusinessException("Priority is required!");
		}
		if(StringUtils.isBlank(ticketDTO.getCategory())) {
			throw new BusinessException("Category is required!");
		}
		if(employeedId == null) {
			throw new BusinessException("Employee ID is required!");
		}
		Ticket ticket = new Ticket(ticketDTO);
		
		Optional<User> optUser = userRepository.findById(employeedId);
		if(optUser.isEmpty()) {
			throw new BusinessException("Employee with ID: "+employeedId+" was not found!");
		}
		
		User user = optUser.get();
		ticket.setEmployee(user);
		ticket.setCreationDate(DateUtils.getCurrentDate());
		ticketRepository.save(ticket);
		
		AuditLog auditLog = new AuditLog("Ticket Creation", ticket, user);
		auditLog.setCreationDate(DateUtils.getCurrentDate());
		auditLogRepository.save(auditLog);
		
		return new TicketDTO(ticket);
	}
	
	@Override
	public TicketDTO updateTicketStatus(Long ticketId, Long userId, String status) throws BusinessException {
		Optional<Ticket> optTicket = ticketRepository.findById(ticketId);
		if(optTicket.isEmpty()) {
			throw new BusinessException("Ticket with ID: "+ticketId+" was not found!");
		}
		
		Optional<User> optUser = userRepository.findById(userId);
		if(optUser.isEmpty()) {
			throw new BusinessException("User with ID: "+userId+" was not found!");
		}
		
		if(status == null) {
			throw new BusinessException("Status is required!");
		}
		
		Status status2 = Status.toEnum(status);
		if(status2 == null) {
			throw new BusinessException("Status with Description: "+status+ " is not a valid Status!");
		}
		
		Ticket ticket = optTicket.get();
		ticket.setStatus(status2);
		User user = optUser.get();
		ticket.setTicketUser(user);
		
		ticketRepository.save(ticket);
		
		AuditLog auditLog = new AuditLog("Ticket Update Status", ticket, user);
		auditLog.setCreationDate(DateUtils.getCurrentDate());
		auditLogRepository.save(auditLog);
		
		return new TicketDTO(ticket);
	}

	@Override
	public TicketDTO getTicketById(Long ticketId) throws BusinessException {
		Optional<Ticket> optTicket = ticketRepository.findById(ticketId);
		if(optTicket.isPresent()) {
			Ticket ticket = optTicket.get();
			List<CommentsDTO> commentsDTOs = getCommentsDTOsByTicket(ticketId);
			TicketDTO ticketDTO = new TicketDTO(ticket);
			ticketDTO.setComments(commentsDTOs);
			return ticketDTO;
		}
		throw new BusinessException("Ticket with ID: "+ticketId+" was not found!");
	}

	private List<CommentsDTO> getCommentsDTOsByTicket(Long ticketId) {
		List<Comments> commentsList = commentsRepository.findByTicketId(ticketId);
		List<CommentsDTO> commentsDTOs = new ArrayList<>(commentsList.size());
		for(Comments comments: commentsList) {
			CommentsDTO commentsDTO = new CommentsDTO(comments);
			commentsDTOs.add(commentsDTO);
		}
		return commentsDTOs;
	}

	@Override
	public Page<TicketDTO> findTicketsByEmployeeId(Long employeeId, Pageable pageable) throws BusinessException {
		Page<Ticket> ticketPage = ticketRepository.findByEmployeeId(employeeId, pageable);
		return this.convertTicketPageToDTOPage(ticketPage);
	}

	@Override
	public Page<TicketDTO> findTicketsByStatus(String status, Pageable pageable) throws BusinessException {
		Status status2 = Status.toEnum(status);
		if(status2 == null) {
			throw new BusinessException("Status with Description: "+status+ " is not a valid Status!");
		}
		Page<Ticket> ticketPage = ticketRepository.findByStatus(status2, pageable);
		ticketPage.forEach(ticket -> {
			List<Comments> commentsList = commentsRepository.findByTicketId(ticket.getId());
			ticket.setComments(commentsList);
		});
		return this.convertTicketPageToDTOPage(ticketPage);
	}
	
	private TicketDTO ticketsToDTO(Ticket ticket) {
        return new TicketDTO(ticket);
    }
	
	private Page<TicketDTO> convertTicketPageToDTOPage(Page<Ticket> ticketPage) {
		return ticketPage.map(this::ticketsToDTO);
	}

	@Override
	public Page<TicketDTO> findTicketsByEmployeeIdAndStatus(Long employeeId, String status, Pageable pageable)
			throws BusinessException {
		Optional<User> optUser = userRepository.findById(employeeId);
		if(optUser.isEmpty()) {
			throw new BusinessException("Employee with ID: "+employeeId+" was not found!");
		}
		Status status2 = Status.toEnum(status);
		if(status2 == null) {
			throw new BusinessException("Status with Description: "+status+ " is not a valid Status!");
		}
		Page<Ticket> ticketPage = ticketRepository.findByEmployeeIdAndStatus(employeeId, status2, pageable);
		return this.convertTicketPageToDTOPage(ticketPage);
	}

	@Override
	public int countTicketsByStatus(String status) throws BusinessException {
		Status status2 = Status.toEnum(status);
		if(status2 == null) {
			throw new BusinessException("Status with Description: "+status+ " is not a valid Status!");
		}
		List<Ticket> tickets = ticketRepository.findByStatus(status2);
		return tickets.size();
	}
	
	@Override
	public int countTicketsByEmployeeIdAndStatus(Long employeeId, String status) throws BusinessException {
		Status status2 = Status.toEnum(status);
		if(status2 == null) {
			throw new BusinessException("Status with Description: "+status+ " is not a valid Status!");
		}
		List<Ticket> tickets = ticketRepository.findByEmployeeIdAndStatus(employeeId, status2);
		return tickets.size();
	}

	@Override
	public int countUnsolvedTickets() throws BusinessException {
		int count = ticketRepository.countUnsolvedTickets();
		return count;
	}

	@Override
	public int countUnsolvedTicketsByEmployeeId(Long employeeId) throws BusinessException {
		int count = ticketRepository.countUnsolvedTicketsByEmployeeId(employeeId);
		return count;
	}
	
	@Override
	public List<TicketDTO> getAllUnsolvedTickets() throws BusinessException {
		List<Ticket> tickets = ticketRepository.findAllUnsolvedTickets();
		List<TicketDTO> dtos = new ArrayList<>();
		for (Ticket ticket : tickets) {
			dtos.add(new TicketDTO(ticket));
		}
		return dtos;
	}

}
