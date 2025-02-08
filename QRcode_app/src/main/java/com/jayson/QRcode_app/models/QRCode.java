package com.jayson.QRcode_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.Date;

@Data
@Document(collection = "qrcodes")
public class QRCode {
    @Id
    @JsonIgnore  // This will hide the field in Swagger UI and the request payload
    private String id;
    private String text;
    private String imageUrl;
    private Date createdAt = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    // No-args constructor (required by Spring Data MongoDB)
    public QRCode() {
        this.createdAt = new Date(); // Automatically set current date when object is created
    }

    // Constructor with arguments (optional, for easier object creation)
    public QRCode(String id, String text, String imageUrl, Date createdAt) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}

