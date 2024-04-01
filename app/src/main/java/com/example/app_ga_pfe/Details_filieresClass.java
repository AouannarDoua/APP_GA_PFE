package com.example.app_ga_pfe;

public class Details_filieresClass {
    private int id;
    private String Title;
    private String details1;
    private String details2;
    private String S1;
    private String S2;
    private String S3;
    private String S4;

    public Details_filieresClass(String Title, String details1, String details2, String S1, String S2, String S3, String S4) {
        this.Title = Title;
        this.details1 = details1;
        this.details2 = details2;
        this.S1 = S1;
        this.S2 = S2;
        this.S3 = S3;
        this.S4 = S4;
    }

    public String getDetails1() {
        return details1;
    }

    public String getDetails2() {
        return details2;
    }

    public String getS1() {
        return S1;
    }

    public String getS2() {
        return S2;
    }

    public String getS3() {
        return S3;
    }

    public String getS4() {
        return S4;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setDetails1(String details1) {
        this.details1 = details1;
    }

    public void setDetails2(String details2) {
        this.details2 = details2;
    }

    public void setS1(String S1) {
        this.S1 = S1;
    }

    public void setS2(String S2) {
        this.S2 = S2;
    }

    public void setS3(String S3) {
        this.S3 = S3;
    }

    public void setS4(String S4) {
        this.S4 = S4;
    }
}
