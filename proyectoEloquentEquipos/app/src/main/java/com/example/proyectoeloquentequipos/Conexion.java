package com.example.proyectoeloquentequipos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyectoeloquentequipos.Model.Repository;

public class Conexion extends AppCompatActivity {
    private EditText etConexion;
    private Button btConexion;
    public static final String URL = "URL";
    public static final String TAG = "proyectoeloquentequipos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion);

        etConexion = findViewById(R.id.etConexion);
        btConexion = findViewById(R.id.btConexion);
        btConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(URL, etConexion.getText().toString());
                editor.commit();
                Repository repository = new Repository();
                finish();
            }
        });


    }
}
