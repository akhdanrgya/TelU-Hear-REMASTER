package com.tubes.teluhear;

public class SessionManager {
    private static SessionManager instance;
    private String username;
    private int id;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void clearSession() {
        this.username = null;
    }

}
