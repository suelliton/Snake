package com.example.suelliton.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Tabuleiro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabuleiro);

        GridLayout grid = new GridLayout(this);
        Button b = new Button(this);

        for(int i = 1; i < 5; i++){
            for(int j=1;j < 5; j++){
                GridLayout.Spec linha = GridLayout.spec(i);
                GridLayout.Spec coluna = GridLayout.spec(j);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(linha,coluna);
                ImageView im = new ImageView(this);
                im.setImageResource(R.mipmap.ic_launcher);
                grid.addView(im,lp);

            }
        }
        setContentView(grid);
    }
}
