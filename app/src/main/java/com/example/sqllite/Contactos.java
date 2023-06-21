package com.example.sqllite;

import java.io.Serializable;

/**
 * Created by Carlos Carlos Ruano.
 */

public class Contactos implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nombre;
    private String apellido;
    private String telefono;
    private String domicilio;
    private String correoElectronico;
    private String edad;

    public Contactos(String nombre, String apellido, String telefono, String edad, String domicilio, String correoElectronico) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.edad = edad;
        this.domicilio = domicilio;
        this.correoElectronico = correoElectronico;
    }

    public Contactos(String nombre, String telefono, String edad) {
        super();
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
    }

    public Contactos(int id, String nombre, String telefono, String edad) {
        super();
        this.ID = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
    }

    public Contactos(int id, String nombre, String apellido, String telefono, String domicilio, String correoElectronico, String edad) {
        super();
        this.ID = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.correoElectronico = correoElectronico;
        this.edad = edad;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}