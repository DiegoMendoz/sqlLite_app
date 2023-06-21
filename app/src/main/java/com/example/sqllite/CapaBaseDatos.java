package com.example.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Ruano.
 */
public class CapaBaseDatos extends SQLiteOpenHelper {

    private static final int VERSION_BASEDATOS = 4;

    // Nombre de la BD
    private static final String NOMBRE_BASEDATOS = "contactosDB";

    // Nombre de la Tabla
    private static final String TABLA_CONTACTOS = "contactos";

    // Nombres de las Columnas
    private static final String ID_CONTACTO = "id";
    private static final String NOMBRE_CONTACTO = "nombre";
    private static final String TELEFONO_CONTACTO = "telefono";
    private static final String EDAD = "edad";

    public CapaBaseDatos(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLA_CONTACTOS + "("
                + ID_CONTACTO + " INTEGER PRIMARY KEY," + NOMBRE_CONTACTO + " TEXT,"
                + TELEFONO_CONTACTO + " TEXT" + EDAD+ "INTEGER"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CONTACTOS);

        // Crear las tablas otra vez
        onCreate(db);

    }

    // Para agregar un nuevo registro
    void addContacto(Contactos contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE_CONTACTO, contact.getNombre());
        values.put(TELEFONO_CONTACTO, contact.getTelefono());
        db.insert(TABLA_CONTACTOS, null, values);
        db.close();
    }

    // Obtener un solo contacto
    public Contactos getContacto(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] campos = new String[] {ID_CONTACTO,NOMBRE_CONTACTO, TELEFONO_CONTACTO};
        String[] args =  new String[] { String.valueOf(id)};

        Cursor cursor = db.query(TABLA_CONTACTOS, campos, ID_CONTACTO + "=?",args
               , null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contactos contact = new Contactos(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return contact;
    }

    // todos los contactos
    public List<Contactos> getContactos() {

        List<Contactos> contactList = new ArrayList<Contactos>();

        String[] campos = new String[] {ID_CONTACTO,NOMBRE_CONTACTO, TELEFONO_CONTACTO};
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLA_CONTACTOS, campos, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Contactos contact = new Contactos();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setNombre(cursor.getString(1));
                contact.setTelefono(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    // Actualizar Contacto
    public int updateContacto(Contactos contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] idContacto = new String[] { String.valueOf(contact.getID()) };

        ContentValues values = new ContentValues();
        values.put(NOMBRE_CONTACTO, contact.getNombre());
        values.put(TELEFONO_CONTACTO, contact.getTelefono());


        return db.update(TABLA_CONTACTOS, values, ID_CONTACTO + " = ?",idContacto );
    }

    // Eliminar Contacto
    public void deleteContacto(Contactos contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_CONTACTOS, ID_CONTACTO + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Total de Contactos
    public int getTotalContactos() {
        String countQuery = "SELECT  * FROM " + TABLA_CONTACTOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();


        return cursor.getCount();
    }
}
