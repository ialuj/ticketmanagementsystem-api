package com.hannsoftware.ticketmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hannsoftware.ticketmanagementsystem.domain.Ticket;
import com.hannsoftware.ticketmanagementsystem.domain.util.Status;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	Optional<Ticket> findById(Long id);
	
	Page<Ticket> findByEmployeeId(Long employeeId, Pageable pageable);
	
	Page<Ticket> findByStatus(Status status, Pageable pageable);
	
	Page<Ticket> findByEmployeeIdAndStatus(Long employeeId, Status status, Pageable pageable);

	List<Ticket> findByStatus(Status status);
	
	List<Ticket> findByEmployeeIdAndStatus(Long employeeId, Status status);
	
	@Query("Select COUNT(t) from Ticket t where t.status <> 'Resolved'")
	int countUnsolvedTickets();
	
	@Query("Select COUNT(t) from Ticket t where t.status <> 'Resolved' and t.employee.id = :employeeId ")
	int countUnsolvedTicketsByEmployeeId(Long employeeId);
	
	@Query("Select t from Ticket t where t.status <> 'Resolved'")
	List<Ticket> findAllUnsolvedTickets();

}
