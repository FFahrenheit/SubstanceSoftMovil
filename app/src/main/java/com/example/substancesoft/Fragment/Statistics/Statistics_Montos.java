package com.example.substancesoft.Fragment.Statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class Statistics_Montos extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_statistics__montos,container,false);
        request = Volley.newRequestQueue(getContext());
        String url = getString(R.string.address)+"/substancesoft/mobile/cortes.php";
        Cartesian3d bar3d = AnyChart.bar3d();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("vacio", 0));
        bar3d.setData(data);
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setChart(bar3d);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
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
        JSONArray json = response.optJSONArray("cortes");
        List<DataEntry> data = new ArrayList<>();
        Cartesian3d bar3d = AnyChart.bar3d();
        try {
            JSONObject jsonObject = null;
            for(int i = 0; i<json.length();i++){
                jsonObject = json.getJSONObject(i);
                data.add(new ValueDataEntry(jsonObject.optString("dia"), jsonObject.optInt("suma")));
            }
            Bar3d bar = bar3d.bar(data);
            bar.setName("Ventas");
            Toast.makeText(getContext(),bar3d.getLabels().toString(),Toast.LENGTH_SHORT);
            bar3d.setTitle("Cortes de la semana");
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
