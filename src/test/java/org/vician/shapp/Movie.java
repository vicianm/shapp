package org.vician.shapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    
    private String name;
    
    private int releaseYear;
    
    private List<Actor> cast;
    
    public Movie(String name, int releaseYear) {
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    
    public List<Actor> getCast() {
        return cast;
    }
    
    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }
    
    public void addActor(Actor actor) {
        if (cast == null) {
            cast = new ArrayList<Actor>();
        }
        cast.add(actor);
    }
    
    public boolean isStarring(Actor actor) {
        for (Actor a : cast) {
            if (a.equals(actor)) {
                return true;
            }
        }
        return false;
    }
    
}
