package com.hannsoftware.ticketmanagementsystem.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.hannsoftware.ticketmanagementsystem.dto.CommentsDTO;

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
@Table(name = "COMMENTS")
public class Comments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2006675885945303828L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @SequenceGenerator(name = "comments_seq", sequenceName = "COMMENTS_SEQUENCE", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "COMMENT", nullable = false)
	private String comment;
	
	@Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne
    @JoinColumn(name = "TICKET_ID", nullable = false)
	private Ticket ticket;
	
	@ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	public Comments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comments(String comment, Ticket ticket, User user) {
		super();
		this.comment = comment;
		this.ticket = ticket;
		this.user = user;
	}

	public Comments(Long id, String comment, Date creationDate, Ticket ticket, User user) {
		super();
		this.id = id;
		this.comment = comment;
		this.creationDate = creationDate;
		this.ticket = ticket;
		this.user = user;
	}
	
	public Comments(CommentsDTO commentsDTO) {
		setId(commentsDTO.getId());
		setComment(commentsDTO.getComment());
		setCreationDate(commentsDTO.getCreationDate());
		if(commentsDTO.getUser() != null) setUser(new User(commentsDTO.getUser()));
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", comment=" + comment + ", creationDate=" + creationDate + ", ticket=" + ticket
				+ ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, creationDate, id, ticket, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comments other = (Comments) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(id, other.id) && Objects.equals(ticket, other.ticket)
				&& Objects.equals(user, other.user);
	}

}
