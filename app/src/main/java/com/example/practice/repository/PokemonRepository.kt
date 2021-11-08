package com.example.practice.repository

import com.example.practice.model.PokemonListResponse

/**
 * Created by Luis hernandez on 11/4/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
interface PokemonRepository {

    suspend fun getPokemonList(): PokemonListResponse?
}