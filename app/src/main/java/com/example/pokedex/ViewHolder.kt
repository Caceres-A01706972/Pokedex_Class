package com.example.pokedex

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ItemPokemonDetailBinding

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPokemonDetailBinding.bind(view)

    fun bind(item:PokemonResult){
        binding.tvName.text = item.name

    }
}
