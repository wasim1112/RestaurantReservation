package app.business.restaurant_reservation.dto;

import lombok.Data;


@Data
public class SearchFoodTableSittingDto {
    private Long id;
    private Long tableNumber ;
    private Long numberOfChairs ;
}
