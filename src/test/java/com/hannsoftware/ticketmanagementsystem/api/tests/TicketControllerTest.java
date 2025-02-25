package com.hannsoftware.ticketmanagementsystem.api.tests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.hannsoftware.ticketmanagementsystem.api.TicketController;
import com.hannsoftware.ticketmanagementsystem.domain.User;
import com.hannsoftware.ticketmanagementsystem.domain.util.Role;
import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;
import com.hannsoftware.ticketmanagementsystem.services.TicketService;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;
    
	private TicketDTO ticket;

	private User employee;

    public TicketControllerTest() {
    	ticket = new TicketDTO();
    	ticket.setId(10L);
    	ticket.setTitle("Network Issue");
    	ticket.setDescription("There is a problem with the network");
    	ticket.setPriority("Low");
    	ticket.setCategory("Network");
    	ticket.setStatus("New");
    	
    	employee = new User();
    	employee.setId(10L);
    	employee.setFullName("John Doe");
    	employee.setUsername("john.doe");
    	employee.setPassword("hndlbmkehjwHoNptg38zO5V82U5xozIkgGwxitI/AM8=");
    	employee.setSalt("itmsystem");
    	employee.setRole(Role.toEnum("Employee"));
	}


    @Test
    void testCreateTicket() throws Exception {
        when(ticketService.createTicket(10L, Mockito.any(TicketDTO.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Network Issue\",\"description\":\"There is a problem with the network\",\"priority\":\"Low\",\"status\":\"New\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Network Issue"))
                .andExpect(jsonPath("$.priority").value("Low"));
    }

    @Test
    void testGetTicketsByStatus() throws Exception {
    	Page<TicketDTO> page = Page.empty();
    	page.and(ticket);
        when(ticketService.findTicketsByStatus("New", Pageable.unpaged())).thenReturn(page);

        mockMvc.perform(get("/api/tickets/status/New"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Network Issue"))
                .andExpect(jsonPath("$[0].priority").value("Low"));
    }
}
	
