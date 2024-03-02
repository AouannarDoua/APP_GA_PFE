package com.example.app_ga_pfe;

public class EmploiTempsClass {
    private int id;
    private int RadioButton;
    private String Matiere1;
    private String Matiere2;
    private String Matiere3;
    private String Matiere4;

    public EmploiTempsClass(String Matiere1, String Matiere2,String Matiere3,String Matiere4) {
        this.id = id;
        this.Matiere1 = Matiere1;
        this.Matiere2 = Matiere2;
        this.Matiere3 = Matiere3;
        this.Matiere4 = Matiere4;
    }
    public int getId() {
        return id;
    }

    public String getMatiere1() {
        return Matiere1;
    }

    public String getMatiere2() {
        return Matiere2;
    }
    public String getMatiere3() {
        return Matiere3;
    }
    public String getMatiere4() {
        return Matiere4;
    }
}
