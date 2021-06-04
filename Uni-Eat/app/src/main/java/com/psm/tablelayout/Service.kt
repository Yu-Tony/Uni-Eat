package com.psm.tablelayout

import com.psm.tablelayout.CardsLong.Categorias
import com.psm.tablelayout.CardsLong.Facultades
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.CardsLong.Resena
import retrofit2.Call
import retrofit2.http.*

//Retrofi usa una interface para hacer la petición hacia el servidor
interface Service{

    @GET("userUniEatC/UsersUniEat")
    fun getUsers(): Call<List<Perfil>>

    // @GET("Album/Albums/{id}")
    //fun getUser(@Path("id") id: Int): Call<List<Perfil>>
    @GET("userUniEatC/UsersUniEat/{userMail}")
    fun getUser(@Path("userMail") userMail: String): Call<List<Perfil>>

    @Headers("Content-Type: application/json")
    @POST("userUniEatC/Save")
    fun saveUser(@Body UsersData: Perfil): Call<Int>

    @Headers("Content-Type: application/json")
    @POST("userUniEatC/update")
    fun updateUser(@Body UsersData: Perfil): Call<Int>

    /*----------------------------------------------------------------------*/
    @GET("categoriaUniEatC/CategoriasUniEat")
    fun getCategorias(): Call<List<Categorias>>

    // @GET("Album/Albums/{id}")
    //fun getUser(@Path("id") id: Int): Call<List<Perfil>>
    @GET("categoriaUniEatC/CategoriasUniEat/{categoriaNombre}")
    fun getCategoria(@Path("categoriaNombre") categoriaNombre: String): Call<List<Categorias>>

    @Headers("Content-Type: application/json")
    @POST("categoriaUniEatC/Delete")
    fun deleteCategory(@Body CategData: Categorias): Call<String>

    @Headers("Content-Type: application/json")
    @POST("categoriaUniEatC/Save")
    fun saveCategory(@Body CategData: Categorias): Call<Int>

    /*----------------------------------------------------------------------*/

    @GET("facultadUniEatC/facultadesUniEat")
    fun getFacultades(): Call<List<Facultades>>

    // @GET("Album/Albums/{id}")
    //fun getUser(@Path("id") id: Int): Call<List<Perfil>>
    @GET("facultadUniEatC/facultadesUniEat/{facultadesNombre}")
    fun getFacultad(@Path("facultadesNombre") facultadesNombre: String): Call<List<Facultades>>

    @Headers("Content-Type: application/json")
    @POST("facultadUniEatC/Save")
    fun saveFacultad(@Body FacuData: Facultades): Call<Int>


    @GET("resenaUniEatC/ResenasUniEat")
    fun getResenas(): Call<List<Resena>>

    @GET("resenaUniEatC/ResenasUniEatBest")
    fun getBestResenas(): Call<List<Resena>>

    //CAMBIAR A BUSCAR RESENA POR ID
    @GET("resenaUniEatC/ResenasUniEat/{resenaID}")
    fun getResena(@Path("resenaID") resenaID: String): Call<List<Resena>>

    @Headers("Content-Type: application/json")
    @POST("resenaUniEatC/Delete")
    fun deleteResena(@Body ResData: Resena): Call<Int>

    @Headers("Content-Type: application/json")
    @POST("resenaUniEatC/Save")
    fun saveResena(@Body ResData: Resena): Call<Int>

    @Headers("Content-Type: application/json")
    @POST("resenaUniEatC/Update")
    fun updateResena(@Body ResData: Resena): Call<Int>
}