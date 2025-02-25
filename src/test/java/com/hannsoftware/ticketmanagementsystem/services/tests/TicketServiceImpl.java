package com.hannsoftware.ticketmanagementsystem.services.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hannsoftware.ticketmanagementsystem.domain.Ticket;
import com.hannsoftware.ticketmanagementsystem.domain.User;
import com.hannsoftware.ticketmanagementsystem.domain.util.Category;
import com.hannsoftware.ticketmanagementsystem.domain.util.Priority;
import com.hannsoftware.ticketmanagementsystem.domain.util.Role;
import com.hannsoftware.ticketmanagementsystem.domain.util.Status;
import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;
import com.hannsoftware.ticketmanagementsystem.repository.TicketRepository;
import com.hannsoftware.ticketmanagementsystem.services.TicketService;

@SpringBootTest
public class TicketServiceImpl {
	
	@Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;
    
    private Ticket ticket;
    
    private User employee;
    
    private User user;
    
    @BeforeEach
    public void setUp() {
    	ticket = new Ticket();
    	ticket.setId(10L);
    	ticket.setTitle("Network Issue");
    	ticket.setDescription("There is a problem with the network");
    	ticket.setPriority(Priority.toEnum("Low"));
    	ticket.setCategory(Category.toEnum("Network"));
    	ticket.setStatus(Status.toEnum("New"));
    	
    	employee = new User();
    	employee.setId(10L);
    	employee.setFullName("John Doe");
    	employee.setUsername("john.doe");
    	employee.setPassword("hndlbmkehjwHoNptg38zO5V82U5xozIkgGwxitI/AM8=");
    	employee.setSalt("itmsystem");
    	employee.setRole(Role.toEnum("Employee"));
    	
    	employee = new User();
    	employee.setId(11L);
    	employee.setFullName("Anna Doe");
    	employee.setUsername("anna.doe");
    	employee.setPassword("hndlbmkehjwHoNptg38zO5V82U5xozIkgGwxitI/AM8=");
    	employee.setSalt("itmsystem");
    	employee.setRole(Role.toEnum("IT Support"));
    }
    
    @Test
    void testCreateTicket() {
        // Arrange
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // Act
        TicketDTO createdTicket = ticketService.createTicket(employee.getId(), new TicketDTO(ticket));

        // Assert
        assertNotNull(createdTicket);
        assertEquals("Network Issue", createdTicket.getTitle());
        verify(ticketRepository, times(1)).save(ticket);
    }
    
    @Test
    void testGetTicketsByStatus() {
        // Arrange
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketRepository.findByStatus(Status.toEnum("New"))).thenReturn(tickets);

        // Act
        Page<TicketDTO> result = ticketService.findTicketsByStatus("New", Pageable.unpaged());

        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals("Network Issue", result.getContent().get(0).getTitle());
        verify(ticketRepository, times(1)).findByStatus(Status.toEnum("New"));
    }

}
