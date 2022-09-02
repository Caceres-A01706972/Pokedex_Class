package com.example.pokedex

import  com.google.gson.annotations.SerializedName

data class PokemonListResults(
    @SerializedName("count") val count:Int,
    @SerializedName("results") val results:List<PokemonResult>

)
