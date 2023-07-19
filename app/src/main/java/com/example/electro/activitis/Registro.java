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
import android.widget.Toast;

import com.example.electro.R;
import com.example.electro.activitis.cliente.MapaClienteActivity;
import com.example.electro.activitis.repartidor.MapaRepartidorActivity;
import com.example.electro.databinding.ActivityRegistroBinding;
import com.example.electro.models.Cliente;
import com.example.electro.models.Repartidor;
import com.example.electro.providers.AuthProvider;
import com.example.electro.providers.ClienteProvider;
import com.example.electro.providers.RepartidorProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {
    SharedPreferences pref;

    AuthProvider authProvider;
    ClienteProvider clienteProvider;
    RepartidorProvider repartidorProvider;
    AppCompatButton btnRegistrar;
    EditText inputEmail,inputNombre,inputEdad,inputPass;
    final LoadingDialog dialogo= new LoadingDialog(Registro.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ActivityRegistroBinding binding = ActivityRegistroBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            pref=getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
            authProvider=new AuthProvider();
            clienteProvider= new ClienteProvider();
            repartidorProvider= new RepartidorProvider();


            Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            Animation bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            binding.topLinearLayout.setAnimation(bottom_down);

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    binding.cardView.setAnimation(fade_in);
                    binding.cardView2.setAnimation(fade_in);
                    binding.textView.setAnimation(fade_in);
                }
            };
            handler.postDelayed(runnable, 1000);

            inputEmail=findViewById(R.id.inputEmail);
            inputEdad=findViewById(R.id.inputEdad);
            inputNombre=findViewById(R.id.inputNombre);
            inputPass=findViewById(R.id.inputPass);
            btnRegistrar=findViewById(R.id.btnRegistrar);
            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickRegistro();
                }
            });
    }

    private void clickRegistro() {
       final String nombre= inputNombre.getText().toString();
       final String contra= inputPass.getText().toString();
       final String correo= inputEmail.getText().toString();
       final String edad= inputEdad.getText().toString();

        if(!nombre.isEmpty() && !contra.isEmpty() && !correo.isEmpty() && !edad.isEmpty()){
            if (validar()){
                dialogo.starloadingAlertDialog();
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogo.apagarAlert();
                    }
                },1000);
                registro(correo,contra,nombre,edad);
            }
        }else {
            Toast.makeText(this, "Existen campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    private void registro(final  String correo,final String contra,final  String nombre,final  String edad) {
        String slectedUser= pref.getString("user","");

        authProvider.register(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if(slectedUser.equals("repartidor")){
                        Repartidor repartidor= new Repartidor(id,nombre,correo,edad,"Toyota","ABSJDJ");
                        createRepartidor(repartidor);


                    }else if(slectedUser.equals("cliente")){
                        Cliente cliente= new Cliente(id,nombre,correo,edad);
                        createCliente(cliente);
                    }

                }else{
                    Toast.makeText(Registro.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createCliente(Cliente cliente){
        clienteProvider.create(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(Registro.this, MapaClienteActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    Toast.makeText(Registro.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Registro.this, "Registro No Exitoso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createRepartidor(Repartidor repa){
        repartidorProvider.create(repa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Registro.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Registro.this, MapaRepartidorActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(Registro.this, "Registro No Exitoso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/*
    private void saveUser(String id,String nombre,String correo,String edad) {
        String slectedUser= pref.getString("user","");
        User user = new User();
        user.setEmail(correo);
        user.setNombre(nombre);
        user.setEdad(edad);


        if(slectedUser.equals("repartidor")){
            database.child("Users").child("Repartidores").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registro.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Registro.this, "Registro No Exitoso", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else if(slectedUser.equals("cliente")){
            database.child("Users").child("Clientes").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registro.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Registro.this, "Registro No Exitoso", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }
*/
    public boolean validar(){
        String nombre= inputNombre.getText().toString();
        String contra= inputPass.getText().toString();
        String correo= inputEmail.getText().toString();
        Integer edad= Integer.valueOf(inputEdad.getText().toString());
        boolean valido=true;
        if(contra.length()<=6){
            valido=false;
            Toast.makeText(this, "La contraseña debe de ser de al menos 6 caracteres", Toast.LENGTH_SHORT).show();
        }
        if(edad<=17){
            valido=false;
            Toast.makeText(this, "Debes de ser mayor de edad", Toast.LENGTH_SHORT).show();
        }
        return valido;


    }
}
