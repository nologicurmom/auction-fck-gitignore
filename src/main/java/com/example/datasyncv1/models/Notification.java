package com.example.datasyncv1.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

@Document(collection = "notification")
public class Notification implements Serializable {
    @Id
    private int idnotification;

    private int idUser;

    private int idUserGagnant;

    private int idEnchere;

    private String description;

    private String dateNotification;

    public Notification() {
    }

    public Notification(int idnotification, int idUser, int idUserGagnant, int idEnchere, String description, String dateNotification) {
        this.idnotification = idnotification;
        this.idUser = idUser;
        this.idUserGagnant = idUserGagnant;
        this.idEnchere = idEnchere;
        this.description = description;
        this.dateNotification = dateNotification;
    }

    public int getIdnotification() {
        return idnotification;
    }

    public void setIdnotification(int idnotification) {
        this.idnotification = idnotification;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUserGagnant() {
        return idUserGagnant;
    }

    public void setIdUserGagnant(int idUserGagnant) {
        this.idUserGagnant = idUserGagnant;
    }

    public int getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(String dateNotification) {
        this.dateNotification = dateNotification;
    }
}
