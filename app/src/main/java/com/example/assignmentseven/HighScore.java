package com.example.assignmentseven;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    String input = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.high_score);
        Intent intent = getIntent();
        Bundle i = getIntent().getExtras();
        ArrayList<String> arrayList = i.getStringArrayList("stringArray");
        //Full-Screen
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        input = intent.getStringExtra("username");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        ListView listView = (ListView)findViewById(R.id.lv_HighScore);
        listView.setAdapter(arrayAdapter);
    }

    public void onClickBack(View view){
        Toast.makeText(this, "Back to Main Activity", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
