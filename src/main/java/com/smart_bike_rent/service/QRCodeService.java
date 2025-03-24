package com.smart_bike_rent.service;

import com.google.zxing.WriterException;
import com.smart_bike_rent.QRCodeImageGeneration;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QRCodeService {

    // Stores valid tokens (bikeId -> token)
    private final Map<String, String> validTokens = new HashMap<>();
    private final QRCodeImageGeneration qrCodeGenerator = new QRCodeImageGeneration();

    /**
     * Generates QR code data AND the QR code image (Base64) for a bike.
     */
    public QRCodeResponse generateQRCode(String bikeId) throws WriterException, IOException {
        String token = generateUnlockToken(bikeId);
        String qrCodeData = bikeId + ":" + token;  // Format: "BIKE123:TOKEN_ABC"

        // Store the token for later validation
        validTokens.put(bikeId, token);

        // Generate QR code image (Base64)
        String qrCodeImage = qrCodeGenerator.generateQRCodeImage(qrCodeData);

        return new QRCodeResponse(qrCodeData, qrCodeImage);
    }

    /**
     * Validates the token and unlocks the bike.
     */
    public boolean validateAndUnlockBike(String bikeId, String token) {
        if (isValidToken(bikeId, token)) {
            // Add your bike unlocking logic here (e.g., call a BikeService)
            validTokens.remove(bikeId);  // Token is single-use
            return true;
        }
        return false;
    }

    private String generateUnlockToken(String bikeId) {
        return UUID.randomUUID().toString();
    }

    private boolean isValidToken(String bikeId, String token) {
        String validToken = validTokens.get(bikeId);
        return validToken != null && validToken.equals(token);
    }

    // Helper class to return both QR code data and image
    public static class QRCodeResponse {
        private final String qrCodeData;
        private final String qrCodeImage;  // Base64

        public QRCodeResponse(String qrCodeData, String qrCodeImage) {
            this.qrCodeData = qrCodeData;
            this.qrCodeImage = qrCodeImage;
        }

        // Getters
        public String getQrCodeData() { return qrCodeData; }
        public String getQrCodeImage() { return qrCodeImage; }
    }
}