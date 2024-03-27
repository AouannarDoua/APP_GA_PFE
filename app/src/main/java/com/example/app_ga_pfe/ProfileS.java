package com.example.app_ga_pfe;

public class ProfileS {
    private String fullName;
    private String gmail;
    private String profileImageUrl;


    public ProfileS(){

    }

    public ProfileS(String fullName, String gmail,String profileImageUrl) {
        this.fullName = fullName;
        this.gmail = gmail;
        this.profileImageUrl = profileImageUrl ;


    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
