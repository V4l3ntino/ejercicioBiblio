package com.ejer_poo.biblioteca;

import java.util.ArrayList;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private Libro libroPrestado;
    ArrayList<Libro> listaLibrosPrestados = new ArrayList<>();

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
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Libro> getLibroPrestado() {
        return this.listaLibrosPrestados;
    }

    public void setLibroPrestado(Libro libroPrestado) {
        listaLibrosPrestados.add(libroPrestado);
    }
    public void listarLibros(){
        for (Libro libro : listaLibrosPrestados) {
            System.out.print("Libors: %s, ".formatted(libro.getTitulo()));
        }
    }
}
