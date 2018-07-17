package com.jetopto.sheldon.loginannotationmvp.model;

import android.os.AsyncTask;

public class LoginTask extends AsyncTask<Void, Void, Void> {


    private Account mAccount;
    private LoginChangeListener mListener;
    private int mRequest;

    public LoginTask(int request, Account account, LoginChangeListener listener) {
        mAccount = account;
        mListener = listener;
        mRequest = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onTaskRunning();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.onResult(mRequest, LoginChangeListener.LOGIN_SUCCESS);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //TODO run valid account here
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
