package com.hannsoftware.ticketmanagementsystem.dto;

import java.io.Serializable;
import java.util.Date;

import com.hannsoftware.ticketmanagementsystem.domain.Comments;

public class CommentsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -418519690823534223L;
	
	private Long id;
	
	private String comment;
	
	private Date creationDate;
	
	private UserDTO user;
	
	public CommentsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentsDTO(String comment, UserDTO user) {
		super();
		this.comment = comment;
		this.user = user;
	}

	public CommentsDTO(Comments comments) {
		setId(comments.getId());
		setComment(comments.getComment());
		setCreationDate(comments.getCreationDate());
		if(comments.getUser() != null) setUser(new UserDTO(comments.getUser()));
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
}
