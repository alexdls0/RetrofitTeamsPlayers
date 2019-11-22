package com.example.proyectoeloquentequipos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.Model.Repository;
import com.example.proyectoeloquentequipos.View.MainViewModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class EditEquipo extends AppCompatActivity {

    public MainViewModel viewModel;
    private static final String ID = "Equipo.ID";
    private TextView etNombre, etEstadio, etCiudad, etAforo;
    private Button btEditar;
    private ImageView ivImagen;
    private String url = "";
    private Uri enlace;
    private static final int SELECT_IMAGE_TO_UPLOAD = 1;
    private String escudo = "";
    public static final String DATA = "012UV34BCDEFGHIMNOPQ567JKL89ARSTWXYZ";
    public static Random RANDOM = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_equipo);

        initComponents();
    }

    private void initComponents() {
        Repository repository = new Repository();
        url = repository.getUrl();

        viewModel = MainActivity.viewModel;
        etNombre = findViewById(R.id.etNombre);
        etEstadio = findViewById(R.id.etEstadio);
        etCiudad = findViewById(R.id.etCiudad);
        etAforo = findViewById(R.id.etAforo);
        btEditar = findViewById(R.id.btEditar);
        ivImagen = findViewById(R.id.ivImagen);

        final Equipo[] e = {new Equipo()};
        final long id = getIntent().getLongExtra(ID, 0);

        viewModel.equipoLiveData(id).observe(EditEquipo.this, new Observer<Equipo>() {
            @Override
            public void onChanged(Equipo equipo) {
                e[0] = equipo;
                String nombre = e[0].getNombre();
                String ciudad = e[0].getCiudad();
                String aforo = ""+e[0].getAforo();
                String estadio = e[0].getEstadio();

                etNombre.setText(nombre);
                etEstadio.setText(estadio);
                etCiudad.setText(ciudad);
                etAforo.setText(aforo);
                btEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(enlace != null){
                            saveSelectedImageInFile(enlace);
                        }
                        Equipo e = new Equipo();
                        e.setNombre(etNombre.getText().toString());
                        e.setCiudad(etCiudad.getText().toString());
                        e.setAforo(Integer.parseInt(etAforo.getText().toString()));
                        e.setEstadio(etEstadio.getText().toString());
                        if(!escudo.equalsIgnoreCase("")){
                            e.setEscudo(escudo);
                        }
                        viewModel.updateEquipo(id,e);
                        finish();
                    }
                });
                Glide.with(EditEquipo.this)
                        .load(url+"/upload/"+equipo.getEscudo())
                        .override(500, 500)// prueba de escalado
                        .into(ivImagen);
            }
        });

        ivImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("*/*");
        String[] types = {"image/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        startActivityForResult(intent, SELECT_IMAGE_TO_UPLOAD);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_TO_UPLOAD && resultCode == Activity.RESULT_OK && null != data) {
            Uri uri = data.getData();
            enlace = uri;
            Glide.with(this)
                    .load(uri)
                    .override(500, 500)
                    .into(ivImagen);
        }
    }
    private void saveSelectedImageInFile(Uri uri) {
        Bitmap bitmap = null;
        if(Build.VERSION.SDK_INT < 28) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                bitmap = null;
            }
        } else {
            try {
                final InputStream in = this.getContentResolver().openInputStream(uri);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            } catch (IOException e) {
                bitmap = null;
            }
        }
        if(bitmap != null) {
            File file = saveBitmapInFile(bitmap);
            if(file != null) {
                viewModel.upload(file);
            }
        }
    }

    private File saveBitmapInFile(Bitmap bitmap) {
        escudo = randomString(15)+".jpg";
        File file = new File(getFilesDir(), escudo);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            file = null;
        }
        return file;
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }
        return sb.toString();
    }
}
