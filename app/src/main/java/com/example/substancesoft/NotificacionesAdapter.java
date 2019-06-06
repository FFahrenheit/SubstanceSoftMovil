package com.example.substancesoft;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.NotificacionesViewHolder> {
    public List<Notificaciones> data;

    public class NotificacionesViewHolder extends RecyclerView.ViewHolder {
        private TextView notificacion, fecha;

        public NotificacionesViewHolder(View view) {
            super(view);
            notificacion = (TextView) view.findViewById(R.id.notificacion);
            fecha = (TextView) view.findViewById(R.id.fecha);
        }
    }

    public NotificacionesAdapter(List<Notificaciones> data) {
        this.data = data;
    }

    @Override
    public NotificacionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notificaciones, parent, false);

        return new NotificacionesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificacionesViewHolder holder, int position) {
        Notificaciones not = data.get(position);
        holder.notificacion.setText(not.getConcepto());
        holder.fecha.setText(not.getFecha());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}