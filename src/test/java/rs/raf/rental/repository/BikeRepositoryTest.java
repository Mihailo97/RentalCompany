package rs.raf.rental.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import rs.raf.rental.model.Bike;


@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BikeRepositoryTest {

    @Autowired
    private IBikeRepository bikeRepository;

    @Test
    void CreateBikeTest() {
        Bike bike = new Bike("SCOTT ADDICT RC 15", false, 350);
        Bike createdBike = bikeRepository.save(bike);

        assertEquals(bike, createdBike);

        bikeRepository.deleteById(createdBike.getId());
    }
}
