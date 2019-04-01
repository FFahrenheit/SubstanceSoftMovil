package com.example.substancesoft;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";
    private ListView inventoryList;
    private ArrayList<QueryInventory> data;
    private QueryInventoryAdapter adapter;
    SharedPreferences vars;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);


        vars = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        data = new ArrayList<QueryInventory>();
        adapter = new QueryInventoryAdapter();

        String IP = vars.getString("address","http://0.0.0.0");

        String url = IP + "/substancesoft/mobile/get-inventory.php";
        Toast.makeText(getActivity(),url, Toast.LENGTH_LONG).show();


        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {

                        try
                        {
                            for(int i=0; i<response.length(); i++)
                            {
                                JSONObject query = response.getJSONObject(i);

                                String nombre = query.getString("nombre");
                                String existencia = query.getString("existencia");
                                String critica = query.getString("critica");
                                String especificacion = query.getString("especificacion");

                                QueryInventory qry = new QueryInventory();
                                qry.setNombre(nombre);
                                qry.setEspecificacion(especificacion);
                                qry.setCritica(critica);
                                qry.setExistencia(existencia);

                                data.add(qry);
                            }

                            inventoryList = (ListView) getActivity().findViewById(R.id.inventoryList);

                            adapter.context = getActivity();
                            adapter.data = data;

                            inventoryList.setAdapter(adapter);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue x = Volley.newRequestQueue(getActivity());
        x.add(request);
        return view;
    }
}