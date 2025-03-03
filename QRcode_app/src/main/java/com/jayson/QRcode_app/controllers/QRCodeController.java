package com.jayson.QRcode_app.controllers;

import com.jayson.QRcode_app.models.QRCode;
import com.jayson.QRcode_app.repository.QRCodeRepository;
import com.jayson.QRcode_app.services.QRCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/qr")
@Tag(name = "QR Code Operations", description = "Endpoints for generating and saving QR codes")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Operation(summary = "Generate QR Code", description = "Generates a QR code image from the provided text or URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR Code successfully generated"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/generate")
    public String generateQRCode(
            @RequestParam String text,
            @RequestParam(required = false) String fgColor,
            @RequestParam(required = false) String bgColor) throws Exception {

        // Set default colors if not provided
        if (fgColor == null) fgColor = "#000000";  // Default black for foreground
        if (bgColor == null) bgColor = "#ffffff";  // Default white for background

        // Log the received colors for debugging
        System.out.println("text:   " + text);
        System.out.println("Foreground Color: " + fgColor);
        System.out.println("Background Color: " + bgColor);

        return qrCodeService.generateQRCode(text, fgColor, bgColor);
    }



    @Operation(summary = "Save QR Code", description = "Saves the generated QR code to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "QR Code successfully saved"),
            @ApiResponse(responseCode = "400", description = "Invalid QR code data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/save")
    public QRCode saveQRCode(@RequestBody QRCode qrCode) {
        return qrCodeRepository.save(qrCode);
    }

//    @Operation(summary = "Decode QR Code", description = "Decodes a QR code from an uploaded image file and returns the embedded text or URL.")
//    @PostMapping("/decode")
//    public String decodeQRCode(@RequestParam("file") MultipartFile file) throws Exception {
//        return qrCodeService.decodeQRCode(file);
//    }
}
