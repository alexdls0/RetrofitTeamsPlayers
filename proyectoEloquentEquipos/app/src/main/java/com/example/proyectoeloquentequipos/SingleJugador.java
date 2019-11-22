package com.example.proyectoeloquentequipos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.Model.Data.Jugadores;
import com.example.proyectoeloquentequipos.Model.Repository;
import com.example.proyectoeloquentequipos.View.MainViewModel;

public class SingleJugador extends AppCompatActivity {

    public MainViewModel viewModel;
    private static final String ID = "Jugadores.ID";
    private TextView tvNombre, tvApellidos;
    private ImageView ivImagen;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_jugador);
        init();
    }

    private void init() {
        Repository repository = new Repository();
        url = repository.getUrl();

        ivImagen = findViewById(R.id.ivImagen);
        tvNombre = findViewById(R.id.tvNombre);
        tvApellidos = findViewById(R.id.tvApellidos);
        final long id = getIntent().getLongExtra(ID, 0);
        viewModel = Plantilla.viewModel;
        final Jugadores[] j = {new Jugadores()};
        viewModel.jugadoresLiveData(id).observe(SingleJugador.this, new Observer<Jugadores>() {
            @Override
            public void onChanged(Jugadores jugadores) {
                j[0] = jugadores;
                String nombre = jugadores.getNombre();
                String apellidos = jugadores.getApellidos();

                tvNombre.setText(nombre);
                tvApellidos.setText(apellidos);

                Glide.with(SingleJugador.this)
                        .load(url+"/upload/"+jugadores.getFoto())
                        .override(500, 500)// prueba de escalado
                        .into(ivImagen);
            }
        });



    }
}
