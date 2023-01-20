package com.example.datasyncv1.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "PhotoProduit")
public class PhotoProduit implements Serializable {

    private int idProduit;
    private String photo;

    public PhotoProduit() {
    }

    public PhotoProduit(int idProduit, String photo) {
        this.idProduit = idProduit;
        this.photo = photo;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
