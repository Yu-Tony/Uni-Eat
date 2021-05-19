package com.psm.tablelayout

import com.psm.tablelayout.CardsLong.Perfil
import retrofit2.Call
import retrofit2.http.*

//Retrofi usa una interface para hacer la petición hacia el servidor
interface Service{

    //Servicios para consumir el Album
    @GET("userUniEatC/UsersUniEat")
    fun getUsers(): Call<List<Perfil>>

    @GET("Album/Albums/{id}")
    fun getUser(@Path("id") id: Int): Call<List<Perfil>>

    @Headers("Content-Type: application/json")
    @POST("Album/Save")
    fun saveUser(@Body UsersData: Perfil): Call<Int>

}