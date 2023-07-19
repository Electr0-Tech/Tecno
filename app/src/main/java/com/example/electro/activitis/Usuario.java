package com.example.electro.activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.example.electro.R;
import com.example.electro.activitis.cliente.MapaClienteActivity;
import com.example.electro.activitis.repartidor.MapaRepartidorActivity;
import com.example.electro.databinding.ActivityUsuarioBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Usuario extends AppCompatActivity {

    AppCompatButton btnSoyCliente,btnSoyRepartidor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUsuarioBinding binding = ActivityUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_usuario);
        pref= getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        final SharedPreferences.Editor editor=pref.edit();

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

        btnSoyCliente=findViewById(R.id.btnSoyCliente);
        btnSoyRepartidor=findViewById(R.id.btnSoyRepartidor);

        btnSoyCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editor.putString("user","cliente");
                editor.apply();
                goToSelectAuth();
            }
        });

        btnSoyRepartidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user","repartidor");
                editor.apply();
                goToSelectAuth();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String user = pref.getString("user","");
            if(user.equals("cliente")){
                Intent i = new Intent(Usuario.this, MapaClienteActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);


            }else{
                Intent i = new Intent(Usuario.this, MapaRepartidorActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }
    }

    private void goToSelectAuth() {
        Intent i= new Intent(Usuario.this, SelectOptionAuthActivity.class);
        startActivity(i);
    }
}