package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

                // Limpiar los errores previos
                Nombre.setError(null);
                Apellidos.setError(null);
                Telefono.setError(null);
                Edad.setError(null);
                Domicilio.setError(null);
                Correo_Electronico.setError(null);

                // Validar que se hayan ingresado los datos necesarios
                boolean hayErrores = false;

                if (nombre.isEmpty()) {
                    hayErrores = true;
                    Nombre.setError("Ingresa un nombre");
                } else if (!nombre.matches("[a-zA-Z ]+")) {
                    hayErrores = true;
                    Nombre.setError("No se permiten números ni caracteres especiales");
                }

                if (apellido.isEmpty()) {
                    hayErrores = true;
                    Apellidos.setError("Ingresa los apellidos");
                } else if (!apellido.matches("[a-zA-Z ]+")) {
                    hayErrores = true;
                    Apellidos.setError("No se permiten números ni caracteres especiales");
                }

                if (telefono.isEmpty()) {
                    hayErrores = true;
                    Telefono.setError("Ingresa un número de teléfono");
                } else if (!telefono.matches("[0-9]{8}")) {
                    hayErrores = true;
                    Telefono.setError("Ingresa un número de teléfono válido (8 dígitos)");
                }

                if (edad.isEmpty()) {
                    hayErrores = true;
                    Edad.setError("Ingresa la edad");
                } else if (!edad.matches("[1-9][0-9]{0,2}")) {
                    hayErrores = true;
                    Edad.setError("Ingresa una edad válida (hasta 3 dígitos y no negativa)");
                } else if (edad.startsWith("0")) {
                    hayErrores = true;
                    Edad.setError("Ingresa una edad válida (no puede empezar con 0)");
                }

                if (domicilio.isEmpty()) {
                    hayErrores = true;
                    Domicilio.setError("Ingresa el domicilio");
                }

                if (correo_electronico.isEmpty()) {
                    hayErrores = true;
                    Correo_Electronico.setError("Ingresa el correo electrónico");
                } else if (!correo_electronico.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                    hayErrores = true;
                    Correo_Electronico.setError("Ingresa un correo electrónico válido");
                }

                if (!hayErrores) {
                    // Verificar si el contacto ya existe en la base de datos
                    if (contactoExistente(nombre, apellido)) {
                        // Mostrar un mensaje de error para el contacto duplicado
                        Toast.makeText(AddActivity.this, "El contacto ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        // Crear un objeto Contactos con los datos
                        Contactos contact = new Contactos(nombre, apellido, telefono, edad, domicilio, correo_electronico);

                        // Agregar el contacto a la base de datos
                        CapaBaseDatos myDB = new CapaBaseDatos(AddActivity.this);
                        myDB.addContacto(contact);

                        // Mostrar un mensaje de éxito
                        Toast.makeText(AddActivity.this, "Contacto agregado con éxito", Toast.LENGTH_SHORT).show();

                        // Redireccionar a MainActivity
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Finalizar AddActivity para evitar volver atrás
                    }
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
                // Limpiar los errores
                Nombre.setError(null);
                Apellidos.setError(null);
                Telefono.setError(null);
                Edad.setError(null);
                Domicilio.setError(null);
                Correo_Electronico.setError(null);
            }
        });
    }

    private boolean contactoExistente(String nombre, String apellido) {
        // Verificar si el contacto ya existe en la base de datos
        CapaBaseDatos myDB = new CapaBaseDatos(AddActivity.this);
        return myDB.existeContacto(nombre, apellido);
    }
}