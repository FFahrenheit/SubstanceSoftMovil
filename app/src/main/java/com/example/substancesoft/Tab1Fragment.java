package com.example.substancesoft;


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
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ivan_ on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button logout;
    SharedPreferences vars;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        logout = (Button) view.findViewById(R.id.logoutButton);

        vars = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Hola", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Cerrar sesion");
                builder.setMessage("¿Esta seguro que quiere cerrar su sesion?");
                builder.setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences check = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = check.edit();
                                editor.putBoolean("logged",false);
                                editor.commit();
                                Intent logout = new  Intent(getActivity(), MainActivity.class);
                                Toast.makeText(getActivity(), "Sesion cerrada", Toast.LENGTH_SHORT).show();
                                startActivity(logout);
                            }
                        });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

            AlertDialog dialog = builder.create();
            dialog.show();
            }
        });

        return view;
    }
}
