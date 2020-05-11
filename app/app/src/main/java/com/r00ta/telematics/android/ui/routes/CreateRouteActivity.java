package com.r00ta.telematics.android.ui.routes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.r00ta.telematics.android.ClientConfig;
import com.r00ta.telematics.android.R;
import com.r00ta.telematics.android.network.AuthManager;
import com.r00ta.telematics.android.network.HttpRequestProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateRouteActivity extends AppCompatActivity {
    private static final String TAG = "CreateRouteActivity";

    @BindView(R.id.start_route_label)
    EditText startRouteLabel;
    @BindView(R.id.to_route_label)
    EditText toRouteLabel;
    @BindView(R.id.available_as_driver_checkbox)
    CheckBox availableAsDriverCheckbox;
    @BindView(R.id.available_as_passenger_checkbox)
    CheckBox availableAsPassengerCheckbox;
    @BindView(R.id.flexibility_on_the_route)
    CheckBox flexibilityOnTheRouteCheckbox;
    @BindView(R.id.driver_radius)
    EditText driverRadius;
    @BindView(R.id.driver_flexibility_on_the_route)
    EditText driverFlexibilityOnTheRoute;
    @BindView(R.id.route_day_monday)
    CheckBox routeDayMonday;
    @BindView(R.id.route_day_tuesday)
    CheckBox routeDayTuesday;
    @BindView(R.id.route_day_wednesday)
    CheckBox routeDayWednesday;
    @BindView(R.id.route_day_thursday)
    CheckBox routeDayThursday;
    @BindView(R.id.route_day_friday)
    CheckBox routeDayFriday;
    @BindView(R.id.route_day_saturday)
    CheckBox routeDaySaturday;
    @BindView(R.id.route_day_sunday)
    CheckBox routeDaySunday;

    @BindView(R.id.btn_create_route)
    Button btnCreateRoute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        ButterKnife.bind(this);

        setDriverCheckboxListener();

        btnCreateRoute.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickCreateRoute();
            }
        });
    }

    public void onClickCreateRoute() {
        Log.d(TAG, "Create route btn clicked");

        if (!validate()) {
            onCreateFailedFormatParameters();
            return;
        }

        btnCreateRoute.setEnabled(false);


        String startLabel = startRouteLabel.getText().toString();
        String toLabel = toRouteLabel.getText().toString();

        Integer flexibilityOnRoute = null;
        Integer radiusStart = null;
        Boolean flexibilityOnTheRoute = null;

        boolean availableAsPassenger = availableAsPassengerCheckbox.isChecked();
        boolean availableAsDriver = availableAsDriverCheckbox.isChecked();

        if (availableAsDriver) {
            String flexibilityString = driverFlexibilityOnTheRoute.getText().toString();
            String radiusStartString = driverRadius.getText().toString();
            flexibilityOnRoute = Integer.parseInt(flexibilityString.equals("") ? "0" : flexibilityString);
            radiusStart =  Integer.parseInt(radiusStartString.equals("") ? "0" : radiusStartString);
            flexibilityOnTheRoute = flexibilityOnTheRouteCheckbox.isChecked();
        }

        JSONArray days = new JSONArray();

        if (routeDayMonday.isChecked()) {
            days.put("MONDAY");
        }
        if (routeDayTuesday.isChecked()) {
            days.put("TUESDAY");
        }
        if (routeDayWednesday.isChecked()) {
            days.put("WEDNESDAY");
        }
        if (routeDayThursday.isChecked()) {
            days.put("THURSDAY");
        }
        if (routeDayFriday.isChecked()) {
            days.put("FRIDAY");
        }
        if (routeDaySaturday.isChecked()) {
            days.put("SATURDAY");
        }
        if (routeDaySunday.isChecked()) {
            days.put("SUNDAY");
        }

        String url = ClientConfig.BASE_HOST + ":1339/users/" + AuthManager.getInstance(getApplicationContext()).getUserId() + "/routes";
        JSONObject body = null;
        try {
            body = new JSONObject().put("days", days)
                    .put("availableAsPassenger", availableAsPassenger)
                    .put("startPositionUserLabel", startLabel)
                    .put("endPositionUserLabel", toLabel)
                    .put("driverPreferences", new JSONObject().put("availableAsDriver", availableAsDriver).put("radiusStartPoint", radiusStart).put("flexibility", flexibilityOnRoute).put("onTheRouteOption", flexibilityOnTheRoute));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, body.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        onCreateSuccess();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        onCreateFailed();
                        Log.i(TAG, "CREATE FAILED");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + AuthManager.getInstance(getApplicationContext()).getJwtToken());
                return headers;
            }
        };
        HttpRequestProvider.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void setDriverCheckboxListener() {
        setupCheckDriverFields();
        availableAsDriverCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setupCheckDriverFields();
            }
        });
    }

    public void setupCheckDriverFields(){
        boolean availableAsDriver = availableAsDriverCheckbox.isChecked();
        driverRadius.setEnabled(availableAsDriver);
        flexibilityOnTheRouteCheckbox.setEnabled(availableAsDriver);
        driverFlexibilityOnTheRoute.setEnabled(availableAsDriver);
    }

    public void onCreateSuccess() {
        finish();
    }

    public void onCreateFailedFormatParameters() {
        btnCreateRoute.setEnabled(true);
    }

    public void onCreateFailed() {
        Toast.makeText(getBaseContext(), "Create route failed", Toast.LENGTH_LONG).show();

        btnCreateRoute.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        if (!availableAsDriverCheckbox.isChecked()){
            return true;
        }

        String flexibilityString = driverFlexibilityOnTheRoute.getText().toString();
        String radiusStartString = driverRadius.getText().toString();
        Integer flexibilityOnRoute = Integer.parseInt(flexibilityString.equals("") ? "0" : flexibilityString);
        Integer radiusStart = Integer.parseInt(radiusStartString.equals("") ? "0" : radiusStartString);

        if (flexibilityOnRoute < 0) {
            driverFlexibilityOnTheRoute.setError("Flexibility can't be negative.");
            valid = false;
        } else {
            driverFlexibilityOnTheRoute.setError(null);
        }

        if (radiusStart < 0) {
            driverRadius.setError("Radius start can't be negative.");
            valid = false;
        } else {
            driverRadius.setError(null);
        }

        return valid;
    }
}
