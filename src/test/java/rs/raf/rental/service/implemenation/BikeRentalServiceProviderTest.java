package rs.raf.rental.service.implemenation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.model.Bike;
import rs.raf.rental.repository.IBikeRepository;
import rs.raf.rental.service.exception.UnableToCalculatePriceException;
import rs.raf.rental.service.implementation.BikeRentalServiceProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static java.util.Date.UTC;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BikeRentalServiceProviderTest {

    @Mock
    IBikeRepository bikeRepository;

    @Autowired
    @InjectMocks
    BikeRentalServiceProvider bikeRentalServiceProvider;

    @Test
    public void CalculateBikePriceSimpleTest(){
        RentalDTO rentalDTO = new RentalDTO(1,
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 10, 0, 0)),
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 20, 0, 0)));


        Bike bike = new Bike("SCOTT ADDICT RC 15", false, 350);
        when(bikeRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(bike));

        double expectedPrice = 3500; /* 350 * 10 */

        try {
            double price = bikeRentalServiceProvider.calculatePrice(rentalDTO);
            assertEquals(expectedPrice, price, 0);
        } catch (UnableToCalculatePriceException e) {
            fail(String.format("Unexpected exception: %s", e.getMessage()));
        }
    }

    @Test
    public void CalculateBikePriceFullHourTest(){
        RentalDTO rentalDTO = new RentalDTO(1,
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 10, 0, 0)),
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 10, 45, 0)));


        Bike bike = new Bike("SCOTT ADDICT RC 15", false, 350);
        when(bikeRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(bike));

        double expectedPrice = 350; /* 350 * 1 */

        try {
            double price = bikeRentalServiceProvider.calculatePrice(rentalDTO);
            assertEquals(expectedPrice, price, 0);
        } catch (UnableToCalculatePriceException e) {
            fail(String.format("Unexpected exception: %s", e.getMessage()));
        }
    }
}
