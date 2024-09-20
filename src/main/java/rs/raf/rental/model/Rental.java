package rs.raf.rental.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@ToString
@NoArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long itemId;

    private RentalItemType rentalItemType;

    private Date startDate;

    private Date endDate;

    private RentalState rentalState;

    public Rental(long itemId, RentalItemType rentalItemType, Date startDate, Date endDate, RentalState rentalState) {
        this.itemId = itemId;
        this.rentalItemType = rentalItemType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalState = rentalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return Objects.equals(getItemId(), rental.getItemId()) && getRentalItemType() == rental.getRentalItemType() && Objects.equals(getStartDate(), rental.getStartDate()) && Objects.equals(getEndDate(), rental.getEndDate()) && getRentalState() == rental.getRentalState();
    }
}
