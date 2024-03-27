package com.example.app_ga_pfe;

public class Notification {
    private String title;
    private String body;
    private String recipientId; // L'ID de l'étudiant destinataire

    // Constructeur par défaut requis pour Firebase
    public Notification() {}

    // Constructeur avec paramètres pour initialiser les valeurs
    public Notification(String title, String body, String recipientId) {
        this.title = title;
        this.body = body;
        this.recipientId = recipientId;
    }

    // Getters et setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
    public String getCodeApogee() {
        return recipientId; // Dans ce cas, nous renvoyons simplement l'ID du destinataire
    }

}
