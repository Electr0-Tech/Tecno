package com.example.electro.activitis.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.electro.R;
import com.example.electro.activitis.Usuario;
import com.example.electro.activitis.repartidor.MapaRepartidorActivity;
import com.example.electro.includes.MyToolbar;
import com.example.electro.providers.AuthProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapaClienteActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button cerrarSe;
    AuthProvider authProvider;
    private GoogleMap map;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_cliente);
        MyToolbar.show(MapaClienteActivity.this,"Cliente",false);
        authProvider= new AuthProvider();
        mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*
        cerrarSe=findViewById(R.id.btnCerrarSesion);
        cerrarSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authProvider.cerrarSesion();
                Intent i = new Intent(MapaClienteActivity.this, Usuario.class);
                startActivity(i);
                finish();
            }
        });*/
    }

    public void cerrar(){
        authProvider.cerrarSesion();
        Intent i = new Intent(MapaClienteActivity.this, Usuario.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cliente_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.action_cerrar){
            cerrar();
        }
        return super.onOptionsItemSelected(item);
    }
}