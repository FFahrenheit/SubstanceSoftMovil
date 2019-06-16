package com.example.substancesoft.Fragment.Statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian3d;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;
import com.example.substancesoft.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Statistics_Demanda extends Fragment
{
    private static final String TAG = "Statistics_Demanda";

    List<DataEntry> data = new ArrayList<>();
    Cartesian3d bar3d = AnyChart.bar3d();
    AnyChartView anyChartView;
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.statistics_demanda_fragment,container,false);
        Bundle arguments = getArguments();
        anyChartView = view.findViewById(R.id.any_chart_view);
        JSONObject obj= null;
        try {
            obj = new JSONObject(arguments.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray json = obj.optJSONArray("demanda");
        try {
            JSONObject jsonObject;
            for(int i = 0; i<json.length();i++){
                jsonObject = json.getJSONObject(i);
                data.add(new ValueDataEntry(jsonObject.optString("nombre"), jsonObject.optInt("suma")));
            }
            bar3d.bar(data).setName("Ventas");
            bar3d.setTitle("Demanda de platillos");
            bar3d.setAnimation(true);
            anyChartView.setChart(bar3d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}