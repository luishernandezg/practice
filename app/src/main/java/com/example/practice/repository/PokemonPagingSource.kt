package com.example.practice.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.api.PokemonService
import com.example.practice.model.PokemonItem
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Luis hernandez on 11/8/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
/*
* Paging source for pokemon list items
* */
class PokemonPagingSource(
    private val pokemonService: PokemonService,
    val query: String
) : PagingSource<Int, PokemonItem>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, PokemonItem> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 0
            val response = pokemonService.getPokemonList(20, nextPageNumber * 20)
            LoadResult.Page(
                data = response.results.mapPokemonItems(),
                prevKey = null, // Only paging forward.
                nextKey = if (response.next != null) nextPageNumber + 1 else null
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonItem>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun List<PokemonItem?>?.mapPokemonItems() =
        this?.mapNotNull {
            it
        } ?: listOf()
}
