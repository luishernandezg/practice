package com.example.practice.api

import com.example.practice.model.Order
import com.example.practice.model.OrderResponse
import com.example.practice.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Luis hernandez on 11/4/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
interface PokemonService {

    @GET("pokemon/")
    suspend fun getPokemonList( @Query("limit") limit: Int,@Query("offset") offset: Int ): PokemonListResponse


    @GET("wp-json/wc/v3/orders")
    suspend fun getOrders():  List<Order?>?

}