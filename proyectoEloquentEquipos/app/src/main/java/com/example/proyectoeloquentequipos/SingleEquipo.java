package com.example.proyectoeloquentequipos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.Model.Repository;
import com.example.proyectoeloquentequipos.View.MainViewModel;

public class SingleEquipo extends AppCompatActivity {

    public MainViewModel viewModel;
    private static final String ID = "Equipo.ID";
    private TextView tvNombre, tvCiudad, tvAfoto, tvEstadio;
    private Button btPlantilla;
    private ImageView ivImagen;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_equipo);
        init();
    }

    private void init() {
        Repository repository = new Repository();
        url = repository.getUrl();

        final long id = getIntent().getLongExtra(ID, 0);
        viewModel = MainActivity.viewModel;
        final Equipo[] e = {new Equipo()};
        ivImagen = findViewById(R.id.ivImagen);
        viewModel.equipoLiveData(id).observe(SingleEquipo.this, new Observer<Equipo>() {
            @Override
            public void onChanged(Equipo equipo) {
                String nombre = equipo.getNombre();
                String ciudad = equipo.getCiudad();
                String aforo = ""+equipo.getAforo();
                String estadio = equipo.getEstadio();

                tvNombre = findViewById(R.id.tvNombre);
                tvCiudad = findViewById(R.id.tvCiudad);
                tvAfoto = findViewById(R.id.tvAforo);
                tvEstadio = findViewById(R.id.tvEstadio);
                btPlantilla = findViewById(R.id.btJugadores);

                tvNombre.setText(nombre);
                tvAfoto.setText(aforo);
                tvCiudad.setText(ciudad);
                tvEstadio.setText(estadio);
                btPlantilla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SingleEquipo.this, Plantilla.class);
                        intent.putExtra(ID, id);
                        startActivity(intent);
                    }
                });

                Glide.with(SingleEquipo.this)
                        .load(url+"/upload/"+equipo.getEscudo())
                        .override(500, 500)// prueba de escalado
                        .into(ivImagen);
            }
        });
    }
}
