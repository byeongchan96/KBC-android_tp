package com.example.intravel.client

import com.example.intravel.interfaces.SubInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SubClient {
    val retrofit: SubInterface = Retrofit.Builder()
        .baseUrl("http://192.168.45.96:8811/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SubInterface::class.java)
}