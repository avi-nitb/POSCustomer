package com.poscustomer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.loopj.android.http.RequestParams;
import com.poscustomer.Application.MyApp;
import com.poscustomer.Model.RestUser;
import com.poscustomer.Utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Abhishek on 31-03-2017.
 */

public class Register_Activity extends CustomActivity  implements CustomActivity.ResponseCallback {
    EditText Name, Mobile, Address, Email, Password, ConfPassword;
    TextView Register;
    private Double Lat, Long;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private CountryCodePicker ccp;


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
      setResponseListener(this);

        actionBar.setDisplayHomeAsUpEnabled(true);
       /* final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
        Register=(TextView)findViewById(R.id.reg_btn);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Create Account");
        actionBar.setTitle("");
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Toast.makeText(getContext(), "Updated " + ccp.getSelectedCountryName(), Toast.LENGTH_SHORT).show();
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Lat = location.getLatitude();
                Long = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
                return;
            }

        } else {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }




        setupUiElements();
    }

    private void setupUiElements() {
        setTouchNClick(R.id.reg_btn);


        Name=(EditText)findViewById(R.id.et_name);
        Mobile=(EditText)findViewById(R.id.et_mobile);
        Address=(EditText)findViewById(R.id.et_address);
        Email=(EditText)findViewById(R.id.et_email);
        Password=(EditText)findViewById(R.id.et_passwd);
        ConfPassword=(EditText)findViewById(R.id.et_conf_passwd);



    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.reg_btn) {

            if (TextUtils.isEmpty(Name.getText().toString())) {
                Name.setError("Enter Name");
                return;
            }
            if (TextUtils.isEmpty(Mobile.getText().toString())) {
                Mobile.setError("Enter Mobile No");
                return;
            }
            if (TextUtils.isEmpty(Address.getText().toString())) {
                Address.setError("Enter Address");
                return;
            }
            if (TextUtils.isEmpty(Email.getText().toString())) {
                Email.setError("Enter Email");
                return;
            }
            if (TextUtils.isEmpty(Password.getText().toString())) {
                Password.setError("Enter Password");
                return;
            }
            if (TextUtils.isEmpty(ConfPassword.getText().toString())) {
                ConfPassword.setError("Enter Password Again");
                return;
            }

            registerUser();

            //startActivity(new Intent(getContext(), CoordinatorActivity.class));

        }

    }

    private void registerUser() {
        RequestParams p = new RequestParams();

        p.put("task", "register_user");
        p.put("name", Name.getText().toString());
        p.put("email", Email.getText().toString());
        p.put("phone", Mobile.getText().toString());
        p.put("lat", Lat);
        p.put("lng", Long);
        p.put("address", Address.getText().toString());
        p.put("password", Password.getText().toString());

        postCall(getContext(), AppConstants.BASE_URL, p, "Registering...", 1);
    }

    private Context getContext() {
        return Register_Activity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        Log.d("response", o.toString());
        if (o.optInt("status") == 1) {

            MyApp.showMassage(getContext(), "Thank You for joining Us");
            RestUser u = null;

                u = new Gson().fromJson(o.toString(), RestUser.class);

            MyApp.getApplication().writeUser(u);
            startActivity(new Intent(getContext(), MainActivity.class));
            MyApp.setStatus(AppConstants.IS_LOGGED, true);
            finish();
        } else {
            MyApp.popMessage("Error", o.optString("message"), getContext());

        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }
}

