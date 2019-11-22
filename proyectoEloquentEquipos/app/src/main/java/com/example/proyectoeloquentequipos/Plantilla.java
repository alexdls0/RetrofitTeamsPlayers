package com.example.proyectoeloquentequipos;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectoeloquentequipos.Model.Data.Jugadores;
import com.example.proyectoeloquentequipos.View.JugadoresAdapter;
import com.example.proyectoeloquentequipos.View.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.List;

public class Plantilla extends AppCompatActivity {

    private static final String ID = "Equipo.ID";
    public static MainViewModel viewModel;
    private JugadoresAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantilla);

        final long id = getIntent().getLongExtra(ID, 0);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Plantilla.this, AddPlayer.class);
                intent.putExtra(ID, id);
                startActivity(intent);
            }
        });

        RecyclerView rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JugadoresAdapter(this, id);
        rvList.setAdapter(adapter);

        viewModel =  ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getLiveJugadoresList().observe(this, new Observer<List<Jugadores>>() {
            @Override
            public void onChanged(List<Jugadores> jugadores) {
                adapter.setData(jugadores);
            }
        });
    }
}
