package com.example.suelliton.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        int points = bundle.getInt("points");
        int record = bundle.getInt("record");
        TextView tx_points = (TextView) findViewById(R.id.tx_points);
        tx_points.setText(""+points);
        TextView tx_message = (TextView) findViewById(R.id.tx_message);
        if(points <= record ){
            tx_message.setText("Não foi desta vez :(, seu record continua sendo:"+ record +"pontos");
        }else{
            tx_message.setText("Parabéns você alcançou um novo record!! "+points+" pontos");
        }

        Button btn_exit = (Button) findViewById(R.id.btn_exit);
        Button btn_newGame = (Button) findViewById(R.id.btn_newGame);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this,Tabuleiro.class);
                startActivity(intent);
            }
        });

    }
}
