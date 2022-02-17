package app.business.restaurant_reservation.dto;

import lombok.Data;

import java.time.Instant;


@Data
public class SearchFoodTableAllowDto {
    private String customerGroupName;
    private int numberOfChairsNeeded;
    private Instant fromTime;
    private Instant toTime;
}
