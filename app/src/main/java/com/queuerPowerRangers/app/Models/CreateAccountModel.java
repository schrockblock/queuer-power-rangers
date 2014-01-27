package com.queuerPowerRangers.app.Models;

/**
 * Created by Michael on 1/24/14.
 */
public class CreateAccountModel {

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CreateAccountModel(User user) {

        this.user = user;
    }
}
