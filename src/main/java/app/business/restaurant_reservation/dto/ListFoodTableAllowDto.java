package app.business.restaurant_reservation.dto;

import lombok.Data;

import java.time.Instant;


@Data
public class ListFoodTableAllowDto {
    private int numberOfGroup ;
    private int tableNumber;
    private int numberOfChairs;
    private Instant fromTime;
    private Instant toTime;
}
