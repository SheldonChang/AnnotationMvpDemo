package com.jetopto.sheldon.loginannotationmvp.model;

public interface LoginChangeListener {

    int LOGIN_SUCCESS = 0;
    int LOGIN_FAILED = -1;

    void onTaskRunning();

    void onResult(int request, int result);
}
