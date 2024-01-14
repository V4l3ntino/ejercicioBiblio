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
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    Cliente cliente = new Cliente();
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

    public void prestarLibro(Libro libro) {
        if (libro.getPrestado().equals(false)) {
            libro.setPrestado(true);
        }
    } 

    public void devolverLibro(Libro libro) {
        if (libro.getPrestado().equals(true)) {
            libro.setPrestado(false);
        }
        
    }
    public boolean validarLibro (String tituloLibro){
        boolean aux = false;
        for (Libro books : listaLibros) {
            if (books.getTitulo().equals(tituloLibro)) {
                aux = true;
            }
        }
        return aux;
    }
    public boolean validarCliente (String nombreCliente){
        boolean aux = false;
        for (Cliente cliente : listaClientes){
            if (cliente.getNombre().equals(nombreCliente)){
                aux = true;
            }
        }
        return aux;
    }
    public void listarLibros() {
        System.out.println("\t LISTADO DE LIBROS");
        System.out.println("========================================");
        for (Libro libro : this.listaLibros) {
            System.out.println("LL%d -     %s (%d) - %s".formatted( libro.getCodigo(),libro.getTitulo(),libro.getAño(),libro.getAutor().getApellido2()));
        }
        System.out.println("=========================================");
    }

    public void listarAutores() {
        System.out.println();
        System.out.println("\t LISTADO DE AUTORES");
        System.out.println("========================================");
        for (Autor autor : this.listaAutores) {
            System.out.println("%d -  %s, %s ".formatted(autor.getId(),autor.getFullName(),autor.getEmail()) );
        }
        System.out.println("========================================");
        System.out.println();
    }

    public void listarClientes(){
        System.out.println();
        System.out.println("\t LISTADO DE CLIENTES");
        System.out.println("========================================");
        System.out.println("    ID - NOMBRE");
        for (Cliente cliente : listaClientes) {
            System.out.println("[*] %d - %s ".formatted(cliente.getId(), cliente.getNombre()));
        }
        System.out.println("========================================");
    }

    public void inicializar() {
        var autor = new Autor("John", "Ronald", "Tolkien", "tolkien@mail.com");
        var autor2 = new Autor("Juan", "Gomez", "Jurado", "jurado@mail.com");
        var libro = new Libro("El señor de los anillos", autor, 1957);
        var libro2 = new Libro("Reina Roja", autor2, 2018);
        var cliente = new Cliente("defaultUser",null,null,null);
        listaAutores.add(autor);
        listaAutores.add(autor2);
        listaLibros.add(libro);
        listaLibros.add(libro2);
        listaClientes.add(cliente);
    }

    public void crearAutor() {
        System.out.println("\t Introduce los atributos del autor.");
        
        System.out.print("[+] Nombre: ");
        String nombre = System.console().readLine();

        System.out.print("[+] Primer apellido: ");
        String apellido1 = System.console().readLine();

        System.out.print("[+] Segundo apellido: ");
        String apellido2 = System.console().readLine();

        System.out.print("[+] Email: ");
        String email = System.console().readLine();
        
        var autor = new Autor(nombre, apellido1, apellido2, email);

        this.addAutor(autor);
    }
    public void crearCliente(){
        System.out.println();
        System.out.println("\t REGISTRAR CLIENTE");
        System.out.print("[+] Nombre: ");
        String nombreCliente = System.console().readLine();
        cliente = new Cliente(nombreCliente,null,null,null);
        listaClientes.add(cliente);
    }

    public void crearLibro() {
        System.out.println("\t Introduce los atributos del libro.");
        
        System.out.print("[+] Titulo: ");
        String newTitulo = System.console().readLine();

        System.out.print("[+] Autor: ");
        String newNombre = System.console().readLine();
        Autor newAutor = null;
        for (Autor autor : listaAutores) {
            String nombreAutor = autor.getNombre1();
            if (nombreAutor.equals(newNombre)) {
                newAutor = autor;
            }
        }
        if (newAutor == null) {
            System.out.println();
            System.out.println("***************************");
            System.out.println("* Autor introducido no existe *");
            System.out.println("***************************");
            System.out.println();
            this.menu();
        }

        boolean validarEntero = false;
        Integer año = 0;
        while (!validarEntero) {
            System.out.print("[+] Año: ");
            String input = System.console().readLine();

            
            try {
                año = Integer.parseInt(input);
                validarEntero = true;
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("[!] Error: Por favor, introduzca un número válido para el año.");
                System.out.println();
            }
        }


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
                3. Registrar cliente
                4. Listar libro
                5. Listar autores
                6. Listar clientes
                7. Prestar libro
                8. Devolver libro
                9. Salir
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

        switch(opcion) {
            case "1" -> {
                this.crearAutor();
            }
            case "2" -> this.crearLibro();
            case "3" -> this.crearCliente();
            case "4" -> this.listarLibros();
            case "5" -> this.listarAutores();
            case "6" -> this.listarClientes();
            
            case "7" -> {
                this.listarClientes();
                System.out.println();
                System.out.print("[+] Introduzca el nombre del cliente : ");
                String nombreCliente = System.console().readLine();
                //VALIDAR CLIENTE
                boolean aux = validarCliente(nombreCliente);
                if (aux == true){
                        //LIBRO
                    System.out.print("[+] Introduzca el título del libro: ");
                    String nombreLibro = System.console().readLine();
                    //validarLibro
                    boolean auxiliar = validarLibro(nombreLibro);
                    if (auxiliar == true) {
                        for (Libro libro : listaLibros) {
                            if (libro.getTitulo().equals(nombreLibro)) {
                                if (libro.getPrestado() == true) {
                                    System.out.println("*************************************************************************************");
                                    System.out.println("* NO ES POSIBLE PRESTAR EL LIBRO \"%s\" PORQUE ESTA SIENDO PRESTADO AL CLIENTE: %s".formatted(libro.getTitulo(),libro.getPrestador().getNombre()));
                                    System.out.println("*************************************************************************************");
                                    System.out.println();
                                    // System.out.println("El libro ya está prestado");
                                }else{
                                    cliente.setLibroPrestado(libro);
                                    libro.setPrestador(cliente);
                                    this.prestarLibro(libro);
                                    System.out.println();
                                    System.out.println("--------------------------------------------------------------------");
                                    System.out.println("| EL LIBRO SE HA PRESTADO CORRECTAMENTE AL CLIENTE "+ libro.getPrestador().getNombre()+" |");
                                    System.out.println("--------------------------------------------------------------------");
                                    System.out.println();
                                }
                                break;
                            }
                        }
                    }else{
                        System.out.println();
                        System.out.println("[-] El libro no existe");
                        System.out.println();
                    }
                }else{
                    System.out.println();
                    System.out.println(" *******************************");
                    System.out.println("* EL CLIENTE NO ESTA REGISTRADO *");
                    System.out.println(" *******************************");
                    System.out.println();
                }

            }
            
            case "8" -> {
                System.out.print("[*] Introduzca el título: ");
                String nombreLibro = System.console().readLine();
                //validarLibro
                boolean auxiliar = validarLibro(nombreLibro);
                if (auxiliar == true) {
                    for (Libro libro : listaLibros) {
                        if (libro.getTitulo().equals(nombreLibro)) {
                            if (libro.getPrestado() == true) {
                                this.devolverLibro(libro);
                                System.out.println();
                                System.out.println("[+] El libro se ha devuelto correctamente");
                            }else{
                                System.out.println();
                                System.out.println("[-] El libro no ha sido prestado aún");
                                System.out.println();
                            }
                            break;
                        }
                    }
                }else{
                    System.out.println();
                    System.out.println("[-] El libro no existe");
                    System.out.println();
                }
            }
            case "9" -> System.exit(0);
            
        }
        //this.crearJSON();
        this.menu();
    }
}