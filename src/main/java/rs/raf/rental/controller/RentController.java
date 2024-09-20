package rs.raf.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.rental.dto.RentalDTO;
import rs.raf.rental.model.RentalItemType;
import rs.raf.rental.service.exception.UnableToCalculatePriceException;
import rs.raf.rental.service.implementation.RentalService;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/rent")
public class RentController {

    private final RentalService rentalService;

    @Autowired
    public RentController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/bike/price")
    public ResponseEntity<Double> rentBike(@RequestBody @Valid RentalDTO rentalDTO){
        try {
            return ResponseEntity.ok(rentalService.calculatePrice(RentalItemType.BIKE, rentalDTO));
        } catch (UnableToCalculatePriceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
