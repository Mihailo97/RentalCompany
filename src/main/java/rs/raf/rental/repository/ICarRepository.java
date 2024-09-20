package rs.raf.rental.repository;

import org.springframework.data.repository.CrudRepository;
import rs.raf.rental.model.Car;
import java.util.Optional;

public interface ICarRepository extends CrudRepository<Car, Long> {

    public Optional<Car> findCarByName(String name);
}
