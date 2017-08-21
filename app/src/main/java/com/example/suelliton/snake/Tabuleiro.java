package com.example.suelliton.snake;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Tabuleiro extends AppCompatActivity {
    int tamGrid = 25;
    ImageView GRID[][] ;
    int position[]  ;
    ArrayList SNAKE = new ArrayList<>();
    int direction[] = {0,1} ;
    int[] currentDirection;
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
                move( direction,true);
            }
        });
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 1;
                direction[1] = 0;
                move(direction,true);
            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = -1;
                direction[1] = 0;
                move(direction,true);
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 0;
                direction[1] = 1;
                move(direction,true);
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
       ImageView img =  GRID[tamGrid/2][tamGrid/2] ;//recupera o imageview central
       img.setImageResource(R.drawable.black);//pinta a primeira celula central

       position[0] = tamGrid/2;//define a posicao central como a cabeça da snake
       position[1] = tamGrid/2;
       Log.i("position", String.valueOf(position));
       SNAKE.add(position);//adiciona no vetor snake como se fosse a cabeça posicao 00 do arraylist
       Log.i("snake", String.valueOf(SNAKE.get(0)));
       move(direction,false);//passa a direcao para move


    }

    public void move(final int[] direction, final boolean changeDirection){
        currentDirection = direction;
        int pos[]  = new int[2];
        pos = (int[]) SNAKE.get(0);
        final int i = (int) pos[0];
        final int j = (int) pos[1];

        final Handler handler = new Handler();

        final int[] finalPos = pos;
        new  Thread(new Runnable(){
                 public void run(){

                     handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            setBlack(GRID[i][j]);

                            setWhite(GRID[i - direction[0]][j - direction[1]]);


                            finalPos[0] = i + direction[0];
                            finalPos[0] = j + direction[1];
                            SNAKE.set(0, finalPos);


                            move(direction,false);
                        }
                    },1000);
                 }
        }).start();

    }


    public void setBlack(ImageView imageView){
        imageView.setImageResource(R.drawable.black);
    }
    public void setWhite(ImageView imageView){
        imageView.setImageResource(R.drawable.white);
    }








}
