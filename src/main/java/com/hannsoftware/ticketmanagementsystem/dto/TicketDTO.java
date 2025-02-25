package com.hannsoftware.ticketmanagementsystem.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hannsoftware.ticketmanagementsystem.domain.Comments;
import com.hannsoftware.ticketmanagementsystem.domain.Ticket;
import com.hannsoftware.ticketmanagementsystem.utils.Utilities;

public class TicketDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 439819622854035112L;
	
	private Long id;
	
	private String title;
	
	private String description;
	
	private Date creationDate;
	
	private String priority;
	
	private String category;
	
	private String status;
		
	private List<CommentsDTO> comments;

	public TicketDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TicketDTO(String title, String description, String priority, String category) {
		super();
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.category = category;
	}

	public TicketDTO(Ticket ticket) {
		setId(ticket.getId());
		setTitle(ticket.getTitle());
		setDescription(ticket.getDescription());
		setCreationDate(ticket.getCreationDate());
		if(ticket.getPriority() != null) setPriority(ticket.getPriority().getDescription());
		if(ticket.getCategory() != null) setCategory(ticket.getCategory().getDescription());
		if(ticket.getStatus() != null) setStatus(ticket.getStatus().getDescription());
		if(Utilities.listHasElements(ticket.getComments())) setComments(commentsList(ticket.getComments()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CommentsDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentsDTO> comments) {
		this.comments = comments;
	}
	
	private List<CommentsDTO> commentsList(List<Comments> comments) {
        List<CommentsDTO> commentsDTOs = new ArrayList<>();
        for (Comments comments2 : comments) {
        	commentsDTOs.add(new CommentsDTO(comments2));
        }
        return commentsDTOs;
    }

}
