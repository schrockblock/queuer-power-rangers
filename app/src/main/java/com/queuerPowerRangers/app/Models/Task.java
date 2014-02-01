package com.queuerPowerRangers.app.Models;

import android.content.Context;

import com.queuerPowerRangers.app.Databases.TaskDataSource;

import java.util.Date;

/**
 * Created by Rotios on 1/17/14.
 */
public class Task {
    private int localId;
    private int id;
    private int project_id;
    private int order;

    private boolean finished;

    private String name;

    private Date created_at;
    private Date updated_at;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;

    public Task() {}

    public Task(Context context, int id, String name, int project_id, int order, boolean finished, Date created_at, Date updated_at) {
        this.id = id;
        this.context = context;
        this.name = name;
        this.project_id = project_id;
        this.order = order;
        this.finished = finished;
        this.created_at = created_at;
        this.updated_at = updated_at;

        TaskDataSource dataSource = new TaskDataSource(context);
        dataSource.open();
        setLocalId(dataSource.createTask(name, project_id, id, order, finished).localId);
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
}