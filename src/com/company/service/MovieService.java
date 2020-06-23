package com.company.service;

import com.company.model.Movie;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieService {
    private static HashMap<Integer, Movie> moviesHashMap;
    private File moviesDataBase = new File("MoviesDataBase.txt");
    private TicketService ticketService = new TicketService();
    private Scanner scanner = new Scanner(System.in);
    private Movie createMovie;
    private Movie currentMovie;
    private Movie movieDay;

    public void createMovie() {
        String name = scanner.nextLine();
        String movieDay = scanner.nextLine();
        createMovie = new Movie(name, movieDay);
        moviesHashMap.put(Integer.valueOf(currentMovie.getName()), currentMovie);
        System.out.println("Мероприятие создано успешно");
    }


    public void deleteMovie() {
        System.out.println("Введите название фильма:");
        String login = scanner.nextLine();
        Movie movie = moviesHashMap.get(login);
        moviesHashMap.remove(login);
        System.out.println("Фильм удален");
    }

    public void createFileMovies() {
        if (!moviesDataBase.exists()) {
            try {
                moviesDataBase.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<Integer, Movie> getMoviesHashMap() {
        if (moviesHashMap == null) {
            moviesHashMap = new HashMap<>();
            String var;
            try (BufferedReader br = new BufferedReader(new FileReader(moviesDataBase))) {
                while ((var = br.readLine()) != null) {
                    String[] array = var.trim().split("; ");
                    Movie movie = new Movie(
                            getFieldValue(array[1]),
                            convertToDate(getFieldValue(array[2])),
                            ticketService.createListOfPlaces()
                    );
                    moviesHashMap.put(movie.getId(), movie);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return moviesHashMap;
    }

    private Calendar convertToDate(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.y");
        try {
            return new Calendar.Builder().setInstant(simpleDateFormat.parse(str)).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFieldValue(String str) {
        int index = str.indexOf('=');
        return str.substring(index + 1);
    }

    public void rewriteMovie() {
        HashMap<Integer, Movie> var = getMoviesHashMap();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(moviesDataBase))) {
            for (Movie movie : var.values()) {
                bw.write(String
                        .format("id=%s; name=%s; date=%s%n", movie.getId(), movie.getName(), movie.date()));
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showMovieList() {
        getMoviesHashMap();
        for (Movie movie : moviesHashMap.values()) {
            System.out.printf("id - %s, фильм - %s, дата - %s", movie.getId(), movie.getName(), movie.date());
            ticketService.showFreeTicketByIdMovie(movie.getId(), this);
            System.out.println();
        }
    }


    public void changeTheDateShowMovie() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.y");
        System.out.println("Введите id фильма, который хотите отредактировать");
        if (scanner.hasNextInt()) {
            int var = scanner.nextInt();
            if (getMoviesHashMap().containsKey(var)) {
                Movie movie = getMoviesHashMap().get(var);
                System.out.println("Введите новую дату просмотра фильма в формате dd.MM.y");
                String date = new Scanner(System.in).nextLine();
                try {
                    movie.setMovieDay(toCalendar(simpleDateFormat.parse(date)));
                } catch (ParseException e) {
                    System.out.println("Вы ввели в неверном формате дату");
                    changeTheDateShowMovie();
                }
            }
        }
    }

    public Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
