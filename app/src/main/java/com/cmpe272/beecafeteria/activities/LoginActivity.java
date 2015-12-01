package com.cmpe272.beecafeteria.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cmpe272.beecafeteria.R;
import com.cmpe272.beecafeteria.base.App;
import com.cmpe272.beecafeteria.modelResponse.PostResponse;
import com.cmpe272.beecafeteria.network.GsonPostRequest;
import com.cmpe272.beecafeteria.network.LoginApiRequests;
import com.cmpe272.beecafeteria.others.SessionManager;
import com.cmpe272.beecafeteria.others.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rushil on 11/12/2015.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(SessionManager.isLoggedIn(LoginActivity.this)){
            onLoginSuccess();
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Utils.isNetworkAvailable(LoginActivity.this))
                    login();
                else
                    Utils.showSnackbarForConnection(coordinatorLayout);
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });



    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String strUsername = _emailText.getText().toString().trim();
        String strPassword = _passwordText.getText().toString().trim();

            final GsonPostRequest gsonPostRequest =
                    LoginApiRequests.postLoginRequest
                            (
                                    new Response.Listener<PostResponse>() {
                                        @Override
                                        public void onResponse(PostResponse dummyObject) {
                                            // Deal with the DummyObject here
                                            //mProgressBar.setVisibility(View.GONE);
                                            //mContent.setVisibility(View.VISIBLE);
                                            ///setData(dummyObject);
                                            _loginButton.setEnabled(true);
                                            SessionManager.createLoginSession(LoginActivity.this,strUsername);
                                            progressDialog.dismiss();
                                            onLoginSuccess();
                                        }
                                    }
                                    ,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Deal with the error here
                                            //mProgressBar.setVisibility(View.GONE);
                                            //mErrorView.setVisibility(View.VISIBLE);
                                            progressDialog.dismiss();
                                            onLoginFailed();
                                        }
                                    },
                                    strUsername, strPassword
                            );

            App.addRequest(gsonPostRequest, TAG);

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                Snackbar.make(coordinatorLayout,"Congratulations you have register successfully!!",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(){
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isEmpty()) {
            _emailText.setError("enter a valid user name");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Remove all network call from stack
        App.cancelAllRequests(TAG);
    }

}
