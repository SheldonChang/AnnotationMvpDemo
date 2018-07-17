package com.jetopto.sheldon.loginannotationmvp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jetopto.sheldon.loginannotationmvp.model.Account;
import com.jetopto.sheldon.loginannotationmvp.presenter.LoginPresenter;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements ILoginView {

    private static final int REQUEST_LOG_IN = 3388;
    private static final int REQUEST_LOG_OUT = 3389;
    private LoginPresenter mPresenter;

    @SystemService
    protected InputMethodManager mInputMethodManager;

    @ViewById(R.id.email_ac_text_view)
    protected AutoCompleteTextView mEmailAutoComplete;
    @ViewById(R.id.password_edit_text)
    protected EditText mPasswordEditText;
    @ViewById(R.id.login_progress)
    protected ProgressBar mProgressBar;
    @ViewById(R.id.login_container)
    protected View mLoginContainer;
    @ViewById(R.id.logout_container)
    protected View mLogoutContainer;
    @ViewById(R.id.email_textview)
    protected TextView mEmailTextView;
    @ViewById(R.id.email_text_input_layout)
    protected TextInputLayout mEmailTextInputLayout;
    @ViewById(R.id.password_text_input_layout)
    protected TextInputLayout mPasswordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter(this);
    }

    @Click({R.id.sign_in_button, R.id.logout_button})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                if (validLogin()) {
                    mPresenter.doAuthorization(REQUEST_LOG_IN);
                    hideSoftKeyboard();
                }
                break;
            case R.id.logout_button:
                mPresenter.doAuthorization(REQUEST_LOG_OUT);
                break;
        }
    }

    @FocusChange({R.id.email_ac_text_view, R.id.password_edit_text})
    protected void focusChange(EditText view) {
        if (!view.isFocused()) {
            switch (view.getId()) {
                case R.id.email_ac_text_view:
                    mEmailTextInputLayout.setError(null);
                    if (!isEmailValid()) {
                        mEmailTextInputLayout.setError(getString(R.string.error_invalid_email));
                    }
                    break;
                case R.id.password_edit_text:
                    mPasswordTextInputLayout.setError(null);
                    if (!isPasswordValid()) {
                        mPasswordTextInputLayout.setError(getString(R.string.error_invalid_password));
                    }
                    break;
            }
        }
    }


    @Override
    public String getEmail() {
        return mEmailAutoComplete.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public Account getAccount() {
        return new Account(getEmail(), getPassword());
    }

    @Override
    public void success(int requset) {
        if (REQUEST_LOG_IN == requset) {
            mLoginContainer.setVisibility(View.GONE);
            mLogoutContainer.setVisibility(View.VISIBLE);
            mEmailTextView.setText(getEmail());
        } else {
            mLoginContainer.setVisibility(View.VISIBLE);
            mLogoutContainer.setVisibility(View.GONE);
            mEmailTextView.setText("");
        }
    }

    @Override
    public void failed(int requset) {
        //TODO
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (null != view) {
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean validLogin() {
        boolean isVaild = true;
        if (!isEmailValid()) {
            mEmailTextInputLayout.setError(getString(R.string.error_invalid_email));
            isVaild = false;
        }
        if (!isPasswordValid()) {
            mPasswordTextInputLayout.setError(getString(R.string.error_invalid_password));
            isVaild = false;
        }
        return isVaild;
    }

    private boolean isEmailValid() {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(getEmail());
        return matcher.matches();
    }

    private boolean isPasswordValid() {
        int minLineth = 6;
        String password = getPassword();
        return !password.isEmpty() && password.length() >= minLineth;
    }
}
