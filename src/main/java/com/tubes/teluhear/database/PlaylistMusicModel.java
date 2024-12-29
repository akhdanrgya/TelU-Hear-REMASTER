package com.tubes.teluhear.database;

public class PlaylistMusicModel {
    private int id;
    private int idPlaylist;
    private int idMusic;

    public PlaylistMusicModel(int id, int idPlaylist, int idMusic) {
        this.id = id;
        this.idPlaylist = idPlaylist;
        this.idMusic = idMusic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public int getIdMusic() {
        return idMusic;
    }

    public void setIdMusic(int idMusic) {
        this.idMusic = idMusic;
    }

    @Override
    public String toString() {
        return "PlaylistMusicModel{" + "id=" + id + ", idPlaylist=" + idPlaylist + ", idMusic=" + idMusic + '}';
    }

}
