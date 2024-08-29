package com.example.wirelesscalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private Button firstChoice ;
    private Button secondChoice ;
    private Button thirdChoice ;
    private Button fourthChoice ;
    private Button fifthChoice ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firstChoice = findViewById(R.id.firstChoice);
        secondChoice = findViewById(R.id.secondChoice);
        thirdChoice = findViewById(R.id.thirdChoice);
        fourthChoice = findViewById(R.id.fourthChoice);
        fifthChoice = findViewById(R.id.fifthChoice);

        firstChoice.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FirstChoice.class)));
        secondChoice.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SecondChoice.class)));
        thirdChoice.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ThirdChoice.class)));
        fourthChoice.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FourthChoice.class)));
        fifthChoice.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FifthChoice.class)));

    }
}