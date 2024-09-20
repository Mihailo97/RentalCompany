package rs.raf.rental.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import rs.raf.rental.model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Date.UTC;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RentalRepositoryTest {

    @Autowired
    private IRentalRepository rentalRepository;

    @Test
    void CreateRentalTest() {
        Rental rental = new Rental(
                1,
                RentalItemType.BIKE,
                new Date(UTC(124, Calendar.SEPTEMBER, 12, 10, 0, 0)),
                new Date(UTC(124, Calendar.SEPTEMBER, 14, 20, 0, 0)),
                RentalState.RESERVED);

        rentalRepository.save(rental);
        Optional<Rental> createdRental = rentalRepository.findById(rental.getId());
        assertFalse(createdRental.isPresent());
        assertEquals(rental, createdRental.get());

        rentalRepository.deleteById(rental.getId());
    }

    @Test
    void FetchRentalByItemIdAndRentalItemTypeTest() {
        Optional<Rental> car = rentalRepository.findRentalByItemIdAndRentalItemType(1, RentalItemType.BIKE);
        assertTrue(car.isPresent());
    }

    @Test
    void RentalItemReservedDuringDesiredTimeframeTest() {

        Date startTime = new Date(UTC(124, Calendar.SEPTEMBER, 13, 10, 0, 0));
        Date endTime = new Date(UTC(124, Calendar.SEPTEMBER, 13, 18, 0, 0));

        List<Rental> rentals = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalStateIsAndStartDateBeforeAndEndDateAfter(1, RentalItemType.BIKE, RentalState.RESERVED, endTime, startTime);

        assertEquals(1, rentals.size());
    }

    @Test
    void RentalItemReservedDueToStartingBeforeExistingReservationEndsTest() {

        Date startTime = new Date(UTC(124, Calendar.SEPTEMBER, 14, 10, 0, 0));
        Date endTime = new Date(UTC(124, Calendar.SEPTEMBER, 15, 10, 0, 0));

        List<Rental> rentals = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalStateIsAndStartDateBeforeAndEndDateAfter(1, RentalItemType.BIKE, RentalState.RESERVED, endTime, startTime);

        assertEquals(1, rentals.size());
    }

    @Test
    void RentalItemReservedDueToEndingAfterExistingReservationStartsTest() {

        Date startTime = new Date(UTC(124, Calendar.SEPTEMBER, 11, 10, 0, 0));
        Date endTime = new Date(UTC(124, Calendar.SEPTEMBER, 12, 20, 0, 0));

        List<Rental> rentals = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalStateIsAndStartDateBeforeAndEndDateAfter(1, RentalItemType.BIKE, RentalState.RESERVED, endTime, startTime);

        assertEquals(1, rentals.size());
    }

    @Test
    void RentalItemAvailableForRentTest() {

        Date startTime = new Date(UTC(124, Calendar.SEPTEMBER, 17, 10, 0, 0));
        Date endTime = new Date(UTC(124, Calendar.SEPTEMBER, 17, 20, 0, 0));

        List<Rental> rentals = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalStateIsAndStartDateBeforeAndEndDateAfter(1, RentalItemType.BIKE, RentalState.RESERVED, endTime, startTime);

        assertEquals(0, rentals.size());
    }

    @Test
    void FetchReservationsInReservedStateTest() {
        Optional<Rental> rentedBike  = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalState(1, RentalItemType.BIKE, RentalState.RESERVED);

        assertTrue(rentedBike.isPresent());assertTrue(rentedBike.isPresent());
    }

    @Test
    void FetchReservationsInReturnedStateTest() {
        Rental rental = new Rental(
                1,
                RentalItemType.BIKE,
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 10, 0, 0)),
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 20, 0, 0)),
                RentalState.RETURNED);
        rentalRepository.save(rental);

        Optional<Rental> rentedBike  = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalState(1, RentalItemType.BIKE, RentalState.RETURNED);

        assertTrue(rentedBike.isPresent());
        rentalRepository.deleteById(rental.getId());
    }

    @Test
    void FetchReservationsInRentedStateTest() {
        Rental rental = new Rental(
                1,
                RentalItemType.BIKE,
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 10, 0, 0)),
                new Date(UTC(124, Calendar.SEPTEMBER, 10, 20, 0, 0)),
                RentalState.RENTED);
        rentalRepository.save(rental);

        Optional<Rental> rentedBike = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalState(1, RentalItemType.BIKE, RentalState.RENTED);
        assertTrue(rentedBike.isPresent());

        rentalRepository.deleteById(rental.getId());
    }

}
