package com.smart_bike_rent.service;

import com.smart_bike_rent.dto.EquipmentDTO;
import com.smart_bike_rent.request.CreateEquipmentRequest;
import com.smart_bike_rent.entity.equipment.Equipment;
import com.smart_bike_rent.exception.EquipmentAlreadyExistsException;
import com.smart_bike_rent.exception.EquipmentNotExists;
import com.smart_bike_rent.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    public void createEquipment(CreateEquipmentRequest createEquipmentRequest){
        Optional<Equipment> optionalEquipment = equipmentRepository.findByNameEquipment(createEquipmentRequest.getNameEquipment());
        if (optionalEquipment.isEmpty()){
            Equipment equipment = new Equipment();
          equipment.setNameEquipment(createEquipmentRequest.getNameEquipment());
          equipment.setPriceEquipment(createEquipmentRequest.getPriceEquipment());
            equipmentRepository.save(equipment);
        } else {
            throw new EquipmentAlreadyExistsException("Equipment with name: " + createEquipmentRequest.getNameEquipment()+ "already exists. Change name");
        }
    }
    public void deleteEquipment(Long idEquipment){
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(idEquipment);
        if(optionalEquipment.isPresent()) {
            equipmentRepository.deleteById(idEquipment);
        } else {
            throw new EquipmentNotExists("Equipment with id: " +idEquipment + " not exists");
        }
    }
    public List<EquipmentDTO> listEquipments(){
        List<Equipment> equipmentList = equipmentRepository.findAll();
        List<EquipmentDTO> listAllEquipment= new ArrayList<>();
        for (Equipment equipment : equipmentList){
            listAllEquipment.add(equipment.mapEquipmentToDTO());
        }return listAllEquipment;
    }
}
