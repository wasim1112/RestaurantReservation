package app.business.restaurant_reservation.dto;

import lombok.Data;

@Data
public class ViewFoodTableSittingDto {
    private Long id;
    private int tableNumber = 0;
    private int number_of_chairs = 0;


}
