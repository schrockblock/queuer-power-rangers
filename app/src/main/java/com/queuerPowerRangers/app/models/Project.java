package com.queuerPowerRangers.app.models;

/**
 * Created by Michael on 1/15/14.
 */
public class Project {
    private int id;
    private String title;
    private int color;

    public Project(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {

        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }
}
