package com.github.vicianm.shapp;

public class MovieDbShell extends ShellApplication {

    public static void main(String[] args) throws Exception {
        new MovieDbShell().runConsole();
    }

    private MovieDb db;
    
    public MovieDbShell() {
        reset();
    }
    
    @ShellMethod(description = "resets the application state")
    public void reset() {
        db = new MovieDb();
    }
    
    @ShellMethod(description = "adds new movie to movie database",
            params={"movie name", "release year"})
    public void addMovie(String name, String releaseYear) {
        Movie movie = new Movie(name, Integer.parseInt(releaseYear));
        db.addMovie(movie);
    }
    
    @ShellMethod(description = "adds actor cast in a movie",
            params={"movie name", "actor name", "actor surname"})
    public void addCast(String movieName, String actorName, String actorSurname) {
        Movie m = db.getMovie(movieName);
        if (m == null) {
            System.out.println("Movie '" + movieName + "' not found.");
        } else {
            Actor actor = new Actor(actorName, actorSurname);
            m.addActor(actor);
        }
    }
    
    @ShellMethod(description = "prints the count of movies in database")
    public int getMoviesCount() {
        return db.getMoviesCount();
    }
    
    @ShellMethod(description = "prints the total number of actor appearances",
            params={"actor name", "actor surname"})
    public int getAppearancesCount(String name, String surname) {
        Actor actor = new Actor(name, surname);
        return db.getAppearancesCount(actor);
    }
    
}
