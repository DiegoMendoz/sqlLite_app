package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText Nombre, Apellidos, Domicilio, Correo_Electronico, Telefono, Edad;
    Button reset_button1, add_button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Obtener referencias a los elementos de la interfaz de usuario
        Nombre = findViewById(R.id.Nombre);
        Apellidos = findViewById(R.id.Apellidos);
        Domicilio = findViewById(R.id.Domicilio);
        Correo_Electronico = findViewById(R.id.Correo_Electronico);
        Telefono = findViewById(R.id.Telefono);
        Edad = findViewById(R.id.Edad);
        reset_button1 = findViewById(R.id.reset_button1);
        add_button1 = findViewById(R.id.add_button1);

        // Configurar el OnClickListener para el botón de agregar
        add_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos del contacto desde la interfaz de usuario
                String nombre = Nombre.getText().toString().trim();
                String apellido = Apellidos.getText().toString().trim();
                String telefono = Telefono.getText().toString().trim();
                String edad = Edad.getText().toString().trim();
                String domicilio = Domicilio.getText().toString().trim();
                String correo_electronico = Correo_Electronico.getText().toString().trim();

                // Validar que se hayan ingresado los datos necesarios
                if (!nombre.isEmpty() && !telefono.isEmpty() && !edad.isEmpty()) {
                    // Validar el campo de teléfono
                    if (telefono.matches("[0-9]{8}")) {
                        // Validar el campo de correo electrónico
                        if (correo_electronico.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                            // Validar el campo de edad
                            if (Integer.parseInt(edad) >= 0 && edad.length() <= 3 && !edad.startsWith("-")) {
                                // Crear un objeto Contactos con los datos
                                Contactos contact = new Contactos(nombre, apellido, telefono, edad, domicilio, correo_electronico);

                                // Agregar el contacto a la base de datos
                                CapaBaseDatos myDB = new CapaBaseDatos(AddActivity.this);
                                myDB.addContacto(contact);

                                // Mostrar un mensaje de éxito
                                Toast.makeText(AddActivity.this, "Contacto agregado con éxito", Toast.LENGTH_SHORT).show();

                                // Limpiar los campos de entrada
                                Nombre.setText("");
                                Apellidos.setText("");
                                Telefono.setText("");
                                Edad.setText("");
                                Domicilio.setText("");
                                Correo_Electronico.setText("");
                            } else {
                                // Mostrar un mensaje de error para la edad inválida
                                Toast.makeText(AddActivity.this, "Ingresa una edad válida (hasta 3 dígitos y no negativa)", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Mostrar un mensaje de error para el correo electrónico inválido
                            Toast.makeText(AddActivity.this, "Ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Mostrar un mensaje de error para el teléfono inválido
                        Toast.makeText(AddActivity.this, "Ingresa un número de teléfono válido (8 dígitos)", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mostrar un mensaje de error para los campos vacíos
                    Toast.makeText(AddActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el OnClickListener para el botón de reseteo
        reset_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Limpiar los campos de entrada
                Nombre.setText("");
                Apellidos.setText("");
                Telefono.setText("");
                Edad.setText("");
                Domicilio.setText("");
                Correo_Electronico.setText("");
            }
        });
    }
}