package com.jayson.QRcode_app.repository;

import com.jayson.QRcode_app.models.QRCode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QRCodeRepository extends MongoRepository<QRCode, String> {
}

