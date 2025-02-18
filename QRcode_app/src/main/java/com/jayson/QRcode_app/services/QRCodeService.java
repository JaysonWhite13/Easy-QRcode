package com.jayson.QRcode_app.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import com.jayson.QRcode_app.models.QRCode;
import com.jayson.QRcode_app.repository.QRCodeRepository;
import org.springframework.stereotype.Service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class QRCodeService {

    QRCodeRepository qrCodeRepository;

    public String generateQRCode(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @PostMapping("/decode/{id}")
    public String decodeQRCodeFromMongo(@PathVariable String id) throws IOException, NotFoundException, ChecksumException, FormatException {
        // Step 1: Fetch the QRCode object from MongoDB
        QRCode qrCode = qrCodeRepository.findById(id).orElseThrow(() -> new RuntimeException("QR Code not found"));

        // Step 2: Decode the Base64-encoded image
        String base64Image = qrCode.getImageUrl().replace("image/png;base64,", ""); // Remove the data prefix
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Step 3: Convert the byte array to a BufferedImage
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        // Step 4: Decode the QR code
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        com.google.zxing.Result result = reader.decode(bitmap);

        // Step 5: Return the decoded text
        return result.getText();  // Return decoded text from the QR code
    }


}
