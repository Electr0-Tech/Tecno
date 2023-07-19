package com.example.electro.providers;

import com.example.electro.models.Cliente;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClienteProvider {
    DatabaseReference database;

    public ClienteProvider() {
        database= FirebaseDatabase.getInstance().getReference().child("Users").child("Clientes");
    }

    public Task<Void> create(Cliente cliente){
        return database.child(cliente.getId()).setValue(cliente);
    }
}
