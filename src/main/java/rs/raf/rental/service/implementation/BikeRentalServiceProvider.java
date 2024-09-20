package rs.raf.rental.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.model.Bike;
import rs.raf.rental.model.Rental;
import rs.raf.rental.model.RentalItemType;
import rs.raf.rental.model.RentalState;
import rs.raf.rental.repository.IBikeRepository;
import rs.raf.rental.repository.IRentalRepository;
import rs.raf.rental.service.IRentalServiceProvider;
import rs.raf.rental.service.exception.UnableToCalculatePriceException;
import rs.raf.rental.service.exception.UnableToRentException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Qualifier("Bike")
public class BikeRentalServiceProvider implements IRentalServiceProvider {

    private IBikeRepository bikeRepository;

    private IRentalRepository rentalRepository;

    private RentalItemType rentalItemType;

    @Autowired
    public BikeRentalServiceProvider(IBikeRepository bikeRepository, IRentalRepository rentalRepository) {
        this.bikeRepository = bikeRepository;
        this.rentalRepository = rentalRepository;
        this.rentalItemType = RentalItemType.BIKE;
    }

    @Override
    public RentalDTO rent(RentalDTO rental) throws UnableToRentException {

        Optional<Bike> bike = bikeRepository.findById(rental.getId());

        if (!bike.isPresent()) {
           throw new UnableToRentException(String.format("Bike with id %d not found!", rental.getId()));
        }

        Optional<Rental> rentedBike = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalState(rental.getId(), rentalItemType, RentalState.RENTED);

        if (rentedBike.isPresent()) {
            throw new UnableToRentException(String.format("Bike with id %d is already rented!", rental.getId()));
        }

        List<Rental> overlappingRentals = rentalRepository.findRentalByItemIdAndRentalItemTypeAndRentalStateIsAndStartDateBeforeAndEndDateAfter(rental.getId(), rentalItemType, RentalState.RESERVED,  rental.getEndDate(), rental.getStartDate());

        if (!overlappingRentals.isEmpty()) {
            throw new UnableToRentException(String.format("Bike with id %d is already reserved for that period!", rental.getId()));
        }

        try{
            double rentalPrice = calculatePrice(rental);
            rental.setPrice(rentalPrice);
            return rental;
        } catch (UnableToCalculatePriceException e) {
            throw new UnableToRentException(e.getMessage());
        }
    }

    @Override
    public double calculatePrice(RentalDTO rental) throws UnableToCalculatePriceException {
        long differenceInMs = rental.getEndDate().getTime() - rental.getStartDate().getTime();
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMs);
        long differenceInHours = (long) Math.ceil(differenceInMinutes / 60.0);

        Optional<Bike> bike = bikeRepository.findById(rental.getId());

        if (!bike.isPresent()) {
            throw new UnableToCalculatePriceException(String.format("Bike with id %d not found!", rental.getId()));
        }

        return differenceInHours * bike.get().getPrice();
    }
}
