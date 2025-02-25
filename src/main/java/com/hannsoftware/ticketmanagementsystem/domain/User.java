package com.hannsoftware.ticketmanagementsystem.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.hannsoftware.ticketmanagementsystem.domain.util.Role;
import com.hannsoftware.ticketmanagementsystem.dto.UserDTO;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1581453336259888837L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "USERS_SEQUENCE", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "FULLNAME", nullable = false)
	private String fullName;
	
	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "SALT", nullable = false)
	private String salt;
	
	@Column(name = "ROLE", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	private List<Ticket> tickets;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String fullName, String username, String password, Role role) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(Long id, String fullName, String username, String password, Role role, List<Ticket> tickets) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.role = role;
		this.tickets = tickets;
	}
	
	public User(UserDTO userDTO) {
		setId(userDTO.getId());
		setFullName(userDTO.getFullName());
		setUsername(userDTO.getUsername());
		setPassword(userDTO.getPassword());
		if(!StringUtils.isBlank(userDTO.getRole())) setRole(Role.toEnum(userDTO.getRole()));
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + fullName + ", role=" + role + ", tickets=" + tickets + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullName, role, tickets);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(fullName, other.fullName) && role == other.role
				&& Objects.equals(tickets, other.tickets);
	}

}
