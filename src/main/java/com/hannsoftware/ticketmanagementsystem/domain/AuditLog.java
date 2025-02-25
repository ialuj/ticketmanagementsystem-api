package com.hannsoftware.ticketmanagementsystem.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.hannsoftware.ticketmanagementsystem.dto.AuditLogDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "AUDIT_LOGS")
public class AuditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -823970817325749296L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_log_seq")
    @SequenceGenerator(name = "audit_log_seq", sequenceName = "AUDIT_LOGS_SEQUENCE", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "ACTION")
	private String action;
	
	@Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne
    @JoinColumn(name = "TICKET_ID", nullable = false)
	private Ticket ticket;
	
	@ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
	private User ticketUser;

	public AuditLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuditLog(String action, Ticket ticket, User ticketUser) {
		super();
		this.action = action;
		this.ticket = ticket;
		this.ticketUser = ticketUser;
	}

	public AuditLog(Long id, String action, Date creationDate, Ticket ticket, User ticketUser) {
		super();
		this.id = id;
		this.action = action;
		this.creationDate = creationDate;
		this.ticket = ticket;
		this.ticketUser = ticketUser;
	}
	
	public AuditLog(AuditLogDTO auditLogDTO) {
		setId(auditLogDTO.getId());
		setAction(auditLogDTO.getAction());
		setCreationDate(auditLogDTO.getCreationDate());
		if(auditLogDTO.getTicket() != null) setTicket(new Ticket(auditLogDTO.getTicket()));
		if(auditLogDTO.getUser() != null) setTicketUser(new User(auditLogDTO.getUser()));
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

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getTicketUser() {
		return ticketUser;
	}

	public void setTicketUser(User ticketUser) {
		this.ticketUser = ticketUser;
	}

	@Override
	public String toString() {
		return "AuditLog [id=" + id + ", action=" + action + ", creationDate=" + creationDate + ", ticket=" + ticket
				+ ", user=" + ticketUser + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, creationDate, id, ticket, ticketUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditLog other = (AuditLog) obj;
		return Objects.equals(action, other.action) && Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(id, other.id) && Objects.equals(ticket, other.ticket)
				&& Objects.equals(ticketUser, other.ticketUser);
	}

}
