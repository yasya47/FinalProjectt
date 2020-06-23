package com.company.model;

import java.io.Serializable;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 0;
    private static int counter = 0;
    private int id;
    private User user;
    private String movie;
    private int numberOfPlace;
    private int cost;
    private boolean isAvailable = true;


    public Ticket(int numberOfPlace) {
        id = ++counter;
        this.numberOfPlace = numberOfPlace;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


    public int getNumberOfPlace() {
        return numberOfPlace;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        final StringBuilder info = new StringBuilder();
        info.append("id=").append(id);
        info.append(", user=").append(user);
        info.append(", movie=").append(movie);
        info.append(", numberOfPlace=").append(numberOfPlace);
        info.append(", cost=").append(cost);
        info.append(", isAvailable=").append(isAvailable);
        return info.toString();
    }
}
