package com.hannsoftware.ticketmanagementsystem.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hannsoftware.ticketmanagementsystem.api.base.BaseController;
import com.hannsoftware.ticketmanagementsystem.dto.CommentsDTO;
import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;
import com.hannsoftware.ticketmanagementsystem.services.CommentsService;
import com.hannsoftware.ticketmanagementsystem.services.TicketService;
import com.hannsoftware.ticketmanagementsystem.utils.BusinessException;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/tickets")
public class TicketController extends BaseController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private CommentsService commentsService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerTicket(@PathParam(value = "employeeId") Long employeeId, @RequestBody TicketDTO ticketDTO) {
		try {
			TicketDTO dto = ticketService.createTicket(employeeId, ticketDTO);
			return ResponseEntity.ok(dto);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/{id}/comment")
	public ResponseEntity<?> commentTicket(@PathVariable Long id, @PathParam(value = "userId") Long userId, @RequestBody CommentsDTO commentsDTO) {
		try {
			CommentsDTO dto = commentsService.createComment(userId, id, commentsDTO);
			return ResponseEntity.ok(dto);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}/update-status")
	public ResponseEntity<?> updateTicketStatus(@PathVariable Long id, @PathParam(value = "userId") Long userId, @PathParam(value = "status") String status) {
		try {
			TicketDTO dto = ticketService.updateTicketStatus(id, userId, status);
			return ResponseEntity.ok(dto);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTicketById(@PathVariable Long id) {
		try {
			TicketDTO dto = ticketService.getTicketById(id);
			return ResponseEntity.ok(dto);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<?> getTicketsByEmployeeId(@PathVariable Long employeeId, Pageable pageable) {
		try {
			Page<TicketDTO> tickets = ticketService.findTicketsByEmployeeId(employeeId, pageable);
			return ResponseEntity.ok(convertFromPageToList(tickets));
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/status")
	public ResponseEntity<?> getTicketsByStatus(@PathParam(value = "status") String status, Pageable pageable) {
		try {
			Page<TicketDTO> tickets = ticketService.findTicketsByStatus(status, pageable);
			return ResponseEntity.ok(convertFromPageToList(tickets));
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/employee/{employeeId}/status")
	public ResponseEntity<?> getTicketsByEmployeeIdAndStatus(@PathVariable Long employeeId, @PathParam(value = "status") String status, Pageable pageable) {
		try {
			Page<TicketDTO> tickets = ticketService.findTicketsByEmployeeIdAndStatus(employeeId, status, pageable);
			return ResponseEntity.ok(convertFromPageToList(tickets));
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/status/count")
	public ResponseEntity<?> countTicketsByStatus(@PathParam(value = "status") String status) {
		try {
			int count = ticketService.countTicketsByStatus(status);
			return ResponseEntity.ok(count);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/employee/{employeeId}/status/count")
	public ResponseEntity<?> countTicketsByEmployeeIdAndStatus(@PathVariable Long employeeId, @PathParam(value = "status") String status) {
		try {
			int count = ticketService.countTicketsByEmployeeIdAndStatus(employeeId, status);
			return ResponseEntity.ok(count);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/unsolved")
	public ResponseEntity<?> countUnsolvedTickets() {
		try {
			int count = ticketService.countUnsolvedTickets();
			return ResponseEntity.ok(count);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/employee/{employeeId}/unsolved")
	public ResponseEntity<?> countUnsolvedTicketsByEmployeeId(@PathVariable Long employeeId) {
		try {
			int count = ticketService.countUnsolvedTicketsByEmployeeId(employeeId);
			return ResponseEntity.ok(count);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/{ticketId}/comments")
	public ResponseEntity<?> getCommentsByTicketId(@PathVariable Long ticketId) {
		try {
			List<CommentsDTO> dtos = commentsService.getCommentsByTicketId(ticketId);
			return ResponseEntity.ok(dtos);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/unsolved-tickets")
	public ResponseEntity<?> getAllUnsolvedTickets() {
		try {
			List<TicketDTO> dtos = ticketService.getAllUnsolvedTickets();
			return ResponseEntity.ok(dtos);
		} catch (BusinessException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	private List<TicketDTO> convertFromPageToList(Page<TicketDTO> ticketsPage) {
		List<TicketDTO> dtos = new ArrayList<>();
		ticketsPage.forEach(ticket -> {
			dtos.add(ticket);
		});
		return dtos;
	}


}
