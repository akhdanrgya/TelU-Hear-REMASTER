package com.tubes.teluhear.database;

public class PlaylistModel {
    private int id;
    private int user_id;
    private String playlist_name;
    private String image;

    public PlaylistModel(int user_id, String playlist_name, String image, int id) {
        this.user_id = user_id;
        this.playlist_name = playlist_name;
        this.image = image;
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PlaylistModel{" +
                "user_id=" + user_id +
                ", playlist_name='" + playlist_name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


}
