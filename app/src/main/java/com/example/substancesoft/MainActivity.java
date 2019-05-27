package com.example.substancesoft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    protected EditText address;
    protected EditText username;
    protected EditText password;
    protected ImageView image;
    protected Button login;
    SharedPreferences vars;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vars = getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        image = (ImageView) findViewById(R.id.appLogo);
        address = (EditText) findViewById(R.id.loginIP);
        username = (EditText) findViewById(R.id.loginUser);
        password = (EditText) findViewById(R.id.loginPassword);
        login = (Button) findViewById(R.id.connect);

        Picasso.with(this).load("https://i.ibb.co/9sNSc37/logo.png").into(image);

        login.setOnClickListener
                (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        final IP ip = new IP("http://" + address.getText().toString());
                        String url = ip.getAddress()+"/substancesoft/mobile/get-login.php?user="+username.getText().toString()+"&pass="+password.getText().toString();
                        Toast.makeText(getApplicationContext(), ""+url, Toast.LENGTH_SHORT).show();
                        JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.GET,
                                url,
                                null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Integer error = response.getInt("error");
                                            if (error == 0)
                                            {
                                                String sName = response.getString("nombre");
                                                Toast.makeText(getApplicationContext(), "Inicio correcto", Toast.LENGTH_SHORT).show();
                                                SharedPreferences.Editor editor = vars.edit();
                                                    editor.putBoolean("logged",true);
                                                    editor.putString("user",username.getText().toString());
                                                    editor.putString("name",sName);
                                                    editor.putString("address",ip.getAddress());
                                                    editor.commit();
                                                    Intent changeWindow = new Intent(MainActivity.this, MainScreen.class);
                                                    startActivity(changeWindow);
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), "Error: "+error.toString() , Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(request);
                    }
                }
        );
        checkLogged();
    }
    public void checkLogged()
    {
        if(vars.getBoolean("logged",false))
        {
                Intent changeWindow = new Intent(MainActivity.this, MainScreen.class);
                startActivity(changeWindow);
        }
    }
}