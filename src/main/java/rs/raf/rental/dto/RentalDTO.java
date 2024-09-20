package rs.raf.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.rental.model.RentalItemType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {

    @Positive
    private long id;

    @NotNull(message = "Start date cannot be null")
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    private Date endDate;

    private double price;

    public RentalDTO(long id, Date startDate, Date endDate) {
        this.endDate = endDate;
        this.startDate = startDate;
        this.id = id;
        this.price = 0;
    }
}
