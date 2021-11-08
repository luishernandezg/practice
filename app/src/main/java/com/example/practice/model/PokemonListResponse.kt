package com.example.practice.model

/**
 * Created by Luis hernandez on 11/4/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */

/*
*
* Pokemon list response
* */
data class PokemonListResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItem?>?
)
