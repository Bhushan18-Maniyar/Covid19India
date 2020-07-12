package com.example.dell.covid19_india;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class IndiaDashboard extends AppCompatActivity {

    TextView confirmed,active,recovered,death,tc,tr,td;
    SimpleArcLoader loader;
    CardView cardtop, cardbottom;
    Button global, indiaStates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_dashboard);

        getSupportActionBar().show();

        loader = (SimpleArcLoader)findViewById(R.id.loader);
        confirmed = (TextView) findViewById(R.id.confirmedcase);
        active = (TextView) findViewById(R.id.activecase);
        recovered = (TextView) findViewById(R.id.recoveredcase);
        death =  (TextView) findViewById(R.id.deceased);
        tc =  (TextView) findViewById(R.id.tconfimred);
        tr =  (TextView) findViewById(R.id.tr);
        td =  (TextView) findViewById(R.id.tdeceased);
        cardbottom = (CardView) findViewById(R.id.cardbottom);
        cardtop = (CardView) findViewById(R.id.cardtop);
        global = (Button)findViewById(R.id.global);
        indiaStates = (Button)findViewById(R.id.indiaStates);
        indiaStates.setVisibility(View.GONE);

        // checking internet connection
        if (isOnline())
        {
            loader.start();
            fetchData();
        } else {
            IndiaDashboard.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(IndiaDashboard.this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isOnline();
                    }
                });
                alertDialog.show();
            } catch(Exception e)
            {
                Toast.makeText(IndiaDashboard.this, "EXCEPTION IN Internate", Toast.LENGTH_SHORT).show();
            }
        }

        global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndiaDashboard.this,Dashboard.class));
            }
        });

        indiaStates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndiaDashboard.this,IndianStates.class));
            }
        });


    }

    public void fetchData(){

        final String jsonString = "https://api.covid19india.org/data.json";

        StringRequest request = new StringRequest(Request.Method.GET, jsonString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(IndiaDashboard.this,"INSIDE onResponse",Toast.LENGTH_LONG).show();

                try {
//                    Toast.makeText(IndiaDashboard.this,"INSIDE onResponse try",Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response.toString());
//                        confirmed.setText(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("statewise");
                    // looping through All Contacts

                    JSONObject c = jsonArray.getJSONObject(0);


                    confirmed.setText(c.getString((String) ("confirmed")));
                    active.setText(c.getString((String) ("active")));
                    death.setText(c.getString((String) ("deaths")));
                    recovered.setText(c.getString((String) ("recovered")));
                    tc.setText(c.getString((String) ("deltaconfirmed")));
                    td.setText(c.getString((String) ("deltadeaths")));
                    tr.setText(c.getString((String) ("deltarecovered")));

                    PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
                    long activepg = Long.parseLong(c.getString((String)("active")));
                    long deathpg = Long.parseLong(c.getString((String)("deaths")));
                    long recoveredpg = Long.parseLong(c.getString((String)("recovered")));
                    long confirmedpg = Long.parseLong(c.getString((String)("confirmed")));
                    mPieChart.addPieSlice(new PieModel("Active", activepg, Color.parseColor("#FE6DA8")));
                    mPieChart.addPieSlice(new PieModel("Recovered", recoveredpg, Color.parseColor("#0fea07")));
                    mPieChart.addPieSlice(new PieModel("Death", deathpg, Color.parseColor("#cf092a")));
                    mPieChart.addPieSlice(new PieModel("Confirmed", confirmedpg, Color.parseColor("#0d4acc")));
                    mPieChart.setAutoCenterInSlice(true);
                    mPieChart.startAnimation();

                    loader.stop();
                    loader.setVisibility(View.GONE);
                    cardtop.setVisibility(View.VISIBLE);
                    cardbottom.setVisibility(View.VISIBLE);
                    indiaStates.setVisibility(View.VISIBLE);
                    loader.stop();

                } catch (JSONException e) {

                    loader.stop();
                    loader.setVisibility(View.GONE);
                    cardtop.setVisibility(View.VISIBLE);
                    cardbottom.setVisibility(View.VISIBLE);
                    indiaStates.setVisibility(View.VISIBLE);
                    loader.stop();
                    Toast.makeText(IndiaDashboard.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


//                loader.stop();
//                loader.setVisibility(View.GONE);
//                cardtop.setVisibility(View.VISIBLE);
//                cardbottom.setVisibility(View.VISIBLE);
//                loader.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loader.stop();
                loader.setVisibility(View.GONE);
                cardtop.setVisibility(View.VISIBLE);
                cardbottom.setVisibility(View.VISIBLE);
                loader.stop();
                Toast.makeText(IndiaDashboard.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Dashboard.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(IndiaDashboard.this, "No Internet Connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
