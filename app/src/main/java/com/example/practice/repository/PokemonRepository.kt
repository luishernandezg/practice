package com.example.practice.repository

import androidx.paging.PagingData
import com.example.practice.model.Order
import com.example.practice.model.OrderResponse
import com.example.practice.model.PokemonItem
import com.example.practice.model.PokemonListResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Luis hernandez on 11/4/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
interface PokemonRepository {

    suspend fun getPokemonList(): PokemonListResponse?

    suspend fun getOrdersList(): List<Order?>?

    fun getPokemonPagingList(query: String, pageSize: Int): Flow<PagingData<PokemonItem>>
}