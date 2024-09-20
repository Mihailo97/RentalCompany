package rs.raf.rental.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import rs.raf.rental.model.Car;
import rs.raf.rental.model.CarType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarRepositoryTest {
    @Autowired
    private ICarRepository carRepository;

    @Test
    void CreateCarTest() {
        Car car = new Car("Renault Clio", 4000, CarType.HATCHBACK);
        carRepository.save(car);

        Optional<Car> createdCar = carRepository.findById(car.getId());
        assertTrue(createdCar.isPresent());
        assertEquals(car, createdCar.get());

        carRepository.deleteById(car.getId());
    }

    @Test
    void FindCarByNameTest() {
        Optional<Car> car = carRepository.findCarByName("Honda Accord");
        assertTrue(car.isPresent());
    }
}
