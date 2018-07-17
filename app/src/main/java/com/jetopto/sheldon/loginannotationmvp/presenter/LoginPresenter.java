package com.jetopto.sheldon.loginannotationmvp.presenter;


import com.jetopto.sheldon.loginannotationmvp.ILoginView;
import com.jetopto.sheldon.loginannotationmvp.model.Account;
import com.jetopto.sheldon.loginannotationmvp.model.LoginChangeListener;
import com.jetopto.sheldon.loginannotationmvp.model.LoginTask;

public class LoginPresenter implements LoginChangeListener {

    private ILoginView mView;
    private LoginTask mTask;

    public LoginPresenter(ILoginView view) {
        mView = view;
    }

    private void success(int request) {
        if (null != mView) {
            mView.success(request);
        }
    }

    private void failed(int request) {
        if (null != mView) {
            mView.failed(request);
        }
    }

    public void doAuthorization(int requestCode) {
        Account depAccount = mView.getAccount();
        Account account = new Account(mView.getEmail(), mView.getPassword());
        mTask = new LoginTask(requestCode, account, this);
        mTask.execute();
    }

    @Override
    public void onTaskRunning() {
        mView.showProgress();
    }

    @Override
    public void onResult(int request, int result) {
        mView.hideProgress();
        switch (result) {
            case LOGIN_SUCCESS:
                success(request);
                break;
            case LOGIN_FAILED:
                failed(request);
                break;
        }
    }
}
