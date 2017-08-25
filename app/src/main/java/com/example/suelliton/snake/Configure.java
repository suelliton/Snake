package com.example.suelliton.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.security.acl.Group;

public class Configure extends AppCompatActivity {
    RadioGroup rdb_group_dificult;
    RadioGroup rdb_group_size ;
    int difficult = 2;
    int size = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        final RadioGroup rdb_group_dificult = (RadioGroup) findViewById(R.id.rdb_group_dificult);
        final RadioGroup rdb_group_size = (RadioGroup) findViewById(R.id.rdb_group_size);

        Button btn_save = (Button) findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdb_group_dificult.getCheckedRadioButtonId() == R.id.rdb_easy){
                    difficult = 1;
                }else if(rdb_group_dificult.getCheckedRadioButtonId() == R.id.rdb_hard){
                    difficult = 2;
                }else if(rdb_group_dificult.getCheckedRadioButtonId() == R.id.rdb_veteran){
                    difficult = 3;
                }

                if(rdb_group_size.getCheckedRadioButtonId() == R.id.rdb_50){
                    size = 50 ;
                }else if(rdb_group_size.getCheckedRadioButtonId() == R.id.rdb_25){
                    size = 25;
                }
                saveDataConfigure(difficult,size);
                finish();
            }
        });


    }
    public void saveDataConfigure(int difficult,int size){

        SharedPreferences prefs = getSharedPreferences("preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("difficult",difficult);
        editor.putInt("size",size);
        Toast.makeText(getBaseContext(),"dificuldade:"+difficult+"tamanho:"+size , Toast.LENGTH_SHORT).show();

    }


}
