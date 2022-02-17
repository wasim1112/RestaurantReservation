package app.business.restaurant_reservation.controller;

import app.business.restaurant_reservation.dto.ListReservationDto;
import app.business.restaurant_reservation.dto.SearchFoodTableAllowDto;
import app.business.restaurant_reservation.dto.SearchReservationDto;
import app.business.restaurant_reservation.dto.ViewReservationDto;
import app.business.restaurant_reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/reservation")
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

//    @PreAuthorize("hasAuthority('RESERVATION:LIST')")
    @GetMapping
    public Page<ListReservationDto> getAll(SearchReservationDto search, Pageable pageable){
        return reservationService.getAll(search, pageable);
    }

//    @PreAuthorize("hasAuthority('RESERVATION:LIST')")
    @GetMapping("/{id}")
    public ViewReservationDto getOne(@PathVariable("id") long id){
        return reservationService.getOne(id);
    }


//    @PreAuthorize("hasAuthority('RESERVATION:RESERVE')")
    @PostMapping("/reserve")
    public ResponseEntity<?> tablesAvailableForReservation(@RequestBody  SearchFoodTableAllowDto search){
        return reservationService.tablesAvailableForReservation(search);
    }



}
