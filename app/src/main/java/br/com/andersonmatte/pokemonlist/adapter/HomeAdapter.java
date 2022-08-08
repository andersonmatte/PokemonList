package br.com.andersonmatte.pokemonlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.andersonmatte.pokemonlist.R;
import br.com.andersonmatte.pokemonlist.entity.Card;
import br.com.andersonmatte.pokemonlist.entity.Pokemon;

public class HomeAdapter extends ArrayAdapter<Pokemon> {

    public HomeAdapter(@NonNull Context context, ArrayList<Pokemon> pokemons) {
        super(context, 0, pokemons);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }
        Pokemon pokemon = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.idNomeLib);
        ImageView courseIV = listitemView.findViewById(R.id.idLogo);
        String name  = pokemon.getName();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        courseTV.setText(name);
        String urlImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + getItem(position).getId() + ".png";
        Picasso.get().load(urlImage).into(courseIV);
        return listitemView;
    }

}

