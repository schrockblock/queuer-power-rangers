package com.queuerPowerRangers.app.Models;

/**
 * Created by Michael on 1/24/14.
 */
public class User {
String password;
    String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String password, String username) {
      this.username=username;
        this.password=password;
    }
}
