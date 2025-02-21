package com.smart_bike_rent.entity.equipment;

import com.smart_bike_rent.dto.EquipmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipments")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long idEquipment;
    @Column(name ="name_Equipment")
    private String nameEquipment;

    @Column(name = "frame_number")
    private Integer frameNumber;

    private SizeBike size;

    @Column(name = "is_available")
    private boolean available;
    @Column(name = "price_Equipment")
    private Double priceEquipment;


    public EquipmentDTO mapEquipmentToDTO(){return new EquipmentDTO(idEquipment, this.nameEquipment, this.frameNumber, this.size, this.available, this.priceEquipment);}
}
