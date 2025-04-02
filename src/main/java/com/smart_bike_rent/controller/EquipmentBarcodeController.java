package com.smart_bike_rent.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.Barcode128;
import com.smart_bike_rent.dto.EquipmentDTO;
import com.smart_bike_rent.entity.equipment.Equipment;
import com.smart_bike_rent.security.service.UserAuthProvider;
import com.smart_bike_rent.service.EquipmentBarcodeService;
import com.smart_bike_rent.service.EquipmentService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/equipment/barcode")
public class EquipmentBarcodeController {
    private final EquipmentBarcodeService barcodeService;
    @Autowired
    private EquipmentService equipmentService;



    public EquipmentBarcodeController(EquipmentBarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    @PostMapping("/{bikeId}/generate-barcode")
    public ResponseEntity<String> generateAndSaveBarcode(@PathVariable Long bikeId) throws java.io.IOException {
        try {
            barcodeService.generateAndSaveBarcode(bikeId);
            return ResponseEntity.ok("Barcode generated and saved successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating barcode: " + e.getMessage());
        }
    }
    @GetMapping("/{bikeId}/barcode-image")
    public ResponseEntity<byte[]> getBarcodeImage(@PathVariable Long bikeId) {
        EquipmentDTO equipmentDTO = equipmentService.getEquipmentById(bikeId);


        byte[] barcodeBytes = equipmentDTO.getBarcodeImage();

        if (barcodeBytes == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(barcodeBytes);
    }


    @PostMapping("/{bikeId}/generate")
    public ResponseEntity<String> generateBarcode(@PathVariable Long bikeId) {
        String barcode = barcodeService.getBarcodeValue(bikeId);
        return ResponseEntity.ok(barcode);
    }

    @GetMapping("/{bikeId}")
    public ResponseEntity<String> getBarcode(@PathVariable Long bikeId) {
        String barcode = barcodeService.getBarcodeValue(bikeId);
        return ResponseEntity.ok(barcode);
    }

}