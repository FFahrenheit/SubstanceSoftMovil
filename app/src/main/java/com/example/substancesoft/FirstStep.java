package com.example.substancesoft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FirstStep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_step);

        String url = getString(R.string.address)+"/substancesoft/mobile/check-logged.php";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String permission = response.getString("valor");
                            if(permission.equals("0"))
                            {
                                Intent changeWindow = new Intent(FirstStep.this, NoPermited.class);
                                startActivity(changeWindow);
                            }
                            else {
                                Intent changeWindow = new Intent(FirstStep.this, MainActivity.class);
                                startActivity(changeWindow);
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
                        Toast.makeText(FirstStep.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(FirstStep.this);
        queue.add(request);
    }
}
