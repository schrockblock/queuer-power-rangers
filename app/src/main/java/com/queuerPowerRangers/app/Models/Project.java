package com.queuerPowerRangers.app.Models;

import android.content.Context;

import com.queuerPowerRangers.app.datasource.ProjectDataSource;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Rotios on 1/15/14.
 */
    public class Project {
    private int id;
    private int localId;
    private String title;
    private int color;


    public Project(Context context, int id, String title) throws SQLException {
        this.id = id;
        this.title = title;

        ProjectDataSource projectDataSource = new ProjectDataSource(context);
        projectDataSource.open();
        localId = projectDataSource.createProject(title, 0, id, new Date(), new Date()).localId;
        projectDataSource.close();
    }

    public Project(){ }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}