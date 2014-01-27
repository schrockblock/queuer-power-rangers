package com.queuerPowerRangers.app.Models;

import android.content.Context;

import com.queuerPowerRangers.app.Databases.ProjectDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rotios on 1/15/14.
 */
public class Project {
    private int localId;
    private int id;
    private String name;
    private int project_id;
    private int order;
    private boolean finished;
    private Date created_at;
    private Date updated_at;
    private int project_color;

    public Project() {}

    public Project(Context context, int id, String name, int project_id, int order, boolean finished, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.project_id = project_id;
        this.order = order;
        this.finished = finished;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.project_color = project_color;

        ProjectDataSource dataSource = new ProjectDataSource(context);
        dataSource.open();
        setLocalId(dataSource.createProject(name, project_id, order, created_at, updated_at).localId);
        dataSource.close();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getProject_color() {
        return project_color;
    }

    public void setProject_color(int project_color) {
        this.project_color = project_color;
    }}