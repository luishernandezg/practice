package com.example.practice.repository

import com.example.practice.api.PokemonService
import com.example.practice.model.PokemonListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Luis hernandez on 11/4/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
class PokemonRepositoryImp(
    private val pokemonService: PokemonService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): PokemonRepository {


    override suspend fun getPokemonList(): PokemonListResponse? {
        return withContext(ioDispatcher) {

             pokemonService.getPokemonList(20,0)
        }
    }

}