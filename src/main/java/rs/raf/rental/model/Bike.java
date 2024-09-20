package rs.raf.rental.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
@ToString
@NoArgsConstructor
public class Bike {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isElectric;

    private double price;

    public Bike(String name, boolean isElectric, double price) {
        this.name = name;
        this.isElectric = isElectric;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bike)) return false;
        Bike bike = (Bike) o;
        return isElectric() == bike.isElectric() && Double.compare(getPrice(), bike.getPrice()) == 0 && Objects.equals(getName(), bike.getName());
    }
}
