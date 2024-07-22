package com.example.translatorapp.Interface

import com.example.translatorapp.TranslatorModal
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TranslatorInterface {

    @FormUrlEncoded
    @POST("translate")
    fun translateText(
        @Header("X-RapidAPI-Key") key: String = "784d0e575bmsh9a0d6f9fca18578p19e6d9jsnd73587aa0865",
        @Header("X-RapidAPI-Host") host: String = "text-translator2.p.rapidapi.com",
        @Field("source_language") source: String,
        @Field("target_language") target: String,
        @Field("text") text: String
    ): Call<TranslatorModal>
}


/*
* Query Parameters (Parameters hoy to) => @Query / levanu
* Header aavtihoy to => @Header
* Body aavtihoy to => @Body
*
* */