package com.hannsoftware.ticketmanagementsystem.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hannsoftware.ticketmanagementsystem.domain.Ticket;
import com.hannsoftware.ticketmanagementsystem.domain.User;
import com.hannsoftware.ticketmanagementsystem.utils.Utilities;

public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1836653739234090267L;
	
	private Long id;
	
	private String fullName;
	
	private String username;
	
	private String password;
	
	private String role;
	
	private Collection<TicketDTO> tickets;

	public UserDTO() {
		super();
	}
	
	public UserDTO(String fullName, String username, String password, String role) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.role = role;
		this.tickets = new ArrayList<>();
	}

	public UserDTO(User user) {
		setId(user.getId());
		setFullName(user.getFullName());
		setUsername(user.getUsername());
		if(user.getRole() != null) setRole(user.getRole().getDescription());
		if(Utilities.listHasElements(user.getTickets())) setTickets(ticketsList(user.getTickets()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Collection<TicketDTO> getTickets() {
		return tickets;
	}

	public void setTickets(Collection<TicketDTO> tickets) {
		this.tickets = tickets;
	}
	
	private List<TicketDTO> ticketsList(List<Ticket> tickets) {
        List<TicketDTO> ticketDTOs = new ArrayList<>();
        for (Ticket ticket : tickets) {
        	ticketDTOs.add(new TicketDTO(ticket));
        }
        return ticketDTOs;
    }

}
