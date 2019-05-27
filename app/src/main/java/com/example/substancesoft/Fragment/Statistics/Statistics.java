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

import com.example.substancesoft.R;

public class Statistics extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        final Button demanda = getView().findViewById(R.id.demanda);
        demanda.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.rootSt, new Statistics_Demanda());
<<<<<<< HEAD
                fragmentTransaction.addToBackStack(null).commit();
=======
                fragmentTransaction.commit();
>>>>>>> parent of e4581b9... Presionar atrás y que te saque (En las estadísticas) es para mensos
            }
        });
        final Button empleados = getView().findViewById(R.id.empleados);
        empleados.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.rootSt, new Statistics_Empleados());
<<<<<<< HEAD
                fragmentTransaction.addToBackStack(null).commit();
=======
                fragmentTransaction.commit();
>>>>>>> parent of e4581b9... Presionar atrás y que te saque (En las estadísticas) es para mensos
            }
        });
        final Button hora = getView().findViewById(R.id.horas);
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.rootSt, new Statistics_Hora());
<<<<<<< HEAD
                fragmentTransaction.addToBackStack(null).commit();
=======
                fragmentTransaction.commit();
>>>>>>> parent of e4581b9... Presionar atrás y que te saque (En las estadísticas) es para mensos
            }
        });
        final Button ingredientes = getView().findViewById(R.id.ingredientes);
        ingredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.rootSt, new Statistics_Ingredientes());
<<<<<<< HEAD
                fragmentTransaction.addToBackStack(null).commit();
=======
                fragmentTransaction.commit();
>>>>>>> parent of e4581b9... Presionar atrás y que te saque (En las estadísticas) es para mensos
            }
        });
        final Button monto = getView().findViewById(R.id.montos);
        monto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.rootSt, new Statistics_Montos());
<<<<<<< HEAD
                fragmentTransaction.addToBackStack(null).commit();
=======
                fragmentTransaction.commit();
>>>>>>> parent of e4581b9... Presionar atrás y que te saque (En las estadísticas) es para mensos
            }
        });
    }
}
