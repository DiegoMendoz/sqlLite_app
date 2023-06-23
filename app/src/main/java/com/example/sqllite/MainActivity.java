package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
FloatingActionButton add_button;
ImageView empty_imageview;
TextView no_data;
CapaBaseDatos myDB;
ArrayList<String> contactos_id,contactos_nombre,contactos_apellido,contactos_telefono,contactos_domicilio,contactos_correo_electronico,contactos_edad;
CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.reyclerView);
        add_button=findViewById(R.id.add_button);
        empty_imageview=findViewById(R.id.empty_imageview);
        no_data=findViewById(R.id.no_data);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                updateAdapter();
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);

            }
        });
        myDB = new CapaBaseDatos(MainActivity.this);
        contactos_id = new ArrayList<>();
        contactos_nombre = new ArrayList<>();
        contactos_apellido = new ArrayList<>();
        contactos_telefono = new ArrayList<>();
        contactos_domicilio = new ArrayList<>();
        contactos_correo_electronico = new ArrayList<>();
        contactos_edad = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this,contactos_id,contactos_nombre,contactos_apellido,contactos_telefono,contactos_edad);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }
    void storeDataInArrays(){


        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No hay contactos.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                contactos_id.add(cursor.getString(0));
                contactos_nombre.add(cursor.getString(2));
                contactos_apellido.add(cursor.getString(1));
                contactos_telefono.add(cursor.getString(3));
                contactos_correo_electronico.add(cursor.getString(4));
                contactos_domicilio.add(cursor.getString(5));
                contactos_edad.add(cursor.getString(6));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }

    }
    private void updateAdapter() {
        contactos_id.clear();
        contactos_nombre.clear();
        contactos_apellido.clear();
        contactos_telefono.clear();
        contactos_domicilio.clear();
        contactos_correo_electronico.clear();
        contactos_edad.clear();

        storeDataInArrays();
        customAdapter.notifyDataSetChanged();
    }
}
