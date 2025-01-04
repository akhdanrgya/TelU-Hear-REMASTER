package com.tubes.teluhear.database;

public class SubsModel {
    public int id;
    public int id_user;

    public SubsModel(int id, int id_user) {
        this.id = id;
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
