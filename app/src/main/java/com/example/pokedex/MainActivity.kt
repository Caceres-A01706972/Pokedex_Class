package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var pkmnList:List<PokemonResult>
    private  lateinit var adapter:PokemonListAdapter

    private  fun initializeList(list:List<PokemonResult>){
        adapter = PokemonListAdapter(list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        //For Horizontal View. Substitute line 25 for this:
//        var layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        binding.recyclerView.layoutManager = layoutManager

        //For Grid View. Substitute line 25 for this:
//        var layoutManager = GridLayoutManager(this, 2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchPokemonList()
        initializeListeners()
    }

    private fun initializeListeners(){
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchPokemonName(s.toString().lowercase(Locale.getDefault()))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun searchPokemonName(prefixNamePokemon: String){

        if(prefixNamePokemon.equals("").not()){
            val  tempData:List<PokemonResult> = pkmnList.filter {
                it.name.contains(prefixNamePokemon)
            }
            initializeList(tempData)
        }else{
            initializeList(pkmnList)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //This is a comment
    private fun searchPokemonList(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPokemonList("?offset=0&limit=968")
            val pokemonList = call.body()
            if(call.isSuccessful){
                //show Recyclerview
                Log.d("Salida",pokemonList!!.results.size.toString())
                pkmnList = pokemonList.results

                CoroutineScope(Dispatchers.Main).launch { initializeList(pkmnList) }

            }else{
                //show error
            }
        }
    }
}