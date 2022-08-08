package br.com.andersonmatte.pokemonlist.service;

import br.com.andersonmatte.pokemonlist.entity.Pokemon;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonService {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("{numero}/")
    Call<Pokemon> getListPokemon(@Path("numero") int numero);

}
