package app.business.restaurant_reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class ViewReservationDto {
    private Long id;
    private String customerGroupName;
    private int numberOfGroup ;
    private int tableNumber;
    private int numberOfChairs;
    private Instant fromTime;
    private Instant toTime;
    private String createdBy;
    private Instant createdAt;
}
