package com.example.assignmentseven;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     public EditText mEdit;
    public TextView mText;
    private String input = "";
    public static ArrayList<String> nameList = new ArrayList<>();
    public static ArrayList<Integer> scoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        //TextView tw = (TextView)findViewById(R.id.username);

        //Full-Screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    public void onClickGame(View v){

        mEdit = (EditText)findViewById(R.id.username);
        input = mEdit.getText().toString();

        if (input.matches("")) {
            Toast.makeText(this, "Enter your alias.", Toast.LENGTH_SHORT).show();
            return;
        }
        else{

            Toast.makeText(this, "Game start", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, GameScreen.class);

            nameList.add(input);
            //i.setAction(i.ACTION_SEND);
            //i.setType("text/plain");
            i.putExtra("username",input);

            startActivity(i);
            mEdit.setText(null);
        }

    }

    public void onClickHigh(View v){
        Toast.makeText(this, "High Score", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, HighScore.class);
        i.putExtra("stringArray",nameList);
        i.putExtra("scoreArray",scoreList);
        startActivity(i);
    }



}