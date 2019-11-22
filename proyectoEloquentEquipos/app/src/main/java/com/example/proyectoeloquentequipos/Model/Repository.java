package com.example.proyectoeloquentequipos.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectoeloquentequipos.Conexion;
import com.example.proyectoeloquentequipos.MainActivity;
import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.Model.Data.Jugadores;
import com.example.proyectoeloquentequipos.Model.Rest.ProjectClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static String TAG = "proyectoeloquentequipos.repository";
    private ProjectClient apiClient;
    private ArrayList<Equipo> equipoList =new ArrayList<>();
    private ArrayList<Jugadores> jugadoresList =new ArrayList<>();
    private MutableLiveData<List<Equipo>> mutableEquipoList =new MutableLiveData<>();
    private MutableLiveData<List<Jugadores>> mutableJugadoresList =new MutableLiveData<>();
    private MutableLiveData<Equipo> equipoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Jugadores> jugadoresMutableLiveData = new MutableLiveData<>();
    private String url = "";

    public Repository() {
        setUrl();
        retrieveApiClient(url);
        fetchEquipoList();
        fetchJugadoresList();

    }

    public void setUrl(){
        SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences(Conexion.TAG, Context.MODE_PRIVATE);
        String enlace = sharedPreferences.getString(Conexion.URL, "0.0.0.0");
        url ="http:/"+enlace+"/web/equiposproyecto/public";
    }

    public String getUrl(){
        return this.url;
    }

    private void retrieveApiClient(String url){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http:/107.21.81.195/web/equiposproyecto/public/api/")
                .baseUrl(url+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient=retrofit.create(ProjectClient.class);

    }

    public void fetchEquipoList() {
        Call<ArrayList<Equipo>> call = apiClient.getEquipos();
        call.enqueue(new Callback<ArrayList<Equipo>>() {
            @Override
            public void onResponse(Call<ArrayList<Equipo>> call, Response<ArrayList<Equipo>> response) {
                Log.v(TAG, response.body().toString());
                equipoList=response.body();
                mutableEquipoList.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Equipo>> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
                equipoList=new ArrayList<>();
                mutableEquipoList=new MutableLiveData<>();
            }
        });
    }

    public void fetchJugadoresList() {
        Call<ArrayList<Jugadores>> call = apiClient.getJugadores();
        call.enqueue(new Callback<ArrayList<Jugadores>>() {
            @Override
            public void onResponse(Call<ArrayList<Jugadores>> call, Response<ArrayList<Jugadores>> response) {
                Log.v(TAG, response.body().toString());
                jugadoresList=response.body();
                mutableJugadoresList.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Jugadores>> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
                jugadoresList=new ArrayList<>();
                mutableJugadoresList=new MutableLiveData<>();
            }
        });
    }

    public List<Equipo> getEquipoList() {
        return equipoList;
    }

    public List<Jugadores> getJugadoresList() {
        return jugadoresList;
    }

    public LiveData<List<Equipo>> getLiveEquipoList(){
        return mutableEquipoList;
    }

    public LiveData<List<Jugadores>> getLiveJugadoresList(){
        return mutableJugadoresList;
    }

    public void add(Equipo equipo) {
        Call<Long> call = apiClient.postEquipo(equipo);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Log.v(TAG, response.body().toString());
                long resultado = response.body();
                if(resultado>0){
                    fetchEquipoList();
                }
            }
            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void add(Jugadores jugadores) {
        Call<Long> call = apiClient.postJugadores(jugadores);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Log.v(TAG, response.body().toString());
                long resultado = response.body();
                if(resultado>0){
                    fetchJugadoresList();
                }
            }
            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void deleteEquipo(long id) {
        Call<Integer> call = apiClient.deleteEquipo(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                fetchEquipoList();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void deleteJugador(long id) {
        Call<Integer> call = apiClient.deleteJugadores(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                fetchJugadoresList();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public void updateEquipo(long id, Equipo equipo) {
        Call<Equipo> call = apiClient.putEquipo(id, equipo);
        call.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                fetchEquipoList();
            }
            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {
                fetchEquipoList();
            }
        });
    }

    public void updateJugador(long id, Jugadores jugador) {
        Call<Jugadores> call = apiClient.putJugadores(id, jugador);
        call.enqueue(new Callback<Jugadores>() {
            @Override
            public void onResponse(Call<Jugadores> call, Response<Jugadores> response) {
                fetchJugadoresList();
            }
            @Override
            public void onFailure(Call<Jugadores> call, Throwable t) {
                fetchJugadoresList();
            }
        });
    }

    public void getSpecificEquipo(long id){
        Call<Equipo> call = apiClient.getEquipo(id);
        call.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                equipoMutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public LiveData<Equipo> equipoLiveData(long id) {
        getSpecificEquipo(id);
        return equipoMutableLiveData;
    }

    public void getSpecificJugadores(long id){
        Call<Jugadores> call = apiClient.getJugadores(id);
        call.enqueue(new Callback<Jugadores>() {
            @Override
            public void onResponse(Call<Jugadores> call, Response<Jugadores> response) {
                jugadoresMutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<Jugadores> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public LiveData<Jugadores> jugadoresLiveData(long id) {
        getSpecificJugadores(id);
        return jugadoresMutableLiveData;
    }

    public void upload(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part request = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Call<String> call = apiClient.fileUpload(request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v(TAG, response.body());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }
}
