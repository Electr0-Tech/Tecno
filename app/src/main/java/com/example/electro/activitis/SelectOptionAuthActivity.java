package com.example.electro.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.electro.R;
import com.example.electro.databinding.ActivitySelectOptionAuthBinding;

public class SelectOptionAuthActivity extends AppCompatActivity {

    AppCompatButton btnRegistro, btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        ActivitySelectOptionAuthBinding binding = ActivitySelectOptionAuthBinding .inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_usuario);

        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

        binding.topLinearLayout1.setAnimation(bottom_down);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.cardView.setAnimation(fade_in);
                binding.cardView.setAnimation(fade_in);
                binding.textView.setAnimation(fade_in);
            }
        };
        handler.postDelayed(runnable, 1000);

        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLogin();
            }
        });

        btnRegistro= findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRegister();
            }
        });
    }

    private void gotoLogin() {
        Intent i= new Intent(SelectOptionAuthActivity.this, Login.class);
        startActivity(i);
    }

    private void gotoRegister() {
        Intent i= new Intent(SelectOptionAuthActivity.this, Registro.class);
        startActivity(i);
    }

}