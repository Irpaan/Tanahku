package com.tanahku.network

import com.tanahku.data.SignIn
import com.tanahku.data.SignInResponse
import com.tanahku.data.SignUp
import com.tanahku.data.SignUpResponse
import com.tanahku.data.TanahResponse
import com.tanahku.data.TanamanResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @POST("register")
    fun doSignup(
        @Body signUp: SignUp
    ): Call<SignUpResponse>

    @POST("login")
    fun doLogin(
        @Body signIn : SignIn
    ): Call<SignInResponse>

    @GET("kecocokan/NAH0001")
    fun gettanamanaluvial(): Call<ArrayList<TanamanResponse>>

    @GET("kecocokan/NAH0002")
    fun gettanamanandosol(): Call<ArrayList<TanamanResponse>>

    @GET("kecocokan/NAH0003")
    fun gettanamanentisol(): Call<ArrayList<TanamanResponse>>

    @GET("kecocokan/NAH0004")
    fun gettanamanhumus(): Call<ArrayList<TanamanResponse>>

    @GET("kecocokan/NAH0005")
    fun gettanamaninseptisol(): Call<ArrayList<TanamanResponse>>

    @GET("kecocokan/NAH0006")
    fun gettanamankapur(): Call<ArrayList<TanamanResponse>>

    @GET("kecocokan/NAH0007")
    fun gettanamanlaterit(): Call<ArrayList<TanamanResponse>>

    @GET("kecocokan/NAH0008")
    fun gettanamanpasir(): Call<ArrayList<TanamanResponse>>

    @GET("tanah/NAH0001")
    fun gettanahaluvial(): Call<ArrayList<TanahResponse>>

    @GET("tanah/NAH0002")
    fun gettanahandosol(): Call<ArrayList<TanahResponse>>

    @GET("tanah/NAH0003")
    fun gettanahentisol(): Call<ArrayList<TanahResponse>>

    @GET("tanah/NAH0004")
    fun gettanahhumus(): Call<ArrayList<TanahResponse>>

    @GET("tanah/NAH0005")
    fun gettanahinseptisol(): Call<ArrayList<TanahResponse>>

    @GET("tanah/NAH0006")
    fun gettanahkapur(): Call<ArrayList<TanahResponse>>

    @GET("tanah/NAH0007")
    fun gettanahlaterit(): Call<ArrayList<TanahResponse>>

    @GET("tanah/NAH0008")
    fun gettanahpasir(): Call<ArrayList<TanahResponse>>
}