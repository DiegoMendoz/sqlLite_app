package com.example.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Ruano.
 */
public class CapaBaseDatos extends SQLiteOpenHelper {
    private Context context;
    private static final int VERSION_BASEDATOS = 6;

    // Nombre de la BD
    private static final String NOMBRE_BASEDATOS = "contactosDB";

    // Nombre de la Tabla
    private static final String TABLA_CONTACTOS = "contactos";

    // Nombres de las Columnas
    private static final String ID_CONTACTO = "id";
    private static final String NOMBRE_CONTACTO = "nombre";
    private static final String APELLIDO_CONTACTO = "apellido";
    private static final String TELEFONO_CONTACTO = "telefono";
    private static final String DOMICILIO_CONTACTO = "domicilio";
    private static final String CORREO_ELECTRONICO = "correo_electronico";
    private static final String EDAD = "edad";

    public CapaBaseDatos(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLA_CONTACTOS + "("
                + ID_CONTACTO + " INTEGER PRIMARY KEY," + NOMBRE_CONTACTO + " TEXT,"
                + APELLIDO_CONTACTO + " TEXT," + TELEFONO_CONTACTO + " TEXT,"
                + DOMICILIO_CONTACTO + " TEXT," + CORREO_ELECTRONICO + " TEXT,"
                + EDAD + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CONTACTOS);

        // Crear las tablas otra vez
        onCreate(db);
    }

    // Para agregar un nuevo registro
    public void addContacto(Contactos contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE_CONTACTO, contact.getNombre());
        values.put(APELLIDO_CONTACTO, contact.getApellido());
        values.put(TELEFONO_CONTACTO, contact.getTelefono());
        values.put(DOMICILIO_CONTACTO, contact.getDomicilio());
        values.put(CORREO_ELECTRONICO, contact.getCorreoElectronico());
        values.put(EDAD, contact.getEdad());

        long result = db.insert(TABLA_CONTACTOS, null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(context, "Subido con éxito", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Error al subir", Toast.LENGTH_SHORT).show();
        }
    }

    // Obtener un solo contacto
    public Contactos getContacto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] campos = new String[]{ID_CONTACTO, NOMBRE_CONTACTO, APELLIDO_CONTACTO,
                TELEFONO_CONTACTO, DOMICILIO_CONTACTO, CORREO_ELECTRONICO, EDAD};
        String[] args = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(TABLA_CONTACTOS, campos, ID_CONTACTO + "=?", args,
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contactos contact = new Contactos(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));

        return contact;
    }

    // Obtener todos los contactos
    public List<Contactos> getContactos() {
        List<Contactos> contactList = new ArrayList<>();

        String[] campos = new String[]{
                ID_CONTACTO, NOMBRE_CONTACTO, APELLIDO_CONTACTO,
                TELEFONO_CONTACTO, DOMICILIO_CONTACTO, CORREO_ELECTRONICO, EDAD
        };
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLA_CONTACTOS, campos, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Contactos contact = new Contactos(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return contactList;
    }
Cursor readAllData(){
        String  query="SELECT * FROM " + TABLA_CONTACTOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;

}
    // Actualizar Contacto
    public int updateContacto(Contactos contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE_CONTACTO, contact.getNombre());
        values.put(APELLIDO_CONTACTO, contact.getApellido());
        values.put(TELEFONO_CONTACTO, contact.getTelefono());
        values.put(DOMICILIO_CONTACTO, contact.getDomicilio());
        values.put(CORREO_ELECTRONICO, contact.getCorreoElectronico());
        values.put(EDAD, contact.getEdad());

        String[] idContacto = new String[]{String.valueOf(contact.getID())};

        int rowsAffected = db.update(TABLA_CONTACTOS, values, ID_CONTACTO + " = ?", idContacto);

        db.close();

        return rowsAffected;
    }

    // Eliminar Contacto
    public void deleteContacto(int contactId ) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLA_CONTACTOS, ID_CONTACTO + " = ?", new String[]{String.valueOf(contactId)});
        if (result == -1) {
            Toast.makeText(context, "Error al intentar eliminar.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Eliminado satisfactoriamente.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Total de Contactos
    public int getTotalContactos() {
        String countQuery = "SELECT  * FROM " + TABLA_CONTACTOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }
    public boolean existeContacto(String nombre, String apellido) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] campos = new String[]{ID_CONTACTO};
        String[] args = new String[]{nombre, apellido};

        String query = "SELECT " + ID_CONTACTO + " FROM " + TABLA_CONTACTOS +
                " WHERE " + NOMBRE_CONTACTO + " = ? AND " + APELLIDO_CONTACTO + " = ?";

        Cursor cursor = db.rawQuery(query, args);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();

        return existe;
    }
}