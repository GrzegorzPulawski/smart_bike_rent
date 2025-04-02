package com.smart_bike_rent.service;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.smart_bike_rent.entity.equipment.Equipment;

import com.smart_bike_rent.exception.EquipmentNotFoundException;
import com.smart_bike_rent.repositories.EquipmentRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;


@Service
public class EquipmentBarcodeService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentBarcodeService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional
    public byte[] generateAndSaveBarcode(Long bikeId) throws IOException {
        // 1. Get Equipment by ID
        Equipment equipment = equipmentRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike not found with ID: " + bikeId));

        // 2. Get barcode value
        String barcodeValue = getBarcodeValue(bikeId);

        // 3. Generate barcode image
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(barcodeValue);
        barcode128.setCodeType(Barcode128.CODE128);
        barcode128.setBarHeight(60f); // Set height for scanner readability
        barcode128.setX(1.5f); // Adjust width of bars

        int width = 300;  // Set barcode width
        int height = 100; // Set barcode height

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);

        // 4. Draw Barcode as a String (because iText Image is not compatible)
        Font font = new Font("Arial", Font.BOLD, 24);
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = (width - fontMetrics.stringWidth(barcodeValue)) / 2;
        int y = height / 2;
        g2d.drawString(barcodeValue, x, y);

        g2d.dispose();

        // 5. Convert Image to ByteArray
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] barcodeBytes = outputStream.toByteArray();

        // 6. Save barcode to Equipment
        equipment.setBarcodeImage(barcodeBytes);
        equipmentRepository.save(equipment); // Save changes

        return barcodeBytes;
    }

    @Transactional
    public String getBarcodeValue(Long bikeId) {
        Equipment bike = equipmentRepository.findById(bikeId)
                .orElseThrow(() -> new EquipmentNotFoundException(bikeId));

        if (bike.getBarcodeValue() == null) {
            bike.setBarcodeValue(generateBarcode(bikeId));
            equipmentRepository.save(bike);
        }
        return bike.getBarcodeValue();
    }

    private String generateBarcode(Long bikeId) {
        String randomSuffix = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
        return String.format("BIKE-%03d-%s", bikeId, randomSuffix); // np. "BIKE-042-AB3D"
    }

}