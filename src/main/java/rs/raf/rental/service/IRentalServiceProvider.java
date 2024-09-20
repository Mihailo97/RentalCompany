package rs.raf.rental.service;

import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.service.exception.UnableToCalculatePriceException;
import rs.raf.rental.service.exception.UnableToRentException;

public interface IRentalServiceProvider {

    public RentalDTO rent(RentalDTO rental) throws UnableToRentException;

    public double calculatePrice(RentalDTO rental) throws UnableToCalculatePriceException;
}
