package app.business.restaurant_reservation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
public class FoodTableSitting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Long id;


    private int tableNumber = 0;

    private int number_of_chairs = 0;

}
