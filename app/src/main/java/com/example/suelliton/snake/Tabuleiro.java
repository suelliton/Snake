package com.example.suelliton.snake;


import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Tabuleiro extends AppCompatActivity {
    boolean eat = false;
    int points = 0;
    int tamGrid = 25;
    ImageView GRID[][] ;
    ArrayList SNAKE = new ArrayList<Arrays>();
    int direction[] = new int[2] ;
    int[] fruit = new int[2];
    int[] tail;

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

            }
        });
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 1;
                direction[1] = 0;

            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = -1;
                direction[1] = 0;

            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 0;
                direction[1] = 1;

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
       direction[0] = 0;
       direction[1] = 1;
       int pos[] = new int[2] ;
       pos[0] = tamGrid/2;
       pos[1] = tamGrid/2;
       SNAKE.add(pos);
       setFruit();
       move();
    }

    public void move(){
        final Handler handler = new Handler();
        new  Thread(new Runnable(){
                 public void run(){
                     handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setBody();
                            printSnake();
                            TextView t = (TextView) findViewById(R.id.deb);
                            String v = "";
                            for(int i = 0;i < SNAKE.size() ;i++){
                                int[] pos;
                                pos = (int[]) SNAKE.get(i);
                                v = v + "|"+pos[0]+"-"+pos[1];
                                t.setText(v);
                            }
                            //só printo os valores atuais
                            move();
                        }
                    },1000);
                 }
        }).start();

    }


    public int[] setHead(){
        //pego a posicao 0 referente a cabeça e somo com a direção para encontrar a nova posicao
        //apos seto na posicao 0 de snake o novo valor e antes disso verifico se a posicao é válida
        int[] pos = (int[]) SNAKE.get(0);//péga a cabeça
        pos[0] = pos[0] + direction[0];//incrementa a posicao dacabeça x
        pos[1] = pos[1] + direction[1];//incrementa a posicao dacabeça y
        pos = checkPos(pos);
        SNAKE.set(0, pos);
        return pos;
    }
    public void setBody(){
        //pego o ultimo elemento o rabd pra depois apagalo ou não
         tail = (int[]) SNAKE.get(SNAKE.size() - 1);
            //aqui realoco os elementos da cobra, o ultimo recebe o penultimo etc
        //a cabeça não recebe nada ainda
            for (int i = SNAKE.size() - 1; i > 0; i--) {
                int[] pos = new int[2];
                pos = (int[]) SNAKE.get(i - 1);
                SNAKE.set(i, pos);
            }

        //aqui chamo pra setar a cabeça
        int head[] ;
        head = setHead();
        //aqui checo se a cabeça comeu a fruta se simm ele adiciona as posições do ultimo elemento ao arra snake
        //o mesmo que seria apagado agora faz parte da cobra.
        //caso contrario seto a ultima referencia ou seja o rabo como sendo uma celula branca do proprio grid
        //fazendo com que a cobra ande

        if(checkEat(head) == true){
            SNAKE.add(tail);
            Toast.makeText(Tabuleiro.this, "Tamanho:"+SNAKE.size(), Toast.LENGTH_SHORT).show();
        }else{
            setWhite(GRID[tail[0]][tail[1]]);
        }

    }
    public void printSnake() {
        for (int i = 0; i < SNAKE.size(); i++) {
            int[] pos = (int[]) SNAKE.get(i);
            setBlack(GRID[pos[0]][pos[1]]);
        }
    }

    public boolean checkEat(int[] pos){
        if(pos[0] == fruit[0] && pos[1] == fruit[1]){
            setFruit();
            TextView tv = (TextView) findViewById(R.id.text_points);
            points +=50;
            tv.setText(""+ points);
            return true;
        }
        return false;
    }


    public void setFruit(){
        fruit[0] = new Random().nextInt(tamGrid-1);
        fruit[1] = new Random().nextInt(tamGrid-1);
        setOrange(GRID[fruit[0]][fruit[1]]);
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
    public void setOrange(ImageView imageView){
        imageView.setImageResource(R.drawable.orange);
    }








}
