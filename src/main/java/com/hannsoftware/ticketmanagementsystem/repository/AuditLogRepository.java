package com.hannsoftware.ticketmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hannsoftware.ticketmanagementsystem.domain.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
	
	public Page<AuditLog> findByTicketId(Long ticketId, Pageable pageable);
	
	public Page<AuditLog> findByTicketUserId(Long ticketUserId, Pageable pageable);

}
