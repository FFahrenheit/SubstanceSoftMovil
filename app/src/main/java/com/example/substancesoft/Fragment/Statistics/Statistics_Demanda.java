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
import com.anychart.anychart.SeriesBar;
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

public class Statistics_Demanda extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>
{
    private static final String TAG = "Statistics_Demanda";

    public RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.statistics_demanda_fragment,container,false);
        request = Volley.newRequestQueue(getContext());
        String url = getString(R.string.address)+"/substancesoft/mobile/demanda.php";
        Cartesian3d bar3d = AnyChart.bar3d();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("vacio", 0));
        bar3d.setData(data);
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setChart(bar3d);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(jsonObjectRequest);
        try {
            //set time in mili
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("demanda");
        List<DataEntry> data = new ArrayList<>();
        Cartesian3d bar3d = AnyChart.bar3d();
        try {
            JSONObject jsonObject = null;
            for(int i = 0; i<json.length();i++){
                jsonObject = json.getJSONObject(i);
                data.add(new ValueDataEntry(jsonObject.optString("nombre"), jsonObject.optInt("suma")));
            }
            Bar3d bar = bar3d.bar(data);
            bar.setName("Ventas");
            Toast.makeText(getContext(),bar3d.getLabels().toString(),Toast.LENGTH_SHORT);
            bar3d.setTitle("Demanda de platillos");
            AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
            anyChartView.setChart(bar3d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se pue wachar mijo",Toast.LENGTH_SHORT);
    }
}