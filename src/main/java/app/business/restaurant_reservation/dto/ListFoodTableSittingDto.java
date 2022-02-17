package app.business.restaurant_reservation.dto;

import lombok.Data;


@Data
public class ListFoodTableSittingDto {
    private Long id;
    private int tableNumber;
    private int number_of_chairs;
}
