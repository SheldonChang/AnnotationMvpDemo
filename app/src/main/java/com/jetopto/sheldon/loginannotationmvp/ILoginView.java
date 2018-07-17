package com.jetopto.sheldon.loginannotationmvp;

import com.jetopto.sheldon.loginannotationmvp.model.Account;

public interface ILoginView {

    String getEmail();

    String getPassword();

    @Deprecated
    Account getAccount();

    void success(int requestCode);

    void failed(int requestCode);

    void hideProgress();

    void showProgress();
}

