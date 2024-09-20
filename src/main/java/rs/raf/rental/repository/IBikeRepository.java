package rs.raf.rental.repository;

import org.springframework.data.repository.CrudRepository;
import rs.raf.rental.model.Bike;
import java.util.Optional;

public interface IBikeRepository extends CrudRepository<Bike, Long> {

    public Optional<Bike> findBikeByName(String name);
}
