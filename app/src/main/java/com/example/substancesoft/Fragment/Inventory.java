package com.example.substancesoft.Fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.example.substancesoft.QueryInventory;
import com.example.substancesoft.QueryInventoryAdapter;
import com.example.substancesoft.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inventory extends Fragment /*implements View.OnClickListener*/
{
    private static final String TAG = "Inventory";
    private ListView inventoryList;
    private ArrayList<QueryInventory> data;
    private QueryInventoryAdapter adapter;
    private Button ivan;
    SharedPreferences vars;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        ivan = view.findViewById(R.id.ivanButton);

        data = new ArrayList<QueryInventory>();
        adapter = new QueryInventoryAdapter();

        ivan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = getString(R.string.address)+ "/substancesoft/impresionTermica/feria.php";
                RequestQueue request = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"No se puede imprimir",Toast.LENGTH_SHORT).show();
                    }
                });
                request.add(jsonObjectRequest);
            }
        });

        String url = getString(R.string.address)+ "/substancesoft/mobile/get-inventory.php";

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

                                String nombre = query.getString("nombre");
                                String existencia = query.getString("existencia");
                                String critica = query.getString("critica");
                                String especificacion = query.getString("especificacion");
                                String clave = query.getString("clave");

                                QueryInventory qry = new QueryInventory();
                                qry.setClave(clave);
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

                            inventoryList.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener()
                                    {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
                                        {
                                            final String key = data.get(position).getClave();
                                            Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();


                                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                                            final EditText edittext = new EditText(getActivity());
                                            String ingredient = data.get(position).getNombre();
                                            alert.setMessage("Ingrese la cantidad de " + ingredient + " a ingresar al inventario");
                                            alert.setTitle("Agregar a inventario");

                                            alert.setView(edittext);

                                            alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int whichButton)
                                                {
                                                    final String qty = edittext.getText().toString();

                                                    String url = getString(R.string.address) + "/substancesoft/mobile/add-inventory.php?pk=" + key + "&qty=" + qty;
                                                    Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT);


                                                    JsonObjectRequest peticion = new JsonObjectRequest
                                                            (
                                                                    Request.Method.GET,
                                                                    url,
                                                                    null,
                                                                    new Response.Listener<JSONObject>()
                                                                    {
                                                                        @Override
                                                                        public void onResponse(JSONObject response)
                                                                        {
                                                                            try {
                                                                                Integer error = response.getInt("error");
                                                                                switch (error)
                                                                                {
                                                                                    case 0:
                                                                                        Toast.makeText(getActivity(), "Agregado al inventario", Toast.LENGTH_LONG).show();
                                                                                        Double newExistence = Double.parseDouble(data.get(position).getExistencia()) + Double.parseDouble(qty);
                                                                                        data.get(position).setExistencia(newExistence.toString());
                                                                                        adapter.notifyDataSetChanged();
                                                                                        break;
                                                                                    default:
                                                                                        Toast.makeText(getActivity(), "Error al agregar: Código: " + error.toString(), Toast.LENGTH_LONG).show();
                                                                                        break;
                                                                                }
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
                                                    x.add(peticion);
                                                }
                                            });

                                            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int whichButton)
                                                {
                                                    Toast.makeText(getActivity(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                            alert.show();
                                        }
                                    }
                            );
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