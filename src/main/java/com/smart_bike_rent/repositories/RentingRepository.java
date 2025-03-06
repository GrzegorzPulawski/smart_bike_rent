package com.smart_bike_rent.repositories;

import com.smart_bike_rent.entity.renting.Renting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentingRepository extends JpaRepository<Renting, Long> {
    List<Renting> findByDateOfReturnBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Renting> findAllByOrderByDateRentingDesc();

    @Query("SELECT r FROM Renting r WHERE r.idRenting IN :idRentings")
    List<Renting> findByIdRentingsIn(@Param("idRentings") List<Long> idRentings);


    @Query("SELECT r FROM Renting r WHERE r.dateOfReturn > :cutoffTime")
    List<Renting> findByDateOfReturnGreaterThan(LocalDateTime cutoffTime);


}