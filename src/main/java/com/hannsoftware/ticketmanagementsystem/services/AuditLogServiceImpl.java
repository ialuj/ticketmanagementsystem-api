package com.hannsoftware.ticketmanagementsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hannsoftware.ticketmanagementsystem.domain.AuditLog;
import com.hannsoftware.ticketmanagementsystem.dto.AuditLogDTO;
import com.hannsoftware.ticketmanagementsystem.repository.AuditLogRepository;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

@Service
public class AuditLogServiceImpl implements AuditLogService {
	
	@Autowired
	private AuditLogRepository auditLogRepository;

	@Override
	public Page<AuditLogDTO> getAllAuditLogs(Pageable pageable) throws BusinessException {
		Page<AuditLog> auditLogPage = auditLogRepository.findAll(pageable);
		return convertAuditLogPageToDTOPage(auditLogPage);
	}

	@Override
	public Page<AuditLogDTO> getAuditLogsByTicketId(Long ticketId, Pageable pageable) throws BusinessException {
		Page<AuditLog> auditLogPage = auditLogRepository.findByTicketId(ticketId, pageable);
		return convertAuditLogPageToDTOPage(auditLogPage);
	}

	@Override
	public Page<AuditLogDTO> getAuditLogsByUserId(Long userId, Pageable pageable) throws BusinessException {
		Page<AuditLog> auditLogPage = auditLogRepository.findByUserId(userId, pageable);
		return convertAuditLogPageToDTOPage(auditLogPage);
	}
	
	private AuditLogDTO auditLogToDTO(AuditLog auditLog) {
        return new AuditLogDTO(auditLog);
    }
	
	private Page<AuditLogDTO> convertAuditLogPageToDTOPage(Page<AuditLog> auditLogPage) {
		return auditLogPage.map(this::auditLogToDTO);
	}

}
