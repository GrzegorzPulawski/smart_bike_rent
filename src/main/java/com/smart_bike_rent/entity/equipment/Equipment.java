package com.smart_bike_rent.entity.equipment;

import com.smart_bike_rent.dto.EquipmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;


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
    private String frameNumber;
    @Enumerated(EnumType.STRING)
    private SizeBike size;

    @Enumerated(EnumType.STRING)
    private TypeBike type;
    @Builder.Default
    private boolean available=true;
    @Builder.Default
    private boolean electric=false;
    @Column( length = 255, name = "price_Equipment")
    private Double priceEquipment;
    @Column(unique = true, length = 20, name="barcode_value")
    private String barcodeValue;



    public EquipmentDTO mapEquipmentToDTO(){
        return new
            EquipmentDTO(idEquipment, this.nameEquipment, this.frameNumber, this.size, this.type, this.available, this.electric, this.priceEquipment, this.barcodeValue);}

}
