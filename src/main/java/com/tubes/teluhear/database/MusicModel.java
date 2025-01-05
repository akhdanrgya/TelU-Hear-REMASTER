package com.tubes.teluhear.database;

public class MusicModel {
    private int id;
    private String judul;
    private String artist;
    private String file_path;

    public MusicModel(int id, String judul, String artist, String file_path) {
        this.id = id;
        this.judul = judul;
        this.artist = artist;
        this.file_path = file_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }



}
