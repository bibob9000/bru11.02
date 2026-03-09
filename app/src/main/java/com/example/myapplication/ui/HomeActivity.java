package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Apply bounce animation to welcome card
        LinearLayout homeCard = findViewById(R.id.homeCard);
        Animation bounceIn = AnimationUtils.loadAnimation(this, R.anim.bounce_in);
        homeCard.startAnimation(bounceIn);
    }
}
