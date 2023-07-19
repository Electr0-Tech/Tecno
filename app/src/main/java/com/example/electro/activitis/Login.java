package com.example.electro.activitis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electro.R;

import com.example.electro.activitis.cliente.MapaClienteActivity;
import com.example.electro.activitis.repartidor.MapaRepartidorActivity;
import com.example.electro.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    AppCompatButton btnLogin;
    EditText email,pass;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    final LoadingDialog dialogo= new LoadingDialog(Login.this);
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref=getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);


        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

        binding.topLinearLayout.setAnimation(bottom_down);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.registerLayout.setAnimation(fade_in);
                binding.cardView.setAnimation(fade_in);
                binding.cardView2.setAnimation(fade_in);
                binding.textView.setAnimation(fade_in);
            }
        };
        handler.postDelayed(runnable, 1000);

        final TextView signUpBtn = findViewById(R.id.textView2);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });

        email= findViewById(R.id.textInputEmail);
        pass= findViewById(R.id.textInputPassword);
        btnLogin= findViewById(R.id.btnLoginP);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });



    }

    private void login() {

        String emaid=email.getText().toString();
        String contra=pass.getText().toString();
        dialogo.starloadingAlertDialog();
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogo.apagarAlert();
            }
        },1000);
        if(!emaid.isEmpty() && !contra.isEmpty()){
            if (contra.length() >= 6){
                mAuth.signInWithEmailAndPassword(emaid,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String user= pref.getString("user","");
                            Toast.makeText(Login.this, "Login exitoso", Toast.LENGTH_LONG).show();
                            if(user.equals("cliente")){
                                Intent i = new Intent(Login.this, MapaClienteActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);


                            }else{
                                Intent i = new Intent(Login.this, MapaRepartidorActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }

                        }else {
                            Toast.makeText(Login.this, "El email o la contraseña con incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }else{
                Toast.makeText(this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
            }

        }
    }
}





