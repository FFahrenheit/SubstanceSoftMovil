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

public class Statistics_Ingredientes extends Fragment{
    AnyChartView anyChartView;
    List<DataEntry> data = new ArrayList<>();
    List<DataEntry> data2 = new ArrayList<>();
    Cartesian3d bar3d = AnyChart.bar3d();
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics__ingredientes, container, false);
        Bundle arguments = getArguments();
        anyChartView = view.findViewById(R.id.any_chart_view);
        JSONObject obj = null;
        try {
            obj = new JSONObject(arguments.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray json = obj.optJSONArray("ingredientes");
        try {
            JSONObject jsonObject;
            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                data.add(new ValueDataEntry(jsonObject.optString("nombre"), jsonObject.optInt("suma_surtido")));
            }
            bar3d.bar(data).setName("Surtido");
            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                data2.add(new ValueDataEntry(jsonObject.optString("nombre"), jsonObject.optInt("suma_uso")));
            }
            bar3d.bar(data2).setColor("#FF0000").setName("Uso");
            bar3d.setTitle("Movimiento de ingredientes");
            bar3d.setAnimation(true);
            anyChartView.setChart(bar3d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
