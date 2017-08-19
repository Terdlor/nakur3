package com.example.l.nakur3;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button btMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        inizView(this);
    }

    //слушатель дл открытия активности марок
    View.OnClickListener onClickMark(final Context context){
        return (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MarkActivity.class);
               startActivity(intent);
            }
        });
    }

    void inizView(Context context){
        btMark = (Button) findViewById(R.id.btMark);
        btMark.setOnClickListener(onClickMark(context));
    }
}
