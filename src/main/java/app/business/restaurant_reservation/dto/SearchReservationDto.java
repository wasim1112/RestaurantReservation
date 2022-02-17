package app.business.restaurant_reservation.dto;

import lombok.Data;

import java.time.Instant;


@Data
public class SearchReservationDto {
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
