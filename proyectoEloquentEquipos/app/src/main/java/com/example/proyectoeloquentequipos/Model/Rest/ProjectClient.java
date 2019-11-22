package com.example.proyectoeloquentequipos.Model.Rest;

import com.example.proyectoeloquentequipos.Model.Data.Equipo;
import com.example.proyectoeloquentequipos.Model.Data.Jugadores;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProjectClient {

    //EQUIPO

    @DELETE("equipo/{id}")
    Call<Integer> deleteEquipo(@Path("id") long id);

    @GET("equipo/{id}")
    Call<Equipo> getEquipo(@Path("id") long id);

    @GET("equipo")
    Call<ArrayList<Equipo>> getEquipos();

    @POST("equipo")
    Call<Long> postEquipo(@Body Equipo equipo);

    @PUT("equipo/{id}")
    Call<Equipo> putEquipo(@Path("id") long id, @Body Equipo equipo);

    //JUGADORES

    @DELETE("jugadores/{id}")
    Call<Integer> deleteJugadores(@Path("id") long id);

    @GET("jugadores/{id}")
    Call<Jugadores> getJugadores(@Path("id") long id);

    @GET("jugadores")
    Call<ArrayList<Jugadores>> getJugadores();

    @POST("jugadores")
    Call<Long> postJugadores(@Body Jugadores jugadores);

    @PUT("jugadores/{id}")
    Call<Jugadores> putJugadores(@Path("id") long id, @Body Jugadores jugadores);

    //IMÁGENES

    @Multipart
    @POST("upload")
    Call<String> fileUpload(@Part MultipartBody.Part file);
}