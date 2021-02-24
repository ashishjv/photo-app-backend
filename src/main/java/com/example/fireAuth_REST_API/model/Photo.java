package com.example.fireAuth_REST_API.model;

import com.example.fireAuth_REST_API.validation.ValidateEmail;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

public class Photo {
    private String id;
    @Indexed
    private String albumId;
    private String photoUrl;
//    @ValidateEmail
    private String createdBy;
    private LocalDate dateCreated;

    public Photo(String albumId, String photoUrl, String createdBy, LocalDate dateCreated) {
        this.albumId = albumId;
        this.photoUrl = photoUrl;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
}
