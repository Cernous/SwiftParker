package com.example.swiftpark.ui.profile;
public class Profile {

    public String profile;
    public String email;

    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Profile(String profile, String email) {
        this.profile = profile;
        this.email = email;
    }

}
