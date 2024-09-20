package rs.raf.rental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.service.implementation.RentalService;

import java.util.Calendar;
import java.util.Date;

import static java.util.Date.UTC;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(RentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void GetBikePrice() throws Exception {

        when(rentalService.calculatePrice(Mockito.any(), Mockito.any()))
                .thenReturn(900.0);

        RentalDTO requestBodyDTO = new RentalDTO(1,
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 10, 0, 0)),
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 20, 0, 0)));

        this.mockMvc.perform(post("/rent/bike/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(requestBodyDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("900.0"));
    }
}
