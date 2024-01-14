package com.ejer_poo.biblioteca;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private Libro libroPrestado;

    public Cliente(){};

    public Cliente(String nombre, String apellido1, String apellido2, String email) {
        setId();
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
    }
    public void setId() {
        this.id = Utilidades.generarCodigoLibro();
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre.toUpperCase();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toLowerCase();
    }

    public String getApellido1() {
        return apellido1.toUpperCase();
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1.toLowerCase();
    }

    public String getApellido2() {
        return apellido2.toUpperCase();
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2.toLowerCase();
    }

    public String getEmail() {
        return email.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public Libro getLibroPrestado() {
        return libroPrestado;
    }

    public void setLibroPrestado(Libro libroPrestado) {
        this.libroPrestado = libroPrestado;
    }
}
