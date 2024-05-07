package com.example.dowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectGameMode extends AppCompatActivity {

    // 選択肢
    private final String[] spinner_items_for_quiz_num = {"5問", "10問", "15問", "20問","25問","30問"};
    private final String[] spinner_items_for_quiz_time = {"30秒", "60秒", "90秒", "120秒","150秒","180秒"};
    private TextView spinner_text_num, spinner_text_time;
    private int total_quiz_num = 5; // adjust by spinner
    private int total_quiz_time = 60; // adjust by spinner, unit is second
    private final CheckBox[] checkBox = new CheckBox[2]; // check box
    private final int default_check_box = 0;
    private String check_mode = "num"; // which check box was checked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game_mode);

        Spinner spinner_num = findViewById(R.id.spinner_num);
        Spinner spinner_time = findViewById(R.id.spinner_time);
        spinner_text_num = findViewById(R.id.spinner_text_num);
        spinner_text_time = findViewById(R.id.spinner_text_time);

        // ArrayAdapter for num-mode
        ArrayAdapter<String> adapter_num = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_items_for_quiz_num);
        adapter_num.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_num.setAdapter(adapter_num);
        spinner_num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // when item is selected
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                total_quiz_num = Integer.parseInt(item.substring(0, item.length() - 1));
                spinner_text_num.setText(getString(R.string.select_game_mode_spinner_text, item));
            }
            // when item isn't selected
            public void onNothingSelected(AdapterView<?> parent) {
                String temp = spinner_items_for_quiz_num[0];
                total_quiz_num = Integer.parseInt(temp.substring(0, temp.length() - 1));
            }
        });

        // ArrayAdapter for time-mode
        ArrayAdapter<String> adapter_time = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_items_for_quiz_time);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_time.setAdapter(adapter_time);
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // when item is selected
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                total_quiz_time = Integer.parseInt(item.substring(0, item.length() - 1));
                spinner_text_time.setText(getString(R.string.select_game_mode_spinner_text, item));
            }
            // when item isn't selected
            public void onNothingSelected(AdapterView<?> parent) {
                String temp = spinner_items_for_quiz_time[0];
                total_quiz_time = Integer.parseInt(temp.substring(0, temp.length() - 1));
            }
        });

        checkBox[0] = findViewById(R.id.checkbox_num);
        checkBox[1] = findViewById(R.id.checkbox_time);
        checkBox[0].setChecked(false);
        checkBox[1].setChecked(false);
        checkBox[default_check_box].setChecked(true);
        checkBox[0].setOnClickListener(new check_box_click());
        checkBox[1].setOnClickListener(new check_box_click());

    }

    public void start_game(View view) {
        if (check_mode == "num") {
            Intent intent = new Intent(getApplicationContext(), Game.class);
            intent.putExtra("TOTAL_QUIZ_NUM", total_quiz_num);
            startActivity(intent);
        }
        else if (check_mode == "time"){
            Intent intent = new Intent(getApplicationContext(), Game.class);
            intent.putExtra("TOTAL_QUIZ_NUM", total_quiz_num);
            startActivity(intent);
        }
    }

    public void return_home(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    class check_box_click implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.checkbox_num) {
                check_mode = "num";
                checkBox[1].setChecked(false);
            }
            else if(view.getId() == R.id.checkbox_time) {
                check_mode = "time";
                checkBox[0].setChecked(false);
            }
        }
    }

}