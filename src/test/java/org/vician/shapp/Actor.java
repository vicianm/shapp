package org.vician.shapp;


public class Actor {

    private String name;
    
    private String surname;
    
    public Actor(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Actor)) {
            return false;
        }
        Actor actor = (Actor)o;
        return
               (name == null && actor.name == null
                || (name != null && name.equals(actor.name)))
            && (surname == null && actor.surname == null
                || (surname != null && surname.equals(actor.surname)));
    }

}
