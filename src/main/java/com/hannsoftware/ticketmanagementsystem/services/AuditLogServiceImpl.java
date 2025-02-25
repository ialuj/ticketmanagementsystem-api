package com.hannsoftware.ticketmanagementsystem.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hannsoftware.ticketmanagementsystem.domain.AuditLog;
import com.hannsoftware.ticketmanagementsystem.dto.AuditLogDTO;
import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;
import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;
import com.hannsoftware.ticketmanagementsystem.repository.AuditLogRepository;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

@Service
public class AuditLogServiceImpl implements AuditLogService {
	
	@Autowired
	private AuditLogRepository auditLogRepository;

	@Override
	public Page<AuditLogDTO> getAllAuditLogs(Pageable pageable) throws BusinessException {
		Page<AuditLog> auditLogPage = auditLogRepository.findAll(pageable);
		List<AuditLogDTO> auditLogDTOs = new ArrayList<>();
		auditLogPage.forEach(auditLog -> {
			TicketDTO ticketDTO = new TicketDTO(auditLog.getTicket());
			UserDTO userDTO = new UserDTO(auditLog.getTicketUser());
			AuditLogDTO auditLogDTO = new AuditLogDTO(auditLog);
			auditLogDTO.setUser(userDTO);
			auditLogDTO.setTicket(ticketDTO);
			auditLogDTOs.add(auditLogDTO);
		});
		Page<AuditLogDTO> page = new PageImpl<>(auditLogDTOs, Pageable.unpaged(), auditLogDTOs.size());
		page.and(auditLogDTOs);
		return page;
	}

	@Override
	public Page<AuditLogDTO> getAuditLogsByTicketId(Long ticketId, Pageable pageable) throws BusinessException {
		Page<AuditLog> auditLogPage = auditLogRepository.findByTicketId(ticketId, pageable);
		return convertAuditLogPageToDTOPage(auditLogPage);
	}

	@Override
	public Page<AuditLogDTO> getAuditLogsByUserId(Long userId, Pageable pageable) throws BusinessException {
		Page<AuditLog> auditLogPage = auditLogRepository.findByTicketUserId(userId, pageable);
		return convertAuditLogPageToDTOPage(auditLogPage);
	}
	
	private AuditLogDTO auditLogToDTO(AuditLog auditLog) {
        return new AuditLogDTO(auditLog);
    }
	
	private Page<AuditLogDTO> convertAuditLogPageToDTOPage(Page<AuditLog> auditLogPage) {
		return auditLogPage.map(this::auditLogToDTO);
	}

}
