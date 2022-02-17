package app.business.restaurant_reservation.repository;

import app.business.restaurant_reservation.domain.FoodTableSitting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface FoodTableSittingRepository extends JpaRepository<FoodTableSitting, Long>, JpaSpecificationExecutor<FoodTableSitting> {



}
