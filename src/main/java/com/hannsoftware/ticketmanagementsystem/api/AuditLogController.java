package com.hannsoftware.ticketmanagementsystem.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hannsoftware.ticketmanagementsystem.dto.AuditLogDTO;
import com.hannsoftware.ticketmanagementsystem.services.AuditLogService;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

@RestController
@RequestMapping("/api/auditlogs")
public class AuditLogController {
	
	@Autowired
	private AuditLogService auditLogService;
	
	@GetMapping
	public ResponseEntity<?> getAllAuditLogs(Pageable pageable) {
		try {
			Page<AuditLogDTO> auditLogPage = auditLogService.getAllAuditLogs(pageable);
			List<AuditLogDTO> dtos = new ArrayList<>();
			auditLogPage.forEach(auditLog -> {
				dtos.add(auditLog);
			});
			return ResponseEntity.ok(dtos);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
