package com.example.dell.covid19_india;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dell on 19-06-2020.
 */

public class DistrictAdapter extends ArrayAdapter<DistrictModel>{

    private Context context;
    private List<DistrictModel> districtModelList;
    public DistrictAdapter(Context context, List<DistrictModel> districtModelList) {
        super(context, R.layout.district,districtModelList);
        this.context = context;
        this.districtModelList = districtModelList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.district,null,true);
        TextView statename = (TextView) view.findViewById(R.id.districtName);
        TextView confirmedcase = (TextView) view.findViewById(R.id.confirmedcase);
        TextView recoveredcase = (TextView) view.findViewById(R.id.recoveredcase);

        statename.setText(districtModelList.get(position).getDistrictName());
        confirmedcase.setText(districtModelList.get(position).getConfirmed());
        recoveredcase.setText(districtModelList.get(position).getRecovered());

        return view;
    }
}
