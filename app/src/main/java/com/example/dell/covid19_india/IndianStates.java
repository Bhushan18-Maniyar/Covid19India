package com.example.dell.covid19_india;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndianStates extends AppCompatActivity {

    ListView stateList;
    CardView cardtop, cardbottom;
    SimpleArcLoader loader;

//    public static Map<String , Integer> stateMap= new HashMap<>();
//    public static Set<String> stateNames = new HashSet<>();
    public static  List<StateModel> stateModelList = new ArrayList<>();

    public static boolean isCalled = false;
    StateModel stateModel;
    MyCustomAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_states);

        Toast.makeText(IndianStates.this,"Long Press On State to open Districts Wise Detail...",Toast.LENGTH_LONG).show();

        stateList = (ListView) findViewById(R.id.statesList);
        cardbottom = (CardView)findViewById(R.id.cardbottom);
        cardtop = (CardView)findViewById(R.id.cardtop);
        loader =(SimpleArcLoader) findViewById(R.id.loader);

        cardtop.setVisibility(View.GONE);
        cardbottom.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        loader.start();

        fetchData();

                        /*........................For Fetching full detail of states........................ */
        stateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(IndianStates.this,StateDetail.class).putExtra("position",position));

            }
        });

        stateList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if(!isCalled){
//                    isCalled = true;
//                    fetchDistrictData();
//                }
                startActivity(new Intent(IndianStates.this,StateDistrictWise.class).putExtra("State",position));
                Toast.makeText(IndianStates.this,stateModelList.get(position).getState(),Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    public void fetchData(){
        final String jsonString = "https://api.covid19india.org/data.json";

        StringRequest request = new StringRequest(Request.Method.GET, jsonString, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("statewise");

                    for(int i = 1 ; i < jsonArray.length() ; i++)
                    {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String statename = jsonObject1.getString((String)("state"));
                        String confirmed = jsonObject1.getString((String)("confirmed"));
                        String active = jsonObject1.getString((String)("active"));
                        String recovered = jsonObject1.getString((String)("recovered"));
                        String deaths = jsonObject1.getString((String)("deaths"));
                        String todaysconfimed = jsonObject1.getString((String)("deltaconfirmed"));
                        String todaysdeath = jsonObject1.getString((String)("deltadeaths"));
                        String todaysrecovered = jsonObject1.getString((String)("deltarecovered"));

//                        stateNames.add(statename);
                        stateModel = new StateModel(statename,  confirmed,  active,  recovered,  deaths,  todaysconfimed,  todaysdeath,  todaysrecovered);
                        stateModelList.add(stateModel);

                    }

                    MyCustomAdapter myCustomAdapter = new MyCustomAdapter(IndianStates.this,stateModelList);
                    stateList.setAdapter(myCustomAdapter);
                    loader.stop();
                    cardtop.setVisibility(View.VISIBLE);
                    cardbottom.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);

                } catch (JSONException e) {

                    loader.stop();
                    cardtop.setVisibility(View.VISIBLE);
                    cardbottom.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);
                    Toast.makeText(IndianStates.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IndianStates.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    protected void onDestroy() {
        super.onDestroy();
        stateModelList.clear();
    }

}
//    private void fetchDistrictData() {
//
//        String districtApi = "https://api.covid19india.org/v2/state_district_wise.json";
//
//        StringRequest request = new StringRequest(Request.Method.GET, districtApi, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response.toString());
////                    JSONArray  = jsonObject.getJSONArray("");
//                    for(int i = 0 ; i < jsonArray.length();i++){
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        String state = jsonObject1.getString("state");
//                        if(stateNames.contains(state)){
////                            Toast.makeText(IndianStates.this,state,Toast.LENGTH_SHORT).show();
//                            stateMap.put(state,i);
//                        } else continue;
//                    }
//
//                } catch (JSONException e) {
//                    Toast.makeText(IndianStates.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(IndianStates.this,error.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }