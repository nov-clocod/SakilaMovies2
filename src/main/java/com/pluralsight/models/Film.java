package com.pluralsight.models;

public class Film {
    private int filmID;
    private String title;
    private String description;
    private int releaseYear;
    private int length;

    public int getFilmID() {
        return filmID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getLength() {
        return length;
    }

    public Film(int filmID, String title, String description, int releaseYear, int length) {
        this.filmID = filmID;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    @Override
    public String toString() {
        return String.format("%-4d %-60s %-60s %-4d %-4d", filmID, title, description, releaseYear, length);
    }
}
