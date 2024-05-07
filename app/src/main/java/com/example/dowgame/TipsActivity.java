package com.example.dowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenMain();
    }

    private void setScreenMain(){
        setContentView(R.layout.activity_tips);

        Button send_summary_of_method = findViewById(R.id.send_summary_of_method);
        send_summary_of_method.setOnClickListener(v -> setScreenSub());
    }

    private void setScreenSub(){
        setContentView(R.layout.summary_of_method);
        Button returnButton = findViewById(R.id.return_tips);
        returnButton.setOnClickListener(v -> setScreenMain());
    }

    public void return_home(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}