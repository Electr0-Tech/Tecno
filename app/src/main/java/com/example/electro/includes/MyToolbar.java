package com.example.electro.includes;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.electro.R;

public class MyToolbar {

    public static  void show(AppCompatActivity activity,String titulo, boolean boton){
        Toolbar toolbar= activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(titulo);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(boton);
    }
}
