/*package com.example.electro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.electro.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //agregar animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView welcome = findViewById(R.id.BienvenidoTextView);
        ImageView logo = findViewById(R.id.logoImagen);

        welcome.setAnimation(animacion2);
        logo.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}
*/
package com.example.electro.activitis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.electro.R;


public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DELAY = 4000;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private TextView name, slogan;
    private ImageView logo;
    private View topView1, topView2, topView3;
    private View bottomView1,bottomView2,bottomView3;

    private int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.bienvenidos);
        slogan = findViewById(R.id.eslogan);

        logo = findViewById(R.id.logo);

        topView1 = findViewById(R.id.topView1);
        topView2 = findViewById(R.id.topView2);
        topView3 = findViewById(R.id.topView3);

        bottomView1 =findViewById(R.id.bottomView1);
        bottomView2 =findViewById(R.id.bottomView2);
        bottomView3 =findViewById(R.id.bottomView3);

        Animation logoAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_animation);
        Animation nameAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.zoom_animation);

        Animation topView1Animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.top_views_animation);
        Animation topView2Animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.top_views_animation);
        Animation topView3Animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.top_views_animation);

        Animation bottomView1Animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_views_animation);
        Animation bottomView2Animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_views_animation);
        Animation bottomView3Animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_views_animation);

        topView1.startAnimation(topView1Animation);
        bottomView1.startAnimation(bottomView1Animation);

        topView1Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                topView2.setVisibility(View.VISIBLE);
                bottomView2.setVisibility(View.VISIBLE);

                topView2.startAnimation(topView2Animation);
                bottomView3.startAnimation(bottomView2Animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        topView2Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                topView3.setVisibility(View.VISIBLE);
                bottomView3.setVisibility(View.VISIBLE);

                topView3.startAnimation(topView3Animation);
                bottomView3.startAnimation(bottomView3Animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        topView3Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                logo.setVisibility(View.VISIBLE);
                logo.startAnimation(logoAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                name.setVisibility(View.VISIBLE);
                name.startAnimation(nameAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        nameAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                slogan.setVisibility(View.VISIBLE);
                final String animateText = slogan.getText().toString();
                slogan.setText("");
                count = 0;

                new CountDownTimer(animateText.length()*100,100){

                    @Override
                    public void onTick(long l) {
                        slogan.setText(slogan.getText().toString()+animateText.charAt(count));
                        count ++;
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si los permisos no están concedidos, solicitarlos
            System.out.println("Si los permisos no están concedidos, solicitarlos");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            startApp();
        } else {
            System.out.println("Si los permisos ya están concedidos, continuar con la lógica de la actividad");
            // Si los permisos ya están concedidos, continuar con la lógica de la actividad
            startApp();
        }
        startApp();
        System.out.println("Ni siquiera entre");


    }

    private void startApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Usuario.class);
                startActivity(intent);
                finish();
            }
        },8000);
    }
}

