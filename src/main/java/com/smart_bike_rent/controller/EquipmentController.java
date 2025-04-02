package com.smart_bike_rent.controller;

import com.smart_bike_rent.dto.EquipmentDTO;
import com.smart_bike_rent.exception.EquipmentNotExists;
import com.smart_bike_rent.request.CreateEquipmentRequest;
import com.smart_bike_rent.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public void createEquipment(@RequestBody CreateEquipmentRequest createEquipmentRequest){
        log.info("Create equipment" + createEquipmentRequest);
        equipmentService.createEquipment(createEquipmentRequest);
    }
    @GetMapping
    public List<EquipmentDTO> equipmentsList(){
        List<EquipmentDTO> equipmentList = equipmentService.listEquipments();
        log.info("List of equipments has: "+  equipmentList.size() +" positions");
        return equipmentService.listEquipments();
    }
    @DeleteMapping("/delete")
    public void deleteEquipment(@RequestParam Long idEquipment){
        log.info("Equipment is deleted with id: " + idEquipment );
        equipmentService.deleteEquipment(idEquipment);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<EquipmentDTO> getEquipmentById(@PathVariable Long id) {
        try {
            EquipmentDTO equipmentDTO = equipmentService.getEquipmentById(id);
            return ResponseEntity.ok(equipmentDTO); // Return DTO
        } catch (EquipmentNotExists e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        }
    }

}
