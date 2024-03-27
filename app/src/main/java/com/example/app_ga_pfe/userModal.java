package com.example.app_ga_pfe;

public class userModal {
    private String username;
        private String profilePictureUrl;
        private String email;

        public userModal(String username, String profilePictureUrl, String email) {
            this.username = username;
            this.profilePictureUrl = profilePictureUrl;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfilePictureUrl() {
            return profilePictureUrl;
        }

        public void setProfilePictureUrl(String profilePictureUrl) {
            this.profilePictureUrl = profilePictureUrl;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

