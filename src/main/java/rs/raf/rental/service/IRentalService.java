package rs.raf.rental.service;

import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.model.RentalItemType;
import rs.raf.rental.service.exception.InvalidRentalItemTypeException;
import rs.raf.rental.service.exception.UnableToCalculatePriceException;
import rs.raf.rental.service.exception.UnableToRentException;

public interface IRentalService {

    public RentalDTO rent(RentalItemType rentalItemType, RentalDTO rentalDTO) throws InvalidRentalItemTypeException, UnableToRentException;

    public double calculatePrice(RentalItemType rentalItemType, RentalDTO rentalDTO) throws UnableToCalculatePriceException;
}
