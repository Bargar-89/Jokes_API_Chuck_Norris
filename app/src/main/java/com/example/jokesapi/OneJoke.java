package com.example.jokesapi;

public class OneJoke {

    private String textOfJoke;
    private String numberOfJoke;
    private int id;

    public OneJoke() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberOfJoke() {
        return numberOfJoke;
    }

    public void setNumberOfJoke(String numberOfJoke) {
        this.numberOfJoke = numberOfJoke;
    }

    public String getTextOfJoke() {
        return textOfJoke;
    }

    public void setTextOfJoke(String textOfJoke) {
        this.textOfJoke = textOfJoke;
    }
}
