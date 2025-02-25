package com.hannsoftware.ticketmanagementsystem.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

public interface TicketService {
	
	public TicketDTO createTicket(Long employeedId, TicketDTO ticketDTO) throws BusinessException;
	
	public TicketDTO updateTicketStatus(Long ticketId, Long userId, String status) throws BusinessException;
	
	public TicketDTO getTicketById(Long ticketId) throws BusinessException;
	
	public Page<TicketDTO> findTicketsByEmployeeId(Long employeeId, Pageable pageable) throws BusinessException;
	
	public Page<TicketDTO> findTicketsByStatus(String status, Pageable pageable) throws BusinessException;
	
	public Page<TicketDTO> findTicketsByEmployeeIdAndStatus(Long employeeId, String status, Pageable pageable) throws BusinessException;
	
	public int countTicketsByStatus(String status) throws BusinessException;

	int countTicketsByEmployeeIdAndStatus(Long employeeId, String status) throws BusinessException;
	
	public int countUnsolvedTickets() throws BusinessException;
	
	int countUnsolvedTicketsByEmployeeId(Long employeeId) throws BusinessException;

	List<TicketDTO> getAllUnsolvedTickets() throws BusinessException;

}
