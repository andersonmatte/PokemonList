
package br.com.andersonmatte.pokemonlist.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.andersonmatte.pokemonlist.R;
import br.com.andersonmatte.pokemonlist.adapter.HomeAdapter;
import br.com.andersonmatte.pokemonlist.entity.Pokemon;
import br.com.andersonmatte.pokemonlist.service.PokemonService;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private List<Pokemon> sortedList = new ArrayList<>();
    private PokemonService pokemonService;
    private MediaPlayer mySong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.configuraToolbar();
        // Inicia o audio
        mySong = MediaPlayer.create(HomeActivity.this, R.raw.audio);
        mySong.start();
        this.pokemonService = PokemonService.retrofit.create(PokemonService.class);
        this.init();
        gridView = findViewById(R.id.gridViewLib);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            public void onItemClick(AdapterView<?> parent, View v, int position, long row) {
                Intent fullScreenIntent = new Intent(getApplicationContext(), FullScreenImageActivity.class);
                String urlImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + sortedList.get(position).getId() + ".png";
                fullScreenIntent.setData(Uri.parse(urlImage));
                fullScreenIntent.putExtra("nome", sortedList.get(position).getName());
                startActivity(fullScreenIntent);
            }
        });
    }

    public void configuraToolbar() {
        getSupportActionBar().setTitle(null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.pikachu);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 50;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
    }

    private void init() {
        if (isOnline()) {
            for (int i = 1; i <= 12; i++) {
                getListPokemon(pokemonService, i);
            }
        } else {
            Toasty.error(HomeActivity.this, getResources().getString(R.string.networkInformationError), Toast.LENGTH_LONG, true).show();
        }
    }

    public void mais(View view) {
        if (isOnline()) {
            int lastIndex = getLastIndex();
            this.pokemons.clear();
            this.sortedList.clear();
            for (int i = lastIndex; i <= lastIndex + 11; i++) {
                getListPokemon(pokemonService, i);
            }
        } else {
            Toasty.error(HomeActivity.this, getResources().getString(R.string.networkInformationError), Toast.LENGTH_LONG, true).show();
        }
    }

    public void voltar(View view) {
        if (isOnline()) {
            int ultimo = getLastIndex() - 1;
            if (ultimo == 12) {
                Toasty.warning(HomeActivity.this, getResources().getString(R.string.primeirapagina), Toast.LENGTH_LONG, true).show();
            } else {
                int maximo = ultimo - 12;
                this.pokemons.clear();
                this.sortedList.clear();
                for (int i = maximo - 11; i <= maximo; i++) {
                    getListPokemon(pokemonService, i);
                }
            }
        } else {
            Toasty.error(HomeActivity.this, getResources().getString(R.string.networkInformationError), Toast.LENGTH_LONG, true).show();
        }
    }

    private void getListPokemon(PokemonService pokemonService, int i) {
        final Call<Pokemon> callUsuario = pokemonService.getListPokemon(i);
        callUsuario.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Pokemon pokemon = response.body();
                if (pokemon != null) {
                    // Adiciona o retorno da API na lista temporária.
                    pokemons.add(pokemon);
                    // Ordena a lista definitiva por Id.
                    sortedList = pokemons.stream()
                            .sorted(Comparator.comparingInt(Pokemon::getId))
                            .collect(Collectors.toList());
                    chamaAdapter();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Toasty.error(HomeActivity.this, getResources().getString(R.string.errobuscarapi), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private void chamaAdapter() {
        // Chama o Adapter pasando a lista ordenada.
        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), (ArrayList<Pokemon>) sortedList);
        gridView.setAdapter(adapter);
    }

    public int getLastIndex() {
        Pokemon pokemon = this.sortedList.get(sortedList.size() - 1);
        return pokemon.getId() + 1;
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mEthernet = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        NetworkInfo mMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mWifi.isConnected() || mMobile.isConnected() || mEthernet.isConnected();
    }

}