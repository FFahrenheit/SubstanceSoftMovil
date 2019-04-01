package com.example.substancesoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QueryInventoryAdapter extends BaseAdapter
{
    public ArrayList<QueryInventory> data;
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
        View v = link.inflate(R.layout.inventory, null);

        TextView tvn = (TextView) v.findViewById(R.id.ingredientName);
        tvn.setText("Nombre:  "+ data.get(i).getNombre());

        tvn = (TextView) v.findViewById(R.id.ingredientExistence);
        tvn.setText("Existencia: "+data.get(i).getExistencia());

        tvn = (TextView) v.findViewById(R.id.ingredientCritical);
        tvn.setText("Existencia crítica : "+data.get(i).getCritica());

        tvn = (TextView) v.findViewById(R.id.ingredientEspecification);
        tvn.setText("Especificación: "+data.get(i).getEspecificacion());
        return v;
    }
}
