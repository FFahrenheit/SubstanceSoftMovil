package com.example.substancesoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificacionesAdapter extends BaseAdapter {

    public ArrayList<Notificaciones> data;
    public Context context;

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public Object getItem(int i)
    {
        return data.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup )
    {
        LayoutInflater link = LayoutInflater.from(context);
        View v = link.inflate(R.layout.notificaciones, null);

        TextView tvn = (TextView) v.findViewById(R.id.notificacion);
        tvn.setText("Notificación:  "+ data.get(i).getConcepto());

        tvn = (TextView) v.findViewById(R.id.fecha);
        tvn.setText("Fecha: "+data.get(i).getFecha());
        return v;
    }
}
