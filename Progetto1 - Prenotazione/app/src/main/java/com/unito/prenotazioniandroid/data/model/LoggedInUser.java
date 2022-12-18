package com.unito.prenotazioniandroid.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    private static boolean userExists = false;

    public static boolean getExistingUser() {
        return userExists;
    }

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
        userExists = true;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
