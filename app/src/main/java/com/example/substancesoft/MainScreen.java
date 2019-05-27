package com.example.substancesoft;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.substancesoft.Fragment.Inventory;
import com.example.substancesoft.Fragment.Notifications;
import com.example.substancesoft.Fragment.Statistics.Statistics;
import com.example.substancesoft.Fragment.Statistics.Statistics_Demanda;
import com.example.substancesoft.Fragment.Statistics.Statistics_Root;

import java.util.Objects;

public class MainScreen extends AppCompatActivity
{

    private static final String TAG = "MainScreen";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("SubstanceSoft");*/
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Notifications(), "Notificaciones");
        adapter.addFragment(new Statistics_Root(), "Estadisticas");
        adapter.addFragment(new Inventory(), "Inventario");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed()
    {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
}