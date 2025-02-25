package com.hannsoftware.ticketmanagementsystem.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.hannsoftware.ticketmanagementsystem.domain.util.Category;
import com.hannsoftware.ticketmanagementsystem.domain.util.Priority;
import com.hannsoftware.ticketmanagementsystem.domain.util.Status;
import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "TICKETS")
public class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6815889969569543846L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_seq")
    @SequenceGenerator(name = "tickets_seq", sequenceName = "TICKETS_SEQUENCE", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "PRIORITY", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
	private Priority priority;
	
	@Column(name = "CATEGORY", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
	private Category category;
	
	@Column(name = "STATUS", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
	private Status status = Status.NEW;
	
	@ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private User employee;
	
	@ManyToOne
    @JoinColumn(name = "USER_ID")
	private User ticketUser;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
	private List<Comments> comments;

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(String title, String description, Priority priority, Category category, User employee, User ticketUser) {
		super();
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.category = category;
		this.employee = employee;
		this.ticketUser = ticketUser;
	}

	public Ticket(Long id, String title, String description, Date creationDate, Priority priority, Category category,
			Status status, User employee, User ticketUser, List<Comments> comments) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.priority = priority;
		this.category = category;
		this.status = status;
		this.employee = employee;
		this.ticketUser = ticketUser;
		this.comments = comments;
	}
	
	public Ticket(TicketDTO ticketDTO) {
		setId(ticketDTO.getId());
		setTitle(ticketDTO.getTitle());
		setDescription(ticketDTO.getDescription());
		if(!StringUtils.isBlank(ticketDTO.getPriority())) setPriority(Priority.toEnum(ticketDTO.getPriority()));
		if(!StringUtils.isBlank(ticketDTO.getCategory())) setCategory(Category.toEnum(ticketDTO.getCategory()));
		if(!StringUtils.isBlank(ticketDTO.getStatus())) setStatus(Status.toEnum(ticketDTO.getStatus()));
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

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public User getTicketUser() {
		return ticketUser;
	}

	public void setTicketUser(User ticketUser) {
		this.ticketUser = ticketUser;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", title=" + title + ", description=" + description + ", creationDate="
				+ creationDate + ", priority=" + priority + ", category=" + category + ", status=" + status
				+ ", employee=" + employee + ", comments=" + comments + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, comments, creationDate, description, employee, id, priority, status, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return category == other.category && Objects.equals(comments, other.comments)
				&& Objects.equals(creationDate, other.creationDate) && Objects.equals(description, other.description)
				&& Objects.equals(employee, other.employee) && Objects.equals(id, other.id)
				&& priority == other.priority && status == other.status && Objects.equals(title, other.title);
	}

}
