package app.business.restaurant_reservation.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Long id;

    @Size(max = 255)
    private String customerGroupName;
    private int numberOfGroup = 0;
    private int tableNumber = 0;
    private int numberOfChairs = 0;
    private Instant fromTime;
    private Instant toTime;

    @CreatedBy
    private String createdBy;

    @NotNull
    @CreatedDate
    private Instant createdAt;

}
