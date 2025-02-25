package com.hannsoftware.ticketmanagementsystem.dto;

import java.io.Serializable;
import java.util.Date;

import com.hannsoftware.ticketmanagementsystem.domain.AuditLog;

public class AuditLogDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1369016566687223445L;
	
	private Long id;
	
	private String action;
	
	private Date creationDate;
	
    private TicketDTO ticket;
	
	private UserDTO user;

	public AuditLogDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuditLogDTO(String action, TicketDTO ticket, UserDTO user) {
		super();
		this.action = action;
		this.ticket = ticket;
		this.user = user;
	}
	
	public AuditLogDTO(AuditLog auditLog) {
		setId(auditLog.getId());
		setAction(auditLog.getAction());
		setCreationDate(auditLog.getCreationDate());
		if(auditLog.getTicket() != null) setTicket(new TicketDTO(auditLog.getTicket()));
		if(auditLog.getUser() != null) setUser(new UserDTO(auditLog.getUser()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public TicketDTO getTicket() {
		return ticket;
	}

	public void setTicket(TicketDTO ticket) {
		this.ticket = ticket;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
