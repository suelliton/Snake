package com.example.suelliton.snake;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Tabuleiro extends AppCompatActivity {
    int tamGrid = 25;
    ImageView GRID[][] ;

    ArrayList SNAKE = new ArrayList<Array>();
    int direction[] = {0,1} ;
    final int[] cell = new int[]{0, 0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabuleiro);



        Button btn_left = (Button) findViewById(R.id.btn_left);
        Button btn_up = (Button) findViewById(R.id.btn_up);
        Button btn_down = (Button) findViewById(R.id.btn_down);
        Button btn_right = (Button) findViewById(R.id.btn_right);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 0;
                direction[1] =-1;
                move(direction);
            }
        });
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 1;
                direction[1] = 0;
                move(direction);
            }
        });


        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        grid.setColumnCount(tamGrid);
        grid.setRowCount(tamGrid);
        GRID = new ImageView[tamGrid][tamGrid];



        for(int i = 0; i < tamGrid; i++){
            for(int j= 0;j < tamGrid; j++){
                LayoutInflater inflater = LayoutInflater.from(this);
                ImageView img = (ImageView) inflater.inflate(R.layout.inflate,grid, false);
                img.setImageResource(R.drawable.white);
                grid.addView(img);
                GRID[i][j] = img;
            }
        }
        startGame();
    }


    public void startGame(){
       ImageView img =  GRID[tamGrid/2][tamGrid/2] ;
       img.setImageResource(R.drawable.black);


       move(direction);


    }

    public void move( final int[] direction){

        SNAKE.add(cell);
        final int i = cell[0];
        final int j = cell[1];
        final Handler handler = new Handler();
        new  Thread(new Runnable(){

                 public void run(){

                     handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ImageView img = GRID[i][j] ;
                            img.setImageResource(R.drawable.black);
                        cell[0] += direction[0] + i  ;
                        cell[1] += direction[1] + j;
                        move(direction);
                        }
                    },1000);
                 }
        }).start();

    }









}
