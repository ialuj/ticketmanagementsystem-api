package com.hannsoftware.ticketmanagementsystem.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hannsoftware.ticketmanagementsystem.dto.AuditLogDTO;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

public interface AuditLogService {
	
	public Page<AuditLogDTO> getAllAuditLogs(Pageable pageable) throws BusinessException;
	
	public Page<AuditLogDTO> getAuditLogsByTicketId(Long ticketId, Pageable pageable) throws BusinessException;
	
	public Page<AuditLogDTO> getAuditLogsByUserId(Long userId, Pageable pageable) throws BusinessException;

}
