package com.example.substancesoft.Fragment.Statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.anychart.AnyChart;
import com.anychart.anychart.Cartesian3d;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;
import com.example.substancesoft.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends Fragment
{
    String url;
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_statistics,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        url = getString(R.string.address)+"/substancesoft/mobile/";
        final Button demanda = getView().findViewById(R.id.demanda);
        demanda.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fragment = new Statistics_Demanda();
                url+= "demanda.php";
                change();
            }
        });
        final Button empleados = getView().findViewById(R.id.empleados);
        empleados.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fragment = new Statistics_Empleados();
                url+= "participacion.php";
                change();
            }
        });
        final Button hora = getView().findViewById(R.id.horas);
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fragment = new Statistics_Hora();
                url+= "horarios.php";
                change();
            }
        });
        final Button ingredientes = getView().findViewById(R.id.ingredientes);
        ingredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Statistics_Ingredientes();
                url+= "ingredientes.php";
                change();
            }
        });
        final Button monto = getView().findViewById(R.id.montos);
        monto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Statistics_Montos();
                url+= "cortes.php";
                change();
            }
        });
    }

    public void change()
    {
        final Bundle arguments = new Bundle();
        RequestQueue request = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        arguments.putString("data", response.toString());
                        fragment.setArguments(arguments);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.rootSt, fragment);
                        fragmentTransaction.addToBackStack(null).commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se puede observar en este momento",Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }
}
