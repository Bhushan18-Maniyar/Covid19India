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

/*
 *
 *
 * Created by BBM on 08-05-2020.
 *
 *
 */

public class MyCustomAdapter extends ArrayAdapter<StateModel> {
    private Context context;
    private List<StateModel> stateModelList;
    public MyCustomAdapter(Context context, List<StateModel> stateModelList) {
        super(context, R.layout.stateslist,stateModelList);
        this.context = context;
        this.stateModelList = stateModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stateslist,null,true);
        TextView statename = (TextView) view.findViewById(R.id.statename);
        TextView confirmedcase = (TextView) view.findViewById(R.id.confirmedcase);
        TextView recoveredcase = (TextView) view.findViewById(R.id.recoveredcase);

        statename.setText(stateModelList.get(position).getState());
        confirmedcase.setText(stateModelList.get(position).getConfirmed());
        recoveredcase.setText(stateModelList.get(position).getRecovered());

        return view;
    }
}
