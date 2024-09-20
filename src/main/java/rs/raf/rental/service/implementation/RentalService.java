package rs.raf.rental.service.implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.model.RentalItemType;
import rs.raf.rental.service.IRentalService;
import rs.raf.rental.service.IRentalServiceProvider;
import rs.raf.rental.service.exception.InvalidRentalItemTypeException;
import rs.raf.rental.service.exception.UnableToCalculatePriceException;
import rs.raf.rental.service.exception.UnableToRentException;

@Service
public class RentalService implements IRentalService {

    private IRentalServiceProvider bikeServiceProvider;

    private IRentalServiceProvider carServiceProvider;

    public RentalService(@Qualifier("Bike") IRentalServiceProvider bikeServiceProvider, @Qualifier("Car") IRentalServiceProvider carServiceProvider) {
        this.bikeServiceProvider = bikeServiceProvider;
        this.carServiceProvider = carServiceProvider;
    }

    @Override
    public RentalDTO rent(RentalItemType rentalItemType, RentalDTO rentalDTO) throws InvalidRentalItemTypeException, UnableToRentException {
        switch (rentalItemType) {
            case CAR:
                return carServiceProvider.rent(rentalDTO);
            case BIKE:
                return bikeServiceProvider.rent(rentalDTO);
            default:
                throw new InvalidRentalItemTypeException("Invalid rental item type '" + rentalItemType.toString() + "'.");
        }
    }

    @Override
    public double calculatePrice(RentalItemType rentalItemType, RentalDTO rentalDTO) throws UnableToCalculatePriceException  {
        switch (rentalItemType) {
            case CAR:
                return carServiceProvider.calculatePrice(rentalDTO);
            case BIKE:
                return bikeServiceProvider.calculatePrice(rentalDTO);
            default:
                throw new UnableToCalculatePriceException(String.format("Invalid rental item type '%s'.", rentalItemType.toString()));
        }
    }
}
