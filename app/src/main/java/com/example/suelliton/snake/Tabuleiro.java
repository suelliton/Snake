package com.example.suelliton.snake;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


public class Tabuleiro extends AppCompatActivity {

    ImageView GRID[][] ;
    ArrayList SNAKE = new ArrayList<>();
    int points = 0;
    int tamGrid = 25;
    int direction[] = new int[2] ;
    int[] fruit = new int[2];
    boolean pause;
    int record = 0;
    int size;
    int difficult;
    int speed ;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabuleiro);

        setupGame();

        final ImageButton btn_left = (ImageButton) findViewById(R.id.imb_left);
        final ImageButton btn_up = (ImageButton) findViewById(R.id.imb_up);
        final ImageButton btn_down = (ImageButton) findViewById(R.id.imb_down);
        final ImageButton btn_right = (ImageButton) findViewById(R.id.imb_right);
        final ImageButton btn_pause = (ImageButton) findViewById(R.id.imb_pause);


        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 0;
                direction[1] =-1;
                btn_left.setClickable(false);
                btn_right.setClickable(false);
                btn_down.setClickable(true);
                btn_up.setClickable(true);

            }
        });
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 1;
                direction[1] = 0;
                btn_left.setClickable(true);
                btn_right.setClickable(true);
                btn_down.setClickable(false);
                btn_up.setClickable(false);

            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = -1;
                direction[1] = 0;
                btn_left.setClickable(true);
                btn_right.setClickable(true);
                btn_down.setClickable(false);
                btn_up.setClickable(false);

            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction[0] = 0;
                direction[1] = 1;
                btn_left.setClickable(false);
                btn_right.setClickable(false);
                btn_down.setClickable(true);
                btn_up.setClickable(true);

            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pause) {
                    pause = true;
                    btn_pause.setImageResource(R.drawable.play_teste);
                }else {
                    pause = false;
                    btn_pause.setImageResource(R.drawable.pause_teste);
                    move();
                }

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
    public  void setupGame(){
        recoveryData();
        if(difficult == 1){
            speed = 2000;
        }else if(difficult == 2){
            speed = 1000;
        } else if(difficult == 3){
            speed = 500;
        }
        if(size == 50){
            tamGrid= 50;
        }else if(size == 25){
            tamGrid = 25;
        }
    }
    public void saveData(){

        SharedPreferences prefs = getSharedPreferences("preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("record",record);
        editor.putInt("size",size);
        editor.putInt("difficult",difficult);
        editor.putInt("speed",speed);
        editor.commit();
    }
    public void recoveryData(){

        SharedPreferences prefs = getSharedPreferences("preferences",MODE_PRIVATE);
        record = prefs.getInt("record",0);
        size = prefs.getInt("size",25);
        difficult = prefs.getInt("difficult",2);
        speed = prefs.getInt("speed",1000);
    }

    public void startGame(){

       direction[0] = 0;
       direction[1] = 1;
       int[] pos = new int[2] ;
       pos[0] = size/2;
       pos[1] = size/2;
        SNAKE.add(0,pos);
        setFruit();
        move();

    }

    public void move(){

            final Handler handler = new Handler();
            new Thread(new Runnable() {
                public void run() {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(!pause) {
                            setBody();
                            printSnake();

                            speed = speed -1;
                            move();
                            }

                        }
                    }, speed);
                }
            }).start();

    }


    public int[] setHead(){
        //pego a posicao 0 referente a cabeça e somo com a direção para encontrar a nova posicao
        //apos seto na posicao 0 de snake o novo valor e antes disso verifico se a posicao é válida
        int[] pos = new int[2];
        int[] p = (int[]) SNAKE.get(0);//péga a cabeça
        pos[0] = p[0] + direction[0];//incrementa a posicao dacabeça x
        pos[1] = p[1] + direction[1];//incrementa a posicao dacabeça y
        pos = checkPos(pos);
        SNAKE.set(0, pos);
        return pos;
    }
    public void setBody(){
        int[] tail;
        //pego o ultimo elemento o rabd pra depois apagalo ou não
         tail = (int[]) SNAKE.get(SNAKE.size() - 1);
            //aqui realoco os elementos da cobra, o ultimo recebe o penultimo etc
        //a cabeça não recebe nada ainda
            for (int i = SNAKE.size() - 1; i > 0; i--) {
                int[] pos ;
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
        if(checkColision(head)){
            saveData();
            Bundle bundle = new Bundle();
            bundle.putInt("points",points);
            bundle.putInt("record",record);
            Intent intent = new Intent(Tabuleiro.this,Result.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }


    public void printSnake() {
        for (int i = 0; i < SNAKE.size(); i++) {
            int[] pos = new int[2];
            pos = (int[]) SNAKE.get(i);
            setBlack(GRID[pos[0]][pos[1]]);
        }
    }

    public boolean checkEat(int[] pos){
        if(pos[0] == fruit[0] && pos[1] == fruit[1]){
            setFruit();
            TextView tv = (TextView) findViewById(R.id.tx_points);
            points +=50;
            if(points >= record){
                record = points;
            }
            tv.setText("Points:"+ points);
            return true;
        }
        return false;
    }

    public boolean checkColision(int[] pos){

        for(int i=1;i < SNAKE.size();i++){
            int[] p = (int[]) SNAKE.get(i);
            if(pos[0] == p[0] && pos[1] == p[1]){
                return true;
            }
        }
        return false;
    }


    public void setFruit(){

        fruit[0] = new Random().nextInt((tamGrid-1));
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

    @Override
    protected void onPause() {
        super.onPause();
        if(!pause)
            pause = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pause = false;
    }



}
