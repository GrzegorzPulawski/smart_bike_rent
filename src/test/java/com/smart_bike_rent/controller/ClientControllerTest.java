package com.smart_bike_rent.controller;

import com.smart_bike_rent.dto.ClientDTO;
import com.smart_bike_rent.request.CreateClientRequest;
import com.smart_bike_rent.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;
    private final ObjectMapper objectMapper= new ObjectMapper();
    @Test
    public void givenClient_whenAdd_ThenReturnStatus() throws Exception {
        // given
       CreateClientRequest createClientRequest = new CreateClientRequest(
               "Marcin",
               "Nowak",
               "ASD123456",
               123444555);

          doNothing().when(clientService).addClient(any(CreateClientRequest.class));
        // when
         // Wysłanie żądania POST do endpointu kontrolera
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClientRequest)))
                        .andExpect(status().isOk());
        // then
        // Weryfikacja, czy metoda addClient została wywołana
        verify(clientService).addClient(any(CreateClientRequest.class));
    }
    @Test
    void givenClients_whenGetClientsList_thenReturnJsonArray() throws Exception {
        // Given
        ClientDTO client1 = new ClientDTO(
                1L,
                "Marcin",
                "Nowak",
                "ASD123456",
                123444555);
        ClientDTO client2 = new ClientDTO(
                2L,
                "Jan",
                "Kowalski",
                "JD7777",
                987654321);

        List<ClientDTO> allClients = Arrays.asList(client1, client2);

        // When
        when(clientService.findAllClients()).thenReturn(allClients);

        // Then
         mockMvc.perform(get("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(allClients.size())))
                .andExpect(jsonPath("$[0].firstName", is("Marcin")))
                .andExpect(jsonPath("$[1].firstName", is("Jan")));
    }
}