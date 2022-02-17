package app.business.restaurant_reservation.repository;

import app.business.restaurant_reservation.domain.Reservation;
import app.business.restaurant_reservation.dto.ViewFoodTableAllowDto;
import app.iam.user.repository.TableAvailableProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    @Transactional
    @Query(value = "  SELECT top 1 \n" +
            "       [table_number] as tableNumber \n" +
            "      ,min([number_of_chairs]) as numberOfChairs\n" +
            "  FROM [food_table_sitting]\n" +
            "  where [number_of_chairs] >= :numberOfChairsNeeded \n" +
            "  and [table_number] not in (select [table_number]  FROM [reservation] \n" +
            " where [from_time] between CAST(:fromTime AS DATETIME2) and CAST(:toTime AS DATETIME2)\n" +
            "   and [to_time] between  CAST(:fromTime AS DATETIME2) and CAST(:toTime AS DATETIME2))\n" +
            "   group by [table_number] order by min([number_of_chairs])", nativeQuery = true)
    Optional<TableAvailableProjection> tablesAvailableForReservation(@Param("numberOfChairsNeeded") int numberOfChairsNeeded, @Param("fromTime") Instant fromTime, @Param("toTime") Instant toTime);

}
