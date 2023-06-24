package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText nombrecontacto, apellidocontacto, telefonocontacto,domiciliocontacto,correcontacto,edadcontacto;
    Button update_button;
    CapaBaseDatos myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
//le colocamos this para hacer referencia a este activity
        myDB = new CapaBaseDatos(this);


        nombrecontacto = findViewById(R.id.NombreUp);
        apellidocontacto = findViewById(R.id.ApellidosUp);
        domiciliocontacto = findViewById(R.id.DomicilioUp);
        correcontacto= findViewById(R.id.Correo_ElectronicoUp);
        telefonocontacto = findViewById(R.id.TelefonoUp);
        edadcontacto= findViewById(R.id.EdadUp);


        update_button = findViewById(R.id.update_button);

        //lee los datos con el metodo void creado
        leercontacto();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos actualizados de los EditText en nuevas variables
                String nuevoNombre = nombrecontacto.getText().toString().trim();
                String nuevoApellido = apellidocontacto.getText().toString().trim();
                String nuevoDomicilio = domiciliocontacto.getText().toString().trim();
                String nuevoCorreo = correcontacto.getText().toString().trim();
                String nuevoTelefono = telefonocontacto.getText().toString().trim();
                String nuevaEdad = edadcontacto.getText().toString().trim();

                // Limpiar los errores previos
                nombrecontacto.setError(null);
                apellidocontacto.setError(null);
                domiciliocontacto.setError(null);
                correcontacto.setError(null);
                telefonocontacto.setError(null);
                edadcontacto.setError(null);

                //Valida los datos ingresados
                boolean hayErrores = false;

                if (nuevoNombre.isEmpty()) {
                    hayErrores = true;
                    nombrecontacto.setError("Ingresa un nombre");
                } else if (!nuevoNombre.matches("[a-zA-Z ]+")) {
                    hayErrores = true;
                    nombrecontacto.setError("No se permiten números ni caracteres especiales");
                }

                if (nuevoApellido.isEmpty()) {
                    hayErrores = true;
                    apellidocontacto.setError("Ingresa los apellidos");
                } else if (!nuevoApellido.matches("[a-zA-Z ]+")) {
                    hayErrores = true;
                    apellidocontacto.setError("No se permiten números ni caracteres especiales");
                }

                if (nuevoTelefono.isEmpty()) {
                    hayErrores = true;
                    telefonocontacto.setError("Ingresa un número de teléfono");
                } else if (!nuevoTelefono.matches("[0-9]{8}")) {
                    hayErrores = true;
                    telefonocontacto.setError("Ingresa un número de teléfono válido (8 dígitos)");
                }

                if (nuevaEdad.isEmpty()) {
                    hayErrores = true;
                    edadcontacto.setError("Ingresa la edad");
                } else if (!nuevaEdad.matches("[1-9][0-9]{0,2}")) {
                    hayErrores = true;
                    edadcontacto.setError("Ingresa una edad válida (hasta 3 dígitos y no negativa)");
                } else if (nuevaEdad.startsWith("0")) {
                    hayErrores = true;
                    edadcontacto.setError("Ingresa una edad válida (no puede empezar con 0)");
                }

                if (nuevoDomicilio.isEmpty()) {
                    hayErrores = true;
                    domiciliocontacto.setError("Ingresa el domicilio");
                }

                if (nuevoCorreo.isEmpty()) {
                    hayErrores = true;
                    correcontacto.setError("Ingresa el correo electrónico");
                } else if (!nuevoCorreo.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                    hayErrores = true;
                    correcontacto.setError("Ingresa un correo electrónico válido");
                }

                if (!hayErrores) {
                //si no hay errores actualizara los datos

                    CapaBaseDatos myDB = new CapaBaseDatos(UpdateActivity.this);
                    myDB.updateContacto(getIntent().getStringExtra("id"), nuevoNombre,
                            nuevoApellido,
                            nuevoDomicilio,
                            nuevoCorreo,
                            nuevoTelefono,
                            nuevaEdad);
                    //cuando actualice los datos este actitity(updateActivity) se cerrara y luego simulara un reinicio
                    //El reinicio para que se cierren todos los activitys y deje unicamente el MainActivity
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    finishAffinity();


                }
            }
        });



    }


    //METODO PARA MANDAR A TRAER EL CONTACTO A TRAVES DEL ID
    void leercontacto() {

        //tomamos el valor id configurado en CustomAdapter (tomamos el valor ID del recycleview)
        //El id lo tomamos para que haga una busqueda
        String idStr = getIntent().getStringExtra("id");

        if (TextUtils.isEmpty(idStr)) {
            Toast.makeText(this, "Ingrese un ID de contacto válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        int contactId = Integer.parseInt(idStr);

        Contactos contacto = myDB.getContacto(contactId);

        if (contacto != null) {
            nombrecontacto.setText(contacto.getNombre());
            apellidocontacto.setText(contacto.getApellido());
            domiciliocontacto.setText(contacto.getDomicilio());
            correcontacto.setText(contacto.getCorreoElectronico());
            telefonocontacto.setText(contacto.getTelefono());
            edadcontacto.setText(contacto.getEdad());

        } else {
            Toast.makeText(this, "No se encontró el contacto.", Toast.LENGTH_SHORT).show();
        }
    }

//Metodo onclick para regresar al Inicio
    public void regresar(View view) {
        Toast.makeText(UpdateActivity.this, "No se actualizaron los datos.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        finishAffinity();
    }
}