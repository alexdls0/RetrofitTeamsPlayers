package com.example.proyectoeloquentequipos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.View.EquipoAdapter;
import com.example.proyectoeloquentequipos.View.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MainViewModel viewModel;
    private EquipoAdapter adapter;
    private static MainActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mContext = this;
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEquipo.class);
                startActivity(intent);
            }
        });

        RecyclerView rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EquipoAdapter(this);
        rvList.setAdapter(adapter);

        viewModel =  ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getLiveEquipoList().observe(this, new Observer<List<Equipo>>() {
            @Override
            public void onChanged(List<Equipo> equipos) {
                adapter.setData(equipos);
            }
        });
    }

    public static MainActivity getContext() {
        return mContext;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Conexion.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
