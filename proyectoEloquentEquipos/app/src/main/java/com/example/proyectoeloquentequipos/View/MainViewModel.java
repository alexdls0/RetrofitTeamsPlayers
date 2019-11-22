package com.example.proyectoeloquentequipos.View;


import android.app.Application;

import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.Model.Data.Jugadores;
import com.example.proyectoeloquentequipos.Model.Repository;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class MainViewModel extends AndroidViewModel {
    private Repository repository;

    public MainViewModel(@NonNull Application application){
        super(application);
        repository= new Repository();

    }

    public String getUrl(){ return repository.getUrl();}

    public List<Equipo> getEquipoList(){
        return repository.getEquipoList();
    }

    public LiveData<Equipo> equipoLiveData (long id){return repository.equipoLiveData(id);}

    public LiveData<Jugadores> jugadoresLiveData (long id){return repository.jugadoresLiveData(id);}

    public LiveData<List<Equipo> >getLiveEquipoList(){
        return repository.getLiveEquipoList();
    }

    public void addEquipo(Equipo equipo) {
        repository.add(equipo);
    }

    public void deleteEquipo(long id) {
        repository.deleteEquipo(id);
    }

    public void deleteJugador(long id) {
        repository.deleteJugador(id);
    }

    public void updateEquipo(long id, Equipo equipo){
        repository.updateEquipo(id, equipo);
    }

    public void updateJugadores(long id, Jugadores jugador){
        repository.updateJugador(id, jugador);
    }

    /*public void setUrl(String url) {
        repository.setUrl(url);
    }*/

    public List<Jugadores> getJugadoresList(){
        return repository.getJugadoresList();
    }

    public LiveData<List<Jugadores> >getLiveJugadoresList(){
        return repository.getLiveJugadoresList();
    }

    public void addJugadores(Jugadores jugadores) {
        repository.add(jugadores);
    }

    public void upload(File file) {
        repository.upload(file);
    }
}
