package com.example.suelliton.snake;


import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Tabuleiro extends AppCompatActivity {
    int tamGrid = 25;
    ImageView GRID[][] ;
    ArrayList SNAKE = new ArrayList<>();
    int direction[] = new int[2] ;
    int[] anterior = new int[2];
    int pos[]  = new int[2];
    int[] previous = new int[2];
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
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
                setPrevious(pos);
            }
        });
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 1;
                direction[1] = 0;
                setPrevious(pos);
            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = -1;
                direction[1] = 0;
                setPrevious(pos);
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 0;
                direction[1] = 1;
                setPrevious(pos);
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
       //primeira direção
       //previous[0] = (tamGrid/2) ;
       //previous[1] = (tamGrid/2);
       setBlack(GRID[tamGrid/2][tamGrid/2]);
       direction[0] = 0;
       direction[1] = 1;
        //define a posicao central como a cabeça da snake
       pos[0] = tamGrid/2;
       pos[1] = tamGrid/2;
       SNAKE.add(pos);//adiciona no vetor snake como se fosse a cabeça posicao 00 do arraylist
       move(direction,false);//passa a direcao para move
    }

    public void move(final int[] direction, final boolean changeDirection){

        final Handler handler = new Handler();

        new  Thread(new Runnable(){
                 public void run(){

                     handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setHead(direction);
                            move(direction,false);
                        }
                    },100);
                 }
        }).start();

    }


    public void setHead(int[] direction){

        pos = (int[]) SNAKE.get(0);//éga a cabeça
        setWhite(GRID[pos[0]][pos[1]]);//printa a cabeça
        previous = pos;
        pos[0] = pos[0] + direction[0];//incrementa a posicao dacabeça
        pos[1] = pos[1] + direction[1];//incrementa a posicao dacabeça
        pos = checkPos(pos);
        setBlack(GRID[pos[0]][pos[1]]);
        SNAKE.set(0, pos);


    }
    public void setPrevious(int[] pos){
        if(direction[0] == 0 && direction[1] == 1 ){
            setWhite(GRID[pos[0]][pos[1]-1]);
        }
        if(direction[0] == 0 && direction[1] == -1 ){
            setWhite(GRID[pos[0]][pos[1]+1]);
        }
        if(direction[0] == 1 && direction[1] == 0 ){
            setWhite(GRID[pos[0]-1][pos[1]]);
        }
        if(direction[0] == -1 && direction[1] == 0 ){
            setWhite(GRID[pos[0]+1][pos[1]]);
        }
    }

    public void setBody(){

    }

    public int[] checkPos(int[] pos){
        if(pos[0] == tamGrid-1){
            pos[0] = 0;
        }
        if(pos[1] == tamGrid-1){
            pos[1] = 0;
        }
        if(pos[0] == -1){
            pos[0] = tamGrid-1;
        }
        if(pos[1] == -1){
            pos[1] = tamGrid-1;
        }

        return pos;
    }


    public void setBlack(ImageView imageView){
        imageView.setImageResource(R.drawable.black);
    }
    public void setWhite(ImageView imageView){
        imageView.setImageResource(R.drawable.white);
    }








}
