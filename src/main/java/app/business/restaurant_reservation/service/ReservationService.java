package app.business.restaurant_reservation.service;


import app.business.restaurant_reservation.domain.Reservation;
import app.business.restaurant_reservation.dto.*;
import app.business.restaurant_reservation.repository.ReservationRepository;
import app.general.common.utils.CryptoUtils;
import app.general.common.utils.GeneralUtils;
import app.general.common.utils.Validators;
import app.iam.user.repository.TableAvailableProjection;
import app.iam.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private Validators validators;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptoUtils cryptoUtils;

    @Autowired
    private GeneralUtils generalUtils;



    public void reserve(CreateReservationDto dto){
        Reservation Reservation = new Reservation();
        reservationRepository.save(Reservation);

    }


    public Page<ListReservationDto> getAll(SearchReservationDto search, Pageable pageable){
        Specification<Reservation> spec = Specification.where((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            return cb.and(predicates.toArray(new Predicate[] {}));
        });
        return reservationRepository.findAll(spec, pageable).map(element -> modelMapper.map(element, ListReservationDto.class));
    }

    public ViewReservationDto getOne(long id) {
        return reservationRepository.findById(id).map(element -> {
            ViewReservationDto dto = modelMapper.map(element, ViewReservationDto.class);


            return dto;
        }).get();
    }

    public ResponseEntity<?> tablesAvailableForReservation(SearchFoodTableAllowDto search){
        TableAvailableProjection result =reservationRepository.tablesAvailableForReservation(search.getNumberOfChairsNeeded(),search.getFromTime(),search.getToTime()).orElse(null);
        Reservation reservation = new Reservation();
        if(result != null) {
            reservation.setCustomerGroupName(search.getCustomerGroupName());
            reservation.setNumberOfGroup(search.getNumberOfChairsNeeded());
            reservation.setFromTime(search.getFromTime());
            reservation.setToTime(search.getToTime());
            reservation.setNumberOfChairs(result.getNumberOfChairs());
            reservation.setTableNumber(result.getTableNumber());
            reservation = reservationRepository.save(reservation);
        }

        return ResponseEntity
                .ok()
                .body(result != null ? reservation : "we can not make this reservation in this time");
    }


}
