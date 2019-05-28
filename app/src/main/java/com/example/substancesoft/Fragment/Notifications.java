package com.example.substancesoft.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.substancesoft.MainActivity;
import com.example.substancesoft.Notificaciones;
import com.example.substancesoft.NotificacionesAdapter;
import com.example.substancesoft.QueryInventory;
import com.example.substancesoft.QueryInventoryAdapter;
import com.example.substancesoft.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ivan_ on 2/28/2017.
 */

public class Notifications extends Fragment{
    private static final String TAG = "Notifications";

    private Button logout;
    SharedPreferences vars;
    private ListView notificationsList;
    private ArrayList<Notificaciones> data;
    private NotificacionesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notifications,container,false);
        logout = (Button) view.findViewById(R.id.logoutButton);

        vars = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Cerrar sesion");
                builder.setMessage("¿Esta seguro que quiere cerrar su sesion?");
                builder.setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                SharedPreferences check = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = check.edit();
                                editor.putBoolean("logged",false);
                                editor.commit();
                                Intent logout = new  Intent(getActivity(), MainActivity.class);
                                Toast.makeText(getActivity(), "Sesion cerrada", Toast.LENGTH_SHORT).show();
                                startActivity(logout);
                            }
                        });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getActivity(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        data = new ArrayList<Notificaciones>();
        adapter = new NotificacionesAdapter();

        String url = getString(R.string.address)+ "/substancesoft/mobile/get-notifications.php";

        final JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {

                        try {
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject query = response.getJSONObject(i);

                                String concepto = query.getString("texto");
                                String fecha = query.getString("fecha");

                                Notificaciones not = new Notificaciones();
                                not.setConcepto(concepto);
                                not.setFecha(fecha);

                                data.add(not);
                            }

                            notificationsList = (ListView) getActivity().findViewById(R.id.notificationsList);

                            adapter.context = getActivity();
                            adapter.data = data;

                            notificationsList.setAdapter(adapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue x = Volley.newRequestQueue(getActivity());
        x.add(request);



        return view;
    }
}