package com.ejer_poo.biblioteca;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class BibliotecaController {
    private String nombre;
    private ArrayList<Libro> listaLibros = new ArrayList<>();
    private ArrayList<Autor> listaAutores = new ArrayList<>();
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    Cliente cliente = new Cliente();
    Libro libroG = new Libro();
    Scanner sc = new Scanner(System.in);
    public BibliotecaController(){}

    public BibliotecaController(String nombre) {
        this.leerJsonAutor().stream().forEach((i) -> this.listaAutores.add(i));
        this.leerJsonLibro().stream().forEach((i) -> this.listaLibros.add(i));
        this.leerJsonCliente().stream().forEach((i) -> this.listaClientes.add(i));
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
            cliente.setLibroPrestado(libro);
            libro.setPrestador(cliente);
        }
    } 

    public void devolverLibro(Libro libro) {
        if (libro.getPrestado().equals(true)) {
            libro.setPrestado(false);
            libro.setPrestador(null);
        }
        
    }
    public boolean validarLibro (String tituloLibro){
        boolean aux = false;
        for (Libro books : listaLibros) {
            if (books.getTitulo().equals(tituloLibro)) {
                aux = true;
                libroG = books;
            }
        }
        return aux;
    }
    public boolean validarCliente (String nombreCliente){
        boolean aux = false;
        for (Cliente cliente : listaClientes){
            if (cliente.getNombre().equals(nombreCliente)){
                aux = true;
                this.cliente = cliente;
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
        var libro = new Libro("El señor de los anillos", autor, 1957,false,null);
        var libro2 = new Libro("Reina Roja", autor2, 2018,false,null);
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
        System.out.print("[+] Apellido: ");
        String ape1 = System.console().readLine();
        System.out.print("[+] Apellido2 :");
        String ape2 = System.console().readLine();
        System.out.print("[+] Email: ");
        String email = System.console().readLine();
        
        boolean validarEntero = false;
        boolean option = false;
        int opcion = 5;
        while (!validarEntero) {
            System.out.println("[+] Prestar libro?(1 = si,0 = no): ");
                String input2 = System.console().readLine();
            try {
                opcion = Integer.parseInt(input2);
                switch (opcion) {
                    case 1:
                        option = true;
                        validarEntero = true;
                        break;
                    case 0:
                        option = false;
                        validarEntero = true;
                        break;
                    default:
                        System.out.println("Elige 1 o 0;");
                        validarEntero = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("1=SI, 0=NO");
            }
        }
        //PRESTAR LIBRO
        if (option == true) {
            boolean comprobar = false;
            while (!comprobar) {
                System.out.print("Introduce el nombre del libro: ");
                String nombreLibro = System.console().readLine();
                boolean aux = validarLibro(nombreLibro);
                if (aux == true) {
                    cliente = new Cliente(nombreCliente,ape1,ape2,email);
                    listaClientes.add(cliente);
                    libroG.setPrestado(true);
                    libroG.setPrestador(cliente);
                    System.out.println("EL LIBRO %s SE HA PRESTADO CORRECTAMENTE AL CLIENTE %s".formatted(libroG.getTitulo().toUpperCase(),libroG.getPrestador().getNombre().toUpperCase()));
                    comprobar = true;
    
                }else{
                    System.out.println("El libro no existe");
                    this.listarLibros();
                }
            }
        } else {
            cliente = new Cliente(nombreCliente,ape1,ape2,email);
            listaClientes.add(cliente);
            
        }
    }

    public void crearLibro() {
        System.out.println("\t Introduce los atributos del libro.");
        
        System.out.print("[+] Titulo: ");
        String newTitulo = System.console().readLine();
        this.listarAutores();
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
        boolean option = false;
        int opcion = 5;
        while (!validarEntero) {
            System.out.print("[+] Año: ");
            String input = System.console().readLine();
            try {
                año = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("[!] Error: Por favor, introduzca un número válido.");
                System.out.println();
            }
            boolean validarEnteroEntero = false;
            while (!validarEnteroEntero) {
                System.out.println("[+] Prestar libro?(1 = si,0 = no): ");
                String input2 = System.console().readLine();
            try {
                opcion = Integer.parseInt(input2);
                switch (opcion) {
                    case 1:
                        option = true;
                        validarEnteroEntero = true;
                        validarEntero = true;
                        break;
                    case 0:
                        option = false;
                        validarEnteroEntero = true;
                        validarEntero = true;
                        break;
                    default:
                        System.out.println("Elige 1 o 0;");
                        validarEnteroEntero = false;
                        validarEntero = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("SOLO PUEDES ELEGIR 1/0");
            }
            }
        }
        //PRESTAR LIBRO
        if (option == true) {
            boolean comprobar = false;
            while (!comprobar) {
                System.out.print("Introduce el nombre del cliente que va a ser prestado: ");
                String nombreCliente = System.console().readLine();
                boolean aux = validarCliente(nombreCliente);
                if (aux == true) {
                    var libro = new Libro(newTitulo, newAutor, año,option,cliente); // 1 libro -> 1 autor
                    //cliente.setLibroPrestado(libro);
                    //newAutor.setLibros(libro); // 1 autor -> N libros
                    this.addLibro(libro);
                    System.out.println("EL LIBRO \"%s\" SE HA PRESTADO CORRECTAMENTE AL CLIENTE \"%s\"".formatted(libro.getTitulo().toUpperCase(),libro.getPrestador().getNombre().toUpperCase()));
                    comprobar = true;

                }else{
                    System.out.println("El cliente no existe");
                    this.listarClientes();
                }
            }
        } else {
            var libro = new Libro(newTitulo, newAutor, año,option,null); // 1 libro -> 1 autor
            //newAutor.setLibros(libro); // 1 autor -> N libros
            this.addLibro(libro);
        }
    }
    public ArrayList<Autor> leerJsonAutor(){
        Gson gson = new Gson();
        String rutaFile = "src/main/java/com/ejer_poo/biblioteca/json/autores.json";
        String json = "";
        try {
            json = Files.readString(Paths.get(rutaFile));
        } catch (Exception e) {
            System.out.println(e);
        }
        Type autorType = new TypeToken<ArrayList<Autor>>() {}.getType();
        ArrayList<Autor> lista = gson.fromJson(json, autorType);
        return lista;
    }
    public ArrayList<Libro> leerJsonLibro(){
        Gson gson = new Gson();
        String rutaFile = "src/main/java/com/ejer_poo/biblioteca/json/libros.json";
        String json = "";
        try {
            json = Files.readString(Paths.get(rutaFile));
        } catch (Exception e) {
            System.out.println(e);
        }
        Type libroType = new TypeToken<ArrayList<Libro>>() {}.getType();
        ArrayList<Libro> lista = gson.fromJson(json, libroType);
        return lista;
    }
    public ArrayList<Cliente> leerJsonCliente(){
        Gson gson = new Gson();
        String rutaFile = "src/main/java/com/ejer_poo/biblioteca/json/clientes.json";
        String json = "";
        try {
            json = Files.readString(Paths.get(rutaFile));
        } catch (Exception e) {
            System.out.println(e);
        }
        Type clienteType = new TypeToken<ArrayList<Cliente>>() {}.getType();
        ArrayList<Cliente> lista = gson.fromJson(json, clienteType);
        return lista;

    }
    public void crearJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonStringAutores = gson.toJson(listaAutores);
        String jsonStringClientes = gson.toJson(listaClientes);
        String jsonStringLibros = gson.toJson(listaLibros);
        // CREO EL ARCHIVO JSON DE AUTORES
        System.out.println(jsonStringAutores);
        try (PrintWriter autores = new PrintWriter(new File("./src/main/java/com/ejer_poo/biblioteca/json/autores.json"));) {
            autores.write(jsonStringAutores);
            
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        //CREO JSON DE CLIENTES
        try (PrintWriter clientes = new PrintWriter(new File("./src/main/java/com/ejer_poo/biblioteca/json/clientes.json"));){
            clientes.write(jsonStringClientes);
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        //CREO JSON DE LIBROS
        try (PrintWriter libros = new PrintWriter(new File("./src/main/java/com/ejer_poo/biblioteca/json/libros.json"));){
            libros.write(jsonStringLibros);
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }
    public void caso7(){
        this.listarClientes();
        System.out.println();
        System.out.print("[+] Introduzca el nombre del cliente : ");
        String nombreCliente = System.console().readLine();
        //VALIDAR CLIENTE
        boolean aux = validarCliente(nombreCliente);
        if (aux == true){
                //LIBRO
            this.listarLibros();
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
                            this.prestarLibro(libro);
                            System.out.println();
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println("| EL LIBRO SE HA PRESTADO CORRECTAMENTE AL CLIENTE \""+ libro.getPrestador().getNombre().toUpperCase()+"\" |");
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
    public void caso8(){
        this.listarLibros();
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
    public void consultar(){
        this.listarLibros();
        System.out.println("Introduce el nombre del libro");
        String nombreLibro = System.console().readLine();
        boolean auxiliar = false;
        auxiliar = validarLibro(nombreLibro);
        if (auxiliar == true) {
            if (libroG.getPrestador().getNombre() == null) {
                System.out.println("El libro no ha sido prestado a nadie");
            } else {
                System.out.println("El libro esta siendo prestado al cliente \"%s\"".formatted(libroG.getPrestador().getNombre().toUpperCase()));
            }
        } else {
            System.out.println("El libro no existe");
        }
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
                9. Consultar libro prestado
                10. Exit
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
            case "7" -> this.caso7();
            case "8" -> this.caso8();
            case "9" -> this.consultar();
            case "10" -> {
                this.crearJson();
                System.exit(0);}
            
        }
        this.menu();
    }
}