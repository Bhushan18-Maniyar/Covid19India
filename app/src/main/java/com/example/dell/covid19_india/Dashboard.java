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
import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {

    TextView confirmed,active,recovered,death,tc,test,td;
    SimpleArcLoader loader;
    CardView cardtop, cardbottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().setTitle("Global Dashboard");
        loader = (SimpleArcLoader)findViewById(R.id.loader);
        confirmed = (TextView) findViewById(R.id.confirmedcase);
        active = (TextView) findViewById(R.id.activecase);
        recovered = (TextView) findViewById(R.id.recoveredcase);
        death =  (TextView) findViewById(R.id.deceased);
        tc =  (TextView) findViewById(R.id.tconfimred);
        test =  (TextView) findViewById(R.id.test);
        td =  (TextView) findViewById(R.id.tdeceased);
        cardbottom = (CardView) findViewById(R.id.cardbottom);
        cardtop = (CardView) findViewById(R.id.cardtop);
        loader.start();

        // checking internet connection
        if (isOnline())
        {
            fetchData();
        } else {
            Dashboard.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(Dashboard.this).create();
                alertDialog.setTitle("Information");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isOnline();
                    }
                });
                alertDialog.show();
            } catch(Exception e)
            {
                Toast.makeText(Dashboard.this, "EXCEPTION IN Internate", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // fetching data from api
    private void fetchData() {
        String url ="https://corona.lmao.ninja/v2/all/";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(Dashboard.this,"SET  SET on response",Toast.LENGTH_LONG).show();
                        try {
//                            Toast.makeText(Dashboard.this,"SET in method try",Toast.LENGTH_LONG).show();

                            JSONObject jsonObject = new JSONObject(response.toString());
                            confirmed.setText(jsonObject.getString((String) ("cases")));
                            active.setText(jsonObject.getString((String) ("active")));
                            recovered.setText(jsonObject.getString((String) ("recovered")));
                            death.setText(jsonObject.getString((String) ("deaths")));
                            tc.setText(jsonObject.getString((String) ("todayCases")));
                            td.setText(jsonObject.getString((String) ("todayDeaths")));
                            test.setText(jsonObject.getString((String) ("tests")));


                            PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
                            long activepg = Long.parseLong(jsonObject.getString((String)("active")));
                            long deathpg = Long.parseLong(jsonObject.getString((String)("deaths")));
                            long recoveredpg = Long.parseLong(jsonObject.getString((String)("recovered")));
                            long confirmedpg = Long.parseLong(jsonObject.getString((String)("cases")));
                            mPieChart.addPieSlice(new PieModel("Active", activepg, Color.parseColor("#FE6DA8")));
                            mPieChart.addPieSlice(new PieModel("Recovered", recoveredpg, Color.parseColor("#0fea07")));
                            mPieChart.addPieSlice(new PieModel("Death", deathpg, Color.parseColor("#cf092a")));
                            mPieChart.addPieSlice(new PieModel("Confirmed", confirmedpg, Color.parseColor("#0d4acc")));
                            mPieChart.setAutoCenterInSlice(true);

                            loader.stop();
                            loader.setVisibility(View.GONE);
                            cardtop.setVisibility(View.VISIBLE);
                            cardbottom.setVisibility(View.VISIBLE);
                            mPieChart.startAnimation();

                        } catch (JSONException e) {
                            loader.stop();
                            loader.setVisibility(View.GONE);
                            cardtop.setVisibility(View.VISIBLE);
                            cardbottom.setVisibility(View.VISIBLE);
                            Toast.makeText(Dashboard.this,"SET Exception'"+e.getMessage()+"' ",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

//        confirmed.setText("Not here again!!");
    }


    // connection checking function
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Dashboard.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(Dashboard.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
