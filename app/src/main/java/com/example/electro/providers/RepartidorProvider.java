package com.example.electro.providers;

import com.example.electro.models.Cliente;
import com.example.electro.models.Repartidor;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RepartidorProvider {
    DatabaseReference database;

    public RepartidorProvider() {
        database= FirebaseDatabase.getInstance().getReference().child("Users").child("Repartidores");
    }

    public Task<Void> create(Repartidor repa){
        return database.child(repa.getId()).setValue(repa);
    }
}
