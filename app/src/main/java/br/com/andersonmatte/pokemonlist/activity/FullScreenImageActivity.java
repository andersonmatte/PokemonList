package br.com.andersonmatte.pokemonlist.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.andersonmatte.pokemonlist.R;

public class FullScreenImageActivity extends AppCompatActivity {

    private TextView name;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        this.name = findViewById(R.id.name);
        ImageView fullScreenImageView = (ImageView) findViewById(R.id.fullScreenImageView);
        Intent callingActivityIntent = getIntent();
        if(callingActivityIntent != null) {
            Uri imageUri = callingActivityIntent.getData();
            if(imageUri != null && fullScreenImageView != null) {
                Picasso.get().load(imageUri).into(fullScreenImageView);
            }
            if (callingActivityIntent.getStringExtra("nome") != null){
                String name  = callingActivityIntent.getStringExtra("nome");
                name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
                this.name.setText(name);
            }
        }
    }

}