package br.org.catolicasc.leitorrss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView txvID;
    TextView txvNome;
    TextView txvPeso;
    TextView txvAltura;
    ImageView imgPokemon;
    Bitmap imgPokemonAtual;

    public void voltar(View view) {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txvID = findViewById(R.id.tvID);
        txvNome = findViewById(R.id.tvNome);
        txvPeso = findViewById(R.id.tvPeso);
        txvAltura = findViewById(R.id.tvAltura);
        imgPokemon = findViewById(R.id.imgPokemon);
        Intent intent = getIntent();
        Pokemon p = (Pokemon) intent.getSerializableExtra("pokemon");

        txvID.setText(p.getId());
        txvNome.setText(p.getNome());
        txvPeso.setText(p.getPeso());
        txvAltura.setText(p.getAltura());

        ArrayList<Pokemon> pokemonArray = new ArrayList<>();
        PokemonListAdapter pokemonAdapter = new PokemonListAdapter(
                DetailActivity.this, R.layout.activity_detail, pokemonArray
        );

        imgPokemonAtual = pokemonAdapter.returnImagemTask(p.getImgUrl());
        imgPokemon.setImageBitmap(imgPokemonAtual);

    }
}