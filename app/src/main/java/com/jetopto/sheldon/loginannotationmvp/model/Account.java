package com.jetopto.sheldon.loginannotationmvp.model;

import android.support.annotation.NonNull;

public class Account {

    private String email;
    private String password;

    public Account(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
