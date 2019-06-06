package com.example.substancesoft.Fragment.Statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Bar3d;
import com.anychart.anychart.Cartesian3d;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;
import com.example.substancesoft.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class Statistics_Empleados extends Fragment
{
    AnyChartView anyChartView;
    List<DataEntry> data = new ArrayList<>();
    List<DataEntry> data2 = new ArrayList<>();
    Cartesian3d bar3d = AnyChart.bar3d();
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_statistics__empleados,container,false);
        Bundle arguments = getArguments();
        anyChartView = view.findViewById(R.id.any_chart_view);
        JSONObject obj= null;
        try {
            obj = new JSONObject(arguments.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray json = obj.optJSONArray("participacion");
        try {
            JSONObject jsonObject;
            for(int i = 0; i<json.length();i++){
                jsonObject = json.getJSONObject(i);
                data.add(new ValueDataEntry(jsonObject.optString("usuario"), jsonObject.optInt("suma")));
            }
            bar3d.bar(data).setName("Ordenes concretadas");
            for(int i = 0; i<json.length();i++){
                jsonObject = json.getJSONObject(i);
                data2.add(new ValueDataEntry(jsonObject.optString("usuario"), jsonObject.optInt("suma_total")));
            }
            bar3d.bar(data2).setColor("#FF0000").setName("Pedidos Realizados");
            bar3d.setTitle("Movimientos de empleados");
            bar3d.setAnimation(true);
            anyChartView.setChart(bar3d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}