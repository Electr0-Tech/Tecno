package com.example.electro.activitis.repartidor;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.electro.R;
import com.example.electro.activitis.Usuario;
import com.example.electro.includes.MyToolbar;
import com.example.electro.providers.AuthProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapaRepartidorActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap map;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SupportMapFragment mapFragment;
    private Location previousLocation;

    AuthProvider authProvider;
    private final static int LOCATION_REQUEST_CODE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocation;
    private Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_repartidor);

        // Inicializar proveedor de autenticación
        authProvider = new AuthProvider();

        // Obtener referencia al fragmento del mapa
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Inicializar el cliente de ubicación fusionada
        fusedLocation = LocationServices.getFusedLocationProviderClient(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Verificar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Habilitar la capa de ubicación del mapa
            map.setMyLocationEnabled(true);

            // Obtener la ubicación actual y realizar las operaciones deseadas
            getCurrentLocation();
            startLocationUpdates();
        } else {
            // Si los permisos de ubicación no están concedidos, solicitarlos al usuario
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    private void getCurrentLocation() {
        // Verificar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obtener la última ubicación conocida
            fusedLocation.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Se ha obtenido la ubicación actual
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                // Aquí puedes realizar las operaciones con la ubicación actual
                                // Por ejemplo, mover la cámara y agregar un marcador
                                moveCameraToLocation(latitude, longitude);
                                addMarkerToLocation(latitude, longitude);
                            }
                        }
                    });
        } else {
            // Si los permisos de ubicación no están concedidos, solicitarlos al usuario
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void moveCameraToLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15f)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void addMarkerToLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Ubicación actual");
        map.addMarker(markerOptions);
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        // Configurar opciones de la solicitud de ubicación, como intervalo de actualización y precisión
        // ...

        LocationCallback locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                System.out.println("Entre al loolbak");
                for (Location location : locationResult.getLocations()) {
                    System.out.println("Locaca"+location.getLatitude()+""+location.getLongitude());
                    if (previousLocation != null) {
                        System.out.println("Localizaiones"+previousLocation.getLatitude()+""+previousLocation.getLongitude());
                        double distance = location.distanceTo(previousLocation);
                        if (distance > 0) {
                            System.out.println("Distacioa es mayor a 0");
                            updateMarker(location);
                        }
                    }
                    previousLocation = location;
                }
            }
        };

        // Solicitar actualizaciones de ubicación utilizando el FusedLocationProviderClient
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void updateMarker(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Si ya tienes un marcador en el mapa, puedes actualizarlo directamente
        if (marker != null) {
            marker.setPosition(latLng);
        } else {
            // Si no tienes un marcador creado previamente, crea uno nuevo y agrégalo al mapa
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title("Ubicación actual")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carrito));
            marker = map.addMarker(markerOptions);
        }

        // Mueve la cámara del mapa a la nueva ubicación
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        System.out.println("Nueva localizacion"+location.getLongitude());
        System.out.println("Nueva localizacion"+location.getLatitude());

        // Realiza las acciones que deseas con la nueva ubicación, como actualizar el marcador en el mapa
        updateMarker(location);


    }
}