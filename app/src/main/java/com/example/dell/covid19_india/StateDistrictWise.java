package com.example.dell.covid19_india;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.example.dell.covid19_india.IndianStates.stateModelList;


public class StateDistrictWise extends AppCompatActivity {

    ListView districtListView;
    CardView cardtop, cardbottom;
    TextView district;
    SimpleArcLoader loader;

    String stateName;
    int position;

    public static List<DistrictModel> districtList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_district_wise);

        district = findViewById(R.id.district);
        districtListView = findViewById(R.id.districtList);
        cardbottom = (CardView)findViewById(R.id.cardbottom);
        cardtop = (CardView)findViewById(R.id.cardtop);

        loader =(SimpleArcLoader) findViewById(R.id.loader);

        Intent i = getIntent();
        position = i.getIntExtra("State",0);
        stateName = stateModelList.get(position).getState();

        district.setText(stateName);
//        position = stateMap.get(stateName.toString());
//        position = 21;
        loader.start();

//        to get object position .... from JSON response
        position = getPosition();

        fetchData();

        districtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(StateDistrictWise.this,DistrictDetail.class).putExtra("position",position));
            }
        });


    }

    private void fetchData() {
//        Toast.makeText(StateDistrictWise.this,"fetchData",Toast.LENGTH_SHORT).show();
        String jsonApiDistrict = "https://api.covid19india.org/v2/state_district_wise.json";
        StringRequest request = new StringRequest(Request.Method.GET, jsonApiDistrict, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(StateDistrictWise.this,"b  try",Toast.LENGTH_SHORT).show();
                try {
//                    Toast.makeText(StateDistrictWise.this,"fetchData",Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response.toString());
                    JSONObject jsonObject = jsonArray.getJSONObject(position);
                    if(stateName.equals(jsonObject.getString("state"))){

                        JSONArray districts = jsonObject.getJSONArray("districtData");
                        for(int j = 0 ; j < districts.length() ; j++){
                            JSONObject district  = districts.getJSONObject(j);
                            String districtName = district.getString("district");

//                        Toast.makeText(StateDistrictWise.this,districtName,Toast.LENGTH_SHORT).show();

                            String active = district.getString("active");
                            String confirmed = district.getString("confirmed");
                            String recovered = district.getString("recovered");
                            String deceased = district.getString("deceased");

                            JSONObject today = district.getJSONObject("delta");
                            String todayConfirmed = today.getString("confirmed");
                            String todayDeceased  = today.getString("deceased");
                            String todayRecovered = today.getString("recovered");
//                    DistrictModel(String districtName, String confirmed, String active, String recovered, String todayConfirmed, String todaydeceased, String todayrecovered)
                            DistrictModel districtModel = new DistrictModel(districtName , confirmed , active , recovered, deceased , todayConfirmed , todayDeceased , todayRecovered);
                            districtList.add(districtModel);

                        }
                        DistrictAdapter districtAdapter = new DistrictAdapter(StateDistrictWise.this,districtList);
                        districtListView.setAdapter(districtAdapter);
                        loader.stop();
                        cardtop.setVisibility(View.VISIBLE);
                        cardbottom.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.GONE);

                    } else {
                        getPosition();
                        fetchData();
                    }


                } catch (JSONException e) {
                    Toast.makeText(StateDistrictWise.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StateDistrictWise.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    protected void onDestroy() {
        super.onDestroy();
        districtList.clear();
    }

    public int getPosition() {

        String districtApi = "https://api.covid19india.org/v2/state_district_wise.json";

        StringRequest request = new StringRequest(Request.Method.GET, districtApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
//                    JSONArray  = jsonObject.getJSONArray("");
                    for(int i = 0 ; i < jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String state = jsonObject1.getString("state");
                        if(state.equals(stateName)){
                            position = i;
                            break;
                        } else continue;
                    }

                } catch (JSONException e) {
                    Toast.makeText(StateDistrictWise.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StateDistrictWise.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        return position;
    }
}
