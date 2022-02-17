package app.business.restaurant_reservation.dto;

import lombok.Data;


@Data
public class ViewFoodTableAllowDto {
    private int tableNumber;
    private int numberOfChairs;
}
