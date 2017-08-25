package com.example.suelliton.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    Context c = this;

    Button btn_newGame ;
    Button btn_continue;
    Button btn_configure ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_newGame = (Button) findViewById(R.id.button_newGame);
        btn_continue = (Button) findViewById(R.id.button_continue);
        btn_configure = (Button) findViewById(R.id.button_configure);

        /*
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this)
                .load(R.drawable.snake_gif) // aqui é teu gif
                .asGif()
                .into(imageView);*/




        btn_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(c,Tabuleiro.class);
                startActivity(intent);
            }
        });

        btn_configure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Configure.class);
                startActivity(intent);
            }
        });


    }





}
