package com.poscustomer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.poscustomer.Adapter.ResturantAdapter;
import com.poscustomer.Application.MyApp;
import com.poscustomer.Model.ResturantData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Abhishek on 24-04-2017.
 */

public class ResturantList extends CustomActivity implements CustomActivity.ResponseCallback{

    private RecyclerView listofresturants;
    private ResturantAdapter adapter;
    private ArrayList<ResturantData.DataArray> listdata;
    private Toolbar toolbar_title;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.resturant_list_main);

        toolbar_title = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_title);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar_title.findViewById(R.id.toolbar_title);
        mTitle.setText("Nearby Resturant");
        actionBar.setTitle("");

        setResponseListener(this);
        listdata=new ArrayList<>();
        listofresturants=(RecyclerView) findViewById(R.id.listofresturants);
        adapter = new ResturantAdapter(listdata,this);
        LinearLayoutManager llm
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        listofresturants.setLayoutManager(llm);
        listofresturants.setAdapter(adapter);
        loadResturants();
    }

    private void loadResturants() {
        RequestParams p = new RequestParams();
        p.put("task", "get_all_restaurent");
        postCall(this, "http://stubuz.com/pos_api/api.php",p,"Loading Resturants",1);
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        Log.d("response", o.toString());
        if (o.optInt("status") == 1) {
            //   RestUser u = new Gson().fromJson(o.toString(), RestUser.class);
//            MyApp.getApplication().writeUser(u);
            MyApp.showMassage(this, "Resturant List");
            ResturantData u = new Gson().fromJson(o.toString(), ResturantData.class);

            listdata.addAll(u.getMessage());
            adapter.notifyDataSetChanged();

        } else {
            MyApp.popMessage("Error", o.optString("message"), this);

        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }
}
