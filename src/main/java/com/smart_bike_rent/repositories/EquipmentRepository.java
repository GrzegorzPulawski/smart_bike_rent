package com.smart_bike_rent.repositories;

import com.smart_bike_rent.entity.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
   Optional<Equipment> findByNameEquipment(String nameEquipment);
   Optional<Equipment> findByAvailable(Boolean available);

}
