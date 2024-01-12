package com.ejer_poo.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BibliotecaController {
    private String nombre;
    private ArrayList<Libro> listaLibros = new ArrayList<>();
    private ArrayList<Autor> listaAutores = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public BibliotecaController(String nombre) {
        this.nombre = nombre;
    }

    public void addLibro(Libro libro) {
        this.listaLibros.add(libro);
    }

    public void addAutor(Autor autor) {
        this.listaAutores.add(autor);
    }

    public Boolean prestarLibro(String libro) {
        //TODO
        
        return false;
    }

    public Boolean devolverLibro(String libro) {
        //TODO
        
        return false;
    }

    public void listarLibros() {
        System.out.println("Lista de libros:");
        for (Libro libro : this.listaLibros) {
            System.out.println(libro.getTitulo());
        }
    }

    public void listarAutores() {
        System.out.println("Lista de autores:");
        for (Autor autor : this.listaAutores) {
            System.out.println(autor.getFullName());
        }
    }

    public void inicializar() {
        
        
    }

    public void crearAutor() {
        System.out.println("Introduce los atributos del autor.");
        
        System.out.print("Nombre: ");
        String nombre = System.console().readLine();

        System.out.print("Primer apellido: ");
        String apellido1 = System.console().readLine();

        System.out.print("Segundo apellido: ");
        String apellido2 = System.console().readLine();

        System.out.print("Email: ");
        String email = System.console().readLine();
        
        var autor = new Autor(nombre, apellido1, apellido2, email);

        this.addAutor(autor);
    }

    public void crearLibro() {
        System.out.println("Introduce los atributos del libro.");
        
        System.out.print("Titulo: ");
        String newTitulo = System.console().readLine();

        System.out.print("Autor: ");
        String newNombre = System.console().readLine();
        Autor newAutor = null;
        for (Autor autor : listaAutores) {
            String nombreAutor = autor.getNombre1();
            if (nombreAutor.equals(newNombre)) {
                newAutor = autor;
            }
        }
        if (newAutor == null) {
            System.out.println("Autor introducido no existe");
            this.menu();
        }

        System.out.print("Año: ");
        int año = Integer.parseInt(System.console().readLine());        

        var libro = new Libro(newTitulo, newAutor, año); // 1 libro -> 1 autor
        newAutor.setLibros(libro); // 1 autor -> N libros

        this.addLibro(libro);
    }
    
    public void menu() {
        System.out.println("""
                Menu Biblioteca
                *****************
                1. Añadir autores
                2. Añadir libro
                3. Listar libro
                4. Listar autores
                5. Prestar libro
                6. Devolver libro
                7. Salir
                """);
        
        System.out.print("Elige una opción: ");
        
        String opcion = System.console().readLine();
  /*      
        var reader = new InputStreamReader(System.in);
        var opcion = reader.read();
        var a = new Scanner(System.in);
        var b = new Scanner(System.in);
        String opcionA = a.nextLine();
*/
        System.out.println("");
        

        switch(opcion) {
            case "1" -> {
                this.crearAutor();
            }
            case "2" -> this.crearLibro();
            case "3" -> this.listarLibros();
            case "4" -> this.listarAutores();
            /*
            case "5" -> this.prestarLibro();
            case "6" -> this.devolverLibro();
            case "7" -> ;
            */
        }
        //this.crearJSON();
        this.menu();
    }
}