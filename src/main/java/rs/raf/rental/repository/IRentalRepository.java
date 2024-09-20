package rs.raf.rental.repository;

import org.springframework.data.repository.CrudRepository;
import rs.raf.rental.model.Rental;
import rs.raf.rental.model.RentalItemType;
import rs.raf.rental.model.RentalState;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IRentalRepository extends CrudRepository<Rental, Long> {

    public Optional<Rental> findRentalByItemIdAndRentalItemType(long itemId, RentalItemType rentalItemType);

    public List<Rental> findRentalByItemIdAndRentalItemTypeAndRentalStateIsAndStartDateBeforeAndEndDateAfter(long itemId, RentalItemType rentalItemType, RentalState rentalState, Date startDate, Date endDate);

    public Optional<Rental> findRentalByItemIdAndRentalItemTypeAndRentalState(long itemId, RentalItemType rentalItemType, RentalState rentalState);
}
