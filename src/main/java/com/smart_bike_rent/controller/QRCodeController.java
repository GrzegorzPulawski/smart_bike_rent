package com.smart_bike_rent.controller;

import com.google.zxing.WriterException;
import com.smart_bike_rent.service.QRCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {
    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    /**
     * Generates QR code data AND image (Base64 encoded)
     */
    @GetMapping("/generate")
    public ResponseEntity<QRCodeService.QRCodeResponse> generateQRCode(
            @RequestParam String idBike) throws WriterException, IOException {
        QRCodeService.QRCodeResponse response = qrCodeService.generateQRCode(idBike);
        return ResponseEntity.ok(response);
    }

    /**
     * Alternative: Directly returns the QR code image (PNG)
     */
    @GetMapping(value = "/generate-image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCodeImage(
            @RequestParam String idBike) throws WriterException, IOException {
        QRCodeService.QRCodeResponse response = qrCodeService.generateQRCode(idBike);
        byte[] imageBytes = Base64.getDecoder().decode(response.getQrCodeImage());
        return ResponseEntity.ok(imageBytes);
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockBike(
            @RequestParam String idBike,
            @RequestParam String token) {
        boolean isUnlocked = qrCodeService.validateAndUnlockBike(idBike, token);
        return isUnlocked ?
                ResponseEntity.ok("Bike unlocked successfully") :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}