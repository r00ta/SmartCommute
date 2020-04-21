package com.r00ta.telematics.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// replace
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .writeTimeout(20, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(false)
//                .build();
//        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                // signum instead main
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailedFormatParameters();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString().toLowerCase();
        final String password = _passwordText.getText().toString();

        Log.d(password, "CREATE");

//        Handler handler = new Handler();
//
//        Runnable r = new Runnable() {
//            public void run() {
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("email", email);
//                    jsonObject.put("password", password);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                final Runnable oldThis = this;
//                // TODO: Implement your own authentication logic here.
////                AndroidNetworking.post("http://10.0.2.2:8000/loginUser")
////                        .addJSONObjectBody(jsonObject)
////                        .setTag("test")
////                        .build()
////                        .getAsJSONObject(new JSONObjectRequestListener() {
////                            @Override
////                            public void onResponse(JSONObject response) {
////                                Log.d(response.toString(),"LOGIN SUCCESS");
////                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////
////                                SharedPreferences.Editor editor = prefs.edit();
////
////                                // Edit the saved preferences
////                                editor.putString("nameUser", email);
////                                editor.putString("password", password);
////                                editor.commit();
////                                progressDialog.dismiss();
////                                onLoginSuccess();
////                            }
////                            @Override
////                            public void onError(ANError error) {
////                                Log.d(TAG, "onError errorCode : " + error.getErrorCode());
////                                Log.d(TAG, "onError errorBody : " + error.getErrorBody());
////                                progressDialog.dismiss();
////                                onLoginFailed();
////                            }
////                        });
//            }
//        };
        finish();
//        handler.post(
//                r
//        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginFailedFormatParameters() {
        _loginButton.setEnabled(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _emailText.getText().toString().toLowerCase();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _emailText.setError("username cannot be empty");
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
}
