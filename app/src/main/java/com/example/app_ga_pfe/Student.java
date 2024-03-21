package com.example.app_ga_pfe;

public class Student {
    private String nom;
    private String apogee;

    public Student() {
        // Constructeur par défaut requis par Firebase
    }

    public Student(String nom, String apogee) {
        this.nom = nom;
        this.apogee = apogee;
    }

    // Les getters et les setters sont nécessaires pour Firebase
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApogee() {
        return apogee;
    }

    public void setApogee(String apogee) {
        this.apogee = apogee;
    }

}
