package rs.raf.rental.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.repository.ICarRepository;
import rs.raf.rental.repository.IRentalRepository;
import rs.raf.rental.service.IRentalServiceProvider;

@Service
@Qualifier("Car")
public class CarRentalServiceProvider implements IRentalServiceProvider {

    private ICarRepository carRepository;

    private IRentalRepository rentalRepository;

    @Autowired
    public CarRentalServiceProvider(ICarRepository carRepository, IRentalRepository rentalRepository) {
        this.carRepository = carRepository;
        this.rentalRepository = rentalRepository;
    }

    @Override
    public RentalDTO rent(RentalDTO rental) {
        return null;
    }

    @Override
    public double calculatePrice(RentalDTO rental) {
        return 0;
    }
}
