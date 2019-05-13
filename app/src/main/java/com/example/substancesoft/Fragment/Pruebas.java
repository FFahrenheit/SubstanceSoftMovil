package com.example.substancesoft.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Bar3d;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.Cartesian3d;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ScaleStackMode;
import com.anychart.anychart.TooltipDisplayMode;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;
import com.example.substancesoft.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pruebas extends Fragment {

    public Pruebas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static Pruebas newInstance(String param1, String param2) {
        Pruebas fragment = new Pruebas();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.fragment_pruebas, container, false);
        Cartesian3d bar3d = AnyChart.bar3d();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        bar3d.setData(data);

        AnyChartView anyChartView = v.findViewById(R.id.any_chart_view);
        anyChartView.setChart(bar3d);

        return v;
    }
}
