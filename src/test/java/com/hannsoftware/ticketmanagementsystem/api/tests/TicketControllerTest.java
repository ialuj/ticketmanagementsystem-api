package com.hannsoftware.ticketmanagementsystem.api.tests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import com.hannsoftware.ticketmanagementsystem.dto.TicketDTO;
import com.hannsoftware.ticketmanagementsystem.services.TicketService;
import com.hannsoftware.ticketmanagementsystem.services.UserService;

@Disabled
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;
    
	private TicketDTO ticket;

	@BeforeEach
    public void setUp() {
    	ticket = new TicketDTO();
    	ticket.setId(11L);
    	ticket.setTitle("Network Issue");
    	ticket.setDescription("There is a problem with the network");
    	ticket.setPriority("Low");
    	ticket.setCategory("Network");
    	ticket.setStatus("New");
	}

    @Test
    void testCreateTicket() throws Exception {
    	Long employeeId = 11L;
        when(ticketService.createTicket(employeeId, Mockito.any(TicketDTO.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets/register?employeeId="+employeeId)
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
    	String status = "New";
        when(ticketService.findTicketsByStatus(status, Pageable.unpaged())).thenReturn(page);

        mockMvc.perform(get("/api/tickets/status?status"+status))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Network Issue"))
                .andExpect(jsonPath("$[0].priority").value("Low"));
    }
}
	
