package org.vician.shapp;

import java.util.ArrayList;
import java.util.List;

public class MovieDb {
    
    private List<Movie> movies;
    
    public MovieDb() {
        movies = new ArrayList<Movie>();
    }
    
    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    
    public Movie getMovie(String name) {
        for (Movie m : movies) {
            if (name.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }
    
    public int getMoviesCount() {
        return movies.size();
    }
    
    public int getAppearancesCount(Actor actor) {
        int count = 0;
        for (Movie m : movies) {
            if (m.isStarring(actor)) {
                count++;
            }
        }
        return count;
    }
    
}
