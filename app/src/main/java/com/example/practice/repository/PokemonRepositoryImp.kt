package com.example.practice.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.practice.api.PokemonService
import com.example.practice.model.PokemonItem
import com.example.practice.model.PokemonListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    override fun getPokemonPagingList(query: String, pageSize: Int): Flow<PagingData<PokemonItem>> {
        return  Pager(
            PagingConfig(pageSize)
        ) {
            PokemonPagingSource(
                pokemonService = pokemonService,
                query = "redditApi"
            )
        }.flow
    }

}