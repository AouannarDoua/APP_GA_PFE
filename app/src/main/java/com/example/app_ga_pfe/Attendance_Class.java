package com.example.app_ga_pfe;

import java.util.ArrayList;
import java.util.List;

public class Attendance_Class {
        private String nomPrenom;
        private String apogee;
        private List<String> jours;

        public Attendance_Class () {
            this.jours = new ArrayList<>();
        }
    public Attendance_Class (String nomPrenom , String apogee) {
       this.nomPrenom =nomPrenom;
        this. apogee = apogee;
    }
        public String getNomPrenom() {
            return nomPrenom;
        }

        public void setNomPrenom(String nomPrenom) {
            this.nomPrenom = nomPrenom;
        }

        public String getApogee() {
            return apogee;
        }

        public void setApogee(String apogee) {
            this.apogee = apogee;
        }

        public List<String> getJours() {
            return jours;
        }

        public void setJours(List<String> jours) {
            this.jours = jours;
        }

        public void addJour(String jour) {
            this.jours.add(jour);
        }
    }


