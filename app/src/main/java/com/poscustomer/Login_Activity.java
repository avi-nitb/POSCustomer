package com.poscustomer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.poscustomer.Application.MyApp;
import com.poscustomer.Model.RestUser;
import com.poscustomer.Utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;


public class Login_Activity extends CustomActivity implements CustomActivity.ResponseCallback {

    private TextView txt_remember;
    private EditText edt_email, edt_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setResponseListener(this);
        setupuiElement();

//        if(MyApp.getStatus(AppConstants.IS_LOGGED)){
//            startActivity(new Intent(getContext(),MainActivity.class));
//            finish();
//        }


    }

    private void setupuiElement() {
        setTouchNClick(R.id.btn_login);
        setTouchNClick(R.id.txt_remember);
        setTouchNClick(R.id.txt_forgot);
        setTouchNClick(R.id.register);
        setTouchNClick(R.id.logo_login);

        txt_remember = (TextView) findViewById(R.id.txt_remember);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);

        if (MyApp.getStatus(AppConstants.IS_REMEMBER)) {
            edt_email.setText(MyApp.getSharedPrefString(AppConstants.EMAIL));
            edt_password.setText(MyApp.getSharedPrefString(AppConstants.PASSWORD));
            txt_remember.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_2, 0, 0, 0);
        } else {
            txt_remember.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_3, 0, 0, 0);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_login) {
            if (TextUtils.isEmpty(edt_email.getText().toString())) {
                edt_email.setError("Enter Account Id");
                return;
            }
            if (TextUtils.isEmpty(edt_password.getText().toString())) {
                edt_password.setError("Enter Password");
                return;
            }
            loginUser();
            //startActivity(new Intent(getContext(), MainActivity.class));

        } else if (v.getId() == R.id.logo_login) {
            startActivity(new Intent(getContext(), YoutubeActivity.class));
        } else if (v.getId() == R.id.txt_forgot) {
            startActivity(new Intent(this, NotificationActivity.class));
        } else if (v.getId() == R.id.register) {
            startActivity(new Intent(getContext(), Register_Activity.class));
        } else if (v.getId() == R.id.txt_remember) {
            if (MyApp.getStatus(AppConstants.IS_REMEMBER)) {
                MyApp.setStatus(AppConstants.IS_REMEMBER, !MyApp.getStatus(AppConstants.IS_REMEMBER));
                txt_remember.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_3, 0, 0, 0);
            } else {
                MyApp.setStatus(AppConstants.IS_REMEMBER, !MyApp.getStatus(AppConstants.IS_REMEMBER));
                txt_remember.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_2, 0, 0, 0);
            }
        }
    }

    private void loginUser() {


        RequestParams p = new RequestParams();

        p.put("task", "user_login");


        p.put("password", edt_password.getText().toString());

        p.put("email", edt_email.getText().toString());


        postCall(getContext(), AppConstants.BASE_URL, p, "Signing in...", 1);
    }

    private Context getContext() {
        return Login_Activity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        Log.d("response", o.toString());
        if (o.optInt("status") == 1) {

            MyApp.showMassage(getContext(), "Login success");
            RestUser u = new Gson().fromJson(o.toString(), RestUser.class);
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
