package com.tubes.teluhear.database;

public class MusicModel {
    private int id;
    private String judul;
    private String artist;
    private String genre;
    private String duration;

    public MusicModel(int id, String judul, String artist, String genre, String duration) {
        this.id = id;
        this.judul = judul;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }



}
