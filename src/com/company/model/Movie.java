package com.company.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Movie implements Serializable {
    private static final long serialVersionUID = 0;
    private static int counter;
    transient private final SimpleDateFormat simpleDateFormat;
    private int id;
    private String name;
    private Calendar movieDay;
    private List<Ticket> ticketList;

    {
        simpleDateFormat = new SimpleDateFormat("dd.MM.y");
    }

    public Movie(String name, Calendar movieDay, List<Ticket> ticketList) {
        id = ++counter;
        this.name = name;
        this.movieDay = movieDay;
        this.ticketList = ticketList;
        for (Ticket ticket : ticketList) {
            ticket.setMovie(id + " " + this.name);
        }
    }

    public Movie(String name, String movieDay) {
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String date() {
        return simpleDateFormat.format(movieDay.getTime());
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }


    public void setMovieDay(Calendar movieDay) {
        this.movieDay = movieDay;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append("; name=").append(name);
        sb.append("; date=").append(date());
        sb.append("; ticketList=").append(ticketList);
        return sb.toString();
    }
}
