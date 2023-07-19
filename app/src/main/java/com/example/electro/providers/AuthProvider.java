package com.example.electro.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider {
    FirebaseAuth auth;

    public AuthProvider() {
        auth= FirebaseAuth.getInstance();
    }

    public Task<AuthResult> register(String email, String pass){
        return auth.createUserWithEmailAndPassword(email,pass);
    }

    public Task<AuthResult> login(String email, String pass){
        return auth.signInWithEmailAndPassword(email,pass);
    }

    public void cerrarSesion(){
        auth.signOut();
    }
}
