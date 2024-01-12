package com.ejer_poo.biblioteca;

import java.util.ArrayList;

public class Autor {
    private int id;
    private String nombre1;
    private String apellido1;
    private String apellido2;
    private String email;
    ArrayList<Libro> libros = new ArrayList<>(); // 1 autor -> N libros

    public Autor(String nombre, String ape1, String ape2, String email) {
        this.setId();
        this.setNombre1(nombre);
        this.setApellido1(ape1);
        this.setApellido2(ape2);
        this.setEmail(email);
    }


    public int getId() {
        return id;
    }

    public String getFullName() {
        return this.apellido1 + " " + this.apellido2 + ", " + this.nombre1;
    }
    
    public String getNombre1() {
        return nombre1;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }
    
    public String getEmail() {
        return email;
    }

    private void setId() {
        this.id = (int) Math.random() * 1000;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLibros(Libro libro) {
        this.libros.add(libro);
    }

    public ArrayList<Libro> getLibros() {
        return this.libros;
    }
    
    public String getNombreCita() {
        return "";
    }
}
