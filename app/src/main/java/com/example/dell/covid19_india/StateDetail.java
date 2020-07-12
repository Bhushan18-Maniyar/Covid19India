package com.example.dell.covid19_india;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class StateDetail extends AppCompatActivity {

    private int position;
    TextView confirmed,active,recovered,death,tc,tr,td;
    SimpleArcLoader loader;
    CardView cardtop, cardbottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_detail);

        loader = (SimpleArcLoader)findViewById(R.id.loader);
        loader.start();

        confirmed = (TextView) findViewById(R.id.confirmedcase);
        active = (TextView) findViewById(R.id.activecase);
        recovered = (TextView) findViewById(R.id.recoveredcase);
        death =  (TextView) findViewById(R.id.deceased);
        tc =  (TextView) findViewById(R.id.tconfimred);
        tr =  (TextView) findViewById(R.id.tr);
        td =  (TextView) findViewById(R.id.tdeceased);
        cardbottom = (CardView) findViewById(R.id.cardbottom);
        cardtop = (CardView) findViewById(R.id.cardtop);

        Intent i = getIntent();
        position = i.getIntExtra("position",0);
        getSupportActionBar().setTitle(IndianStates.stateModelList.get(position).getState()+" Detail");
        confirmed.setText(IndianStates.stateModelList.get(position).getConfirmed());
        active.setText(IndianStates.stateModelList.get(position).getActive());
        recovered.setText(IndianStates.stateModelList.get(position).getRecovered());
        death.setText(IndianStates.stateModelList.get(position).getDeaths());
        tc.setText(IndianStates.stateModelList.get(position).getTodaysconfirmed());
        tr.setText(IndianStates.stateModelList.get(position).getTodaysrecovered());
        td.setText(IndianStates.stateModelList.get(position).getTodaysdeath());


        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        long activepg = Long.parseLong(IndianStates.stateModelList.get(position).getActive());
        long deathpg = Long.parseLong(IndianStates.stateModelList.get(position).getDeaths());
        long recoveredpg = Long.parseLong(IndianStates.stateModelList.get(position).getRecovered());
        long confirmedpg = Long.parseLong(IndianStates.stateModelList.get(position).getConfirmed());

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
        loader.stop();

    }
}
