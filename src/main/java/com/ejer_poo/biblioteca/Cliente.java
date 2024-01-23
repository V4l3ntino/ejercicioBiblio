package com.ejer_poo.biblioteca;

import java.util.ArrayList;

import com.google.gson.JsonElement;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    ArrayList<Libro> listaLibrosPrestados = new ArrayList<>();

    public Cliente(){};

    public Cliente(String nombre, String apellido1, String apellido2, String email) {
        this.setId();
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
    }
    public void setId() {
        this.id = Utilidades.generarCodigoLibro();
    }
    public void setIdManual(int id){
        this.id =  id;
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
        if (libroPrestado != null) {
            listaLibrosPrestados.add(libroPrestado);
        }
    }
    public void setDevolverLibro(Libro libro){
        for (int i = 0; i < listaLibrosPrestados.size(); i++) {
            if (listaLibrosPrestados.get(i).getCodigo() == libro.getCodigo()) {
                listaLibrosPrestados.remove(i);
            }
        }
    }
    public ArrayList booksId(){
        ArrayList <Integer> libros = new ArrayList<>();
        for (Libro libro : listaLibrosPrestados) {
            libros.add(libro.getCodigo());
        }
        return libros;
    }
    public ArrayList books(){
        ArrayList <String> libros = new ArrayList<>();
        for (Libro libro : listaLibrosPrestados) {
            libros.add(libro.getTitulo());
        }
        return libros;
    }
    @Override
    public String toString() {
        return "Id: %d = Nombre: %s".formatted(id,nombre)+" = Libros-prestados: "+ books();
    }

}
