package rs.raf.rental.e2e;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Test;
import rs.raf.rental.dto.RentalDTO;

import java.util.Calendar;
import java.util.Date;

import static java.util.Date.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentEndToEndTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void GetBikePriceTest() {

        RentalDTO requestBodyDTO = new RentalDTO(1,
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 10, 0, 0)),
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 20, 0, 0)));

        WebTestClient.ResponseSpec response =  webClient.post()
                .uri("/rent/bike/price")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyDTO)
                .exchange();

        response.expectStatus().isOk();
        response.expectHeader().contentType(MediaType.APPLICATION_JSON);
        Double responseVal = response.expectBody(Double.class).returnResult().getResponseBody();
        assertEquals(4000.0, responseVal);
    }
}
