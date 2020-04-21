package com.r00ta.telematics.android;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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



public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    @BindView(R.id.language_input) EditText _languageText;
    @BindView(R.id.input_email) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailedFormatParameters();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String nameUser = _usernameText.getText().toString().toLowerCase();
        final String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        final String language = _languageText.getText().toString();

        // TODO: Implement your own signup logic here.

        Handler handler = new Handler();
// remove
        onSignupSuccess();
        Runnable r = new Runnable() {
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nameUser", nameUser);
                    jsonObject.put("password", password);
                    jsonObject.put("language", language);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                AndroidNetworking.post("http://10.0.2.2:8000/createUser")
//                        .addJSONObjectBody(jsonObject)
//                        .setTag("test")
//                        .build()
//                        .getAsJSONObject(new JSONObjectRequestListener() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Log.d(response.toString(),"Create SUCCESS");
//                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//                                SharedPreferences.Editor editor = prefs.edit();
//
//                                // Edit the saved preferences
//                                editor.putString("nameUser", nameUser);
//                                editor.putString("password", password);
//                                editor.commit();
//                                progressDialog.dismiss();
//                                onSignupSuccess();
//                            }
//                            @Override
//                            public void onError(ANError error) {
//                                Log.d(TAG, "onError errorCode : " + error.getErrorCode());
//                                Log.d(TAG, "onError errorBody : " + error.getErrorBody());
//                                progressDialog.dismiss();
//                                onSignupFailed();
//                            }
//                        });
            }
        };

        handler.post(
                r
        );

    }



    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onSignupFailedFormatParameters() {
        _signupButton.setEnabled(true);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "A user with this email already exists", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString().toLowerCase();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String language = _languageText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("username cannot be empty");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4  || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        if (language.isEmpty() || (!language.equals("EN") && !language.equals("IT"))) {
            _languageText.setError("Language not accepted");
            valid = false;
        } else {
            _languageText.setError(null);
        }
        return valid;
    }
}