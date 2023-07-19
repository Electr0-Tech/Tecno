package com.example.electro.models;

public class Repartidor {
    String id;
    String nombre;
    String email;
    String edad;

    String marcaVehiculo;
    String placaVehiculo;


    public Repartidor(){

    }

    public Repartidor(String id, String nombre, String email, String edad, String marcaVehiculo, String placaVehiculo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.marcaVehiculo = marcaVehiculo;
        this.placaVehiculo = placaVehiculo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }
}
