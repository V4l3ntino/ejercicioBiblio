package com.ejer_poo.biblioteca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class BibliotecaController {
    private String nombre;
    private  ArrayList<Libro> listaLibros = new ArrayList<>();
    private ArrayList<Autor> listaAutores = new ArrayList<>();
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    Cliente clienteG = new Cliente();
    Libro libroG = new Libro();
    Autor autorG = new Autor();
    Scanner sc = new Scanner(System.in);
    public BibliotecaController(){}

    public BibliotecaController(String nombre) {
        this.leerJsonAutor().stream().forEach((i) -> this.listaAutores.add(i));
        this.leerJsonCliente().stream().forEach((i) -> this.listaClientes.add(i));
        this.leerJsonLibro().stream().forEach((i) -> this.listaLibros.add(i));
        this.leerJsonDependencias();
        this.nombre = nombre;
    }


    public void addLibro(Libro libro) {
        this.listaLibros.add(libro);
    }

    public void addAutor(Autor autor) {
        this.listaAutores.add(autor);
    }
    public void setBorrarAutor(int id){
        ArrayList<Libro> listaLibrosAutor = new ArrayList<>();
        ArrayList<Libro> listaLibrosPrestador = new ArrayList<>();
        for (int i = 0; i < listaAutores.size(); i++) {
            //VARIABLE I ES EL AUTOR
            if (listaAutores.get(i).getId() == id) {
                listaLibrosAutor = listaAutores.get(i).getListaLibros();
                //Este FOR busca todos los libros del autor
                for (Libro libro : listaLibrosAutor) {
                    int idCodigo = libro.getCodigo();
                    //Este FOR busca y elimina los libros del autor del la lista de libros general
                    for (int j = 0; j < listaLibros.size(); j++) {
                        //VARIABLE J SON LOS LIBROS GENERALES
                        if (listaLibros.get(j).getCodigo() == idCodigo) {
                            listaLibrosPrestador = listaLibros.get(j).getPrestador().getLibroPrestado();
                            //Este FOR busca y elimina aquellos libros que van ha ser eliminados de la lista libros asociados a ese cliente
                            for (int k = 0; k < listaLibrosPrestador.size(); k++) {
                                //VARIABLE K SON LOS LIBROS ASOCIADOS A UN CLIENTE
                                if (listaLibrosPrestador.get(k).getCodigo() == idCodigo) {
                                    listaLibrosPrestador.remove(k);
                                }
                            }
                            listaLibros.remove(j);
                        }
                    }
                }
                listaAutores.remove(i);
            }
        }
    }
    public void setBorrarLibro(int codigo){
        ArrayList<Libro> listaLibroPrestador = new ArrayList<>();
        ArrayList<Libro> listaLibroAutor = new ArrayList<>();
        //Este FOR busca y elimina el libro de la lista general de libros
        for (int i = 0; i < listaLibros.size(); i++) {
            if (listaLibros.get(i).getCodigo() == codigo) {
                listaLibroPrestador = listaLibros.get(i).getPrestador().getLibroPrestado();
                listaLibroAutor = listaLibros.get(i).getAutor().getListaLibros();
                //Borrar los libros asociados al cliente prestador
                for (int j = 0; j < listaLibroPrestador.size(); j++) {
                    if (listaLibroPrestador.get(j).getCodigo() == codigo) {
                        listaLibroPrestador.remove(j);
                    }
                }
                //Borrar los libros asociados al autor
                for (int j2 = 0; j2 < listaLibroAutor.size(); j2++) {
                    if (listaLibroAutor.get(j2).getCodigo() == codigo) {
                        listaLibroAutor.remove(j2);
                    }
                }
                listaLibros.remove(i);
            }
        }
    }

    public void prestarLibro(Libro libro) {
        if (libro.getPrestado().equals(false)) {
            libro.setPrestado(true);
            libro.setPrestador(clienteG);
            clienteG.setLibroPrestado(libro);
        }
    } 

    public void devolverLibro(Libro libro) {
        if (libro.getPrestado().equals(true)) {
            libro.setPrestado(false);
            libro.setQuitarPrestador();
            clienteG.setDevolverLibro(libro);
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
                this.clienteG = cliente;
            }
        }
        return aux;
    }
    public boolean validarAutor (String nombreAutor){
        boolean aux = false;
        for (Autor autor : listaAutores){
            if (autor.getNombre1().equals(nombreAutor)){
                aux = true;
                this.autorG = autor;
            }
        }
        return aux;
    }
    public void listarLibros() {
        System.out.println("\t LISTADO DE LIBROS");
        System.out.println("========================================");
        for (Libro libro : this.listaLibros) {
            System.out.println(libro.toString());
        }
        System.out.println("=========================================");
    }

    public void listarAutores() {
        System.out.println();
        System.out.println("\t LISTADO DE AUTORES");
        System.out.println("========================================");
        for (Autor autor : this.listaAutores) {
            System.out.println(autor.toString());
        }
        System.out.println("========================================");
        System.out.println();
    }

    public void listarClientes(){
        System.out.println();
        System.out.println("\t LISTADO DE CLIENTES");
        System.out.println("========================================");
        for (Cliente cliente : listaClientes) {
            System.out.println(cliente.toString());
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
                    clienteG = new Cliente(nombreCliente,ape1,ape2,email);
                    listaClientes.add(clienteG);
                    this.prestarLibro(libroG);
                    System.out.println("EL LIBRO %s SE HA PRESTADO CORRECTAMENTE AL CLIENTE %s".formatted(libroG.getTitulo().toUpperCase(),libroG.getPrestador().getNombre().toUpperCase()));
                    comprobar = true;
    
                }else{
                    System.out.println("El libro no existe");
                    this.listarLibros();
                }
            }
        } else {
            clienteG = new Cliente(nombreCliente,ape1,ape2,email);
            listaClientes.add(clienteG);
            
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
                    var libro = new Libro(newTitulo, newAutor, año,option,clienteG); // 1 libro -> 1 autor
                    newAutor.setLibros(libro);
                    clienteG.setLibroPrestado(libro);
                    this.addLibro(libro);
                    System.out.println("EL LIBRO \"%s\" SE HA PRESTADO CORRECTAMENTE AL CLIENTE \"%s\"".formatted(libro.getTitulo().toUpperCase(),libro.getPrestador().getNombre().toUpperCase()));
                    comprobar = true;

                }else{
                    System.out.println("El cliente no existe");
                    this.listarClientes();
                }
            }
        } else {
            clienteG = new Cliente();
            var libro = new Libro(newTitulo, newAutor, año,option,clienteG); 
            newAutor.setLibros(libro);
            this.addLibro(libro);
        }
    }

    //LECTURA DE DATOS
    //------------------------------------------------------------------------------------------------------------//
    public ArrayList<Autor> leerJsonAutor(){
        File input = new File("./src/main/java/com/ejer_poo/biblioteca/json/autores.json");
        ArrayList<Autor> listaDeAutores = new ArrayList<>(); 
        try {
            JsonElement elemento = JsonParser.parseReader(new FileReader(input));
            JsonArray listaObjetos = elemento.getAsJsonArray();
            for (JsonElement element : listaObjetos) {
                JsonObject objeto = element.getAsJsonObject();
                int id = objeto.get("id").getAsInt();
                String name = objeto.get("nombre1").getAsString();
                String surname1 = objeto.get("apellido1").getAsString();
                String surname2 = objeto.get("apellido2").getAsString();
                String email = objeto.get("email").getAsString();
                Autor autor = new Autor(name, surname1, surname2, email);
                autor.setIdManual(id);
                listaDeAutores.add(autor);
            }
        } catch (JsonIOException e) {
            System.err.println("HA OCURRIDO UNA EXCEPCION");
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.err.println("ERROR EN LA SINTAXIS DEL ARCHIVO");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("ARCHIVO NO ENCONTRADO");
            e.printStackTrace();
        }
        // Gson gson = new Gson();
        // String rutaFile = "src/main/java/com/ejer_poo/biblioteca/json/autores.json";
        // String json = "";
        // try {
        //     json = Files.readString(Paths.get(rutaFile));
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
        // Type autorType = new TypeToken<ArrayList<Autor>>() {}.getType();
        // ArrayList<Autor> lista = gson.fromJson(json, autorType);
        return listaDeAutores;
    }
    public ArrayList<Libro> leerJsonLibro(){
        File input = new File("./src/main/java/com/ejer_poo/biblioteca/json/libros.json");
        ArrayList<Libro> listaDeLibros = new ArrayList<>(); 
        try {
            JsonElement elemento = JsonParser.parseReader(new FileReader(input));
            JsonArray listaObjetos = elemento.getAsJsonArray();
            for (JsonElement element : listaObjetos) {
                JsonObject objeto = element.getAsJsonObject();
                int codigo = objeto.get("codigo").getAsInt();
                String titulo = objeto.get("titulo").getAsString();
                int idAutor = objeto.get("autor").getAsInt();
                int anio = objeto.get("año").getAsInt();
                boolean prestado = objeto.get("prestado").getAsBoolean();
                //LECTURA DE PRETADOR EN CASO DE QUE EXISTA
                Integer idPrestador = 0;
                if (objeto.has("prestador")) {
                    idPrestador = objeto.get("prestador").getAsInt();
                }
                //BUSQUEDAS DE AUTOR
                Autor elAutor = new Autor();
                for (Autor autor : listaAutores) {
                    if(autor.getId() == idAutor){
                        elAutor = autor;
                        break;
                    }
                }
                
                //BUSQUEDA PRESTADOR
                Cliente elPrestador = new Cliente();
                for (Cliente cliente : listaClientes) {
                    if (cliente.getId() == idPrestador) {
                        elPrestador = cliente;
                    }
                }
                
                Libro libro = new Libro(titulo, elAutor, anio, prestado, elPrestador);
                libro.setCodigManual(codigo);
                listaDeLibros.add(libro);
                elAutor.listaLibros.add(libro);
            }
        } catch (JsonIOException e) {
            System.err.println("HA OCURRIDO UNA EXCEPCION");
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.err.println("ERROR EN LA SINTAXIS DEL ARCHIVO");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("ARCHIVO NO ENCONTRADO");
            e.printStackTrace();
        }
        return listaDeLibros;
    }
    public ArrayList<Cliente> leerJsonCliente(){
        // Gson gson = new Gson();
        // String rutaFile = "src/main/java/com/ejer_poo/biblioteca/json/clientes.json";
        // String json = "";
        // try {
        //     json = Files.readString(Paths.get(rutaFile));
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
        // Type clienteType = new TypeToken<ArrayList<Cliente>>() {}.getType();
        // ArrayList<Cliente> lista = gson.fromJson(json, clienteType);
        // return lista;
        File input = new File("./src/main/java/com/ejer_poo/biblioteca/json/clientes.json");
        ArrayList<Cliente> listaDeClientes = new ArrayList<>();
        try {
            JsonElement elemento = JsonParser.parseReader(new FileReader(input));
            JsonArray listaObjetos = elemento.getAsJsonArray();
            for (JsonElement element : listaObjetos) {
                JsonObject objeto = element.getAsJsonObject();
                int id = objeto.get("id").getAsInt();
                String name = objeto.get("nombre").getAsString();
                String surname1 = objeto.get("apellido1").getAsString();
                String surname2 = objeto.get("apellido2").getAsString();
                String email = objeto.get("email").getAsString();
                clienteG = new Cliente(name, surname1, surname2, email);
                clienteG.setIdManual(id);
                listaDeClientes.add(clienteG);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listaDeClientes;
    }
    public void leerJsonDependencias(){
        //CLIENTES
        File input = new File("./src/main/java/com/ejer_poo/biblioteca/json/clientes.json");
        // File input2 = new File("./src/main/java/com/ejer_poo/biblioteca/json/autores.json");
        try {
            JsonElement elemento = JsonParser.parseReader(new FileReader(input));
            JsonArray listaObjetos = elemento.getAsJsonArray();
            for (JsonElement element : listaObjetos) {
                JsonObject objeto = element.getAsJsonObject();
                int idCliente = objeto.get("id").getAsInt();
                boolean boleano = objeto.get("boolean").getAsBoolean();
                if (boleano != false) {
                    String lista = objeto.get("listaLibrosPrestados").getAsString();
                    String listaRegex = lista.replaceAll("\\[|\\]", "");
                    ArrayList<String> listaLibrosString = new ArrayList<String>(Arrays.asList(listaRegex.split(",")));
                    for (Cliente clientePrestador : listaClientes) {
                        if (clientePrestador.getId() == idCliente) {
                            try {
                                for (int i = 0; i < listaLibrosString.size(); i++) {
                                    Integer idBook = Integer.parseInt(listaLibrosString.get(i));
                                    for (Libro libro : listaLibros) {
                                        if (libro.getCodigo() == idBook) {
                                            clientePrestador.setLibroPrestado(libro);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.err.println("ERROR AL PASAR A ENTERO EL ID DE LA LISTA DE LIBROS-PRESTADOS");
                                e.printStackTrace();
                            }
                        }
                    } 
                }
            }
        } catch (Exception e) {
            System.err.println("ALGO HA SALIDO MAL AL LEER LAS DEPENDENCIAS");
            e.printStackTrace();
        }
        // //AUTORES
        // try {
        //     JsonElement elemento = JsonParser.parseReader(new FileReader(input2));
        //     JsonArray listaObjetos = elemento.getAsJsonArray();
        //     for (JsonElement element : listaObjetos) {
        //         JsonObject objeto = element.getAsJsonObject();
        //         int idAutor = objeto.get("id").getAsInt();
        //         boolean boleano = objeto.get("boolean").getAsBoolean();
        //         if (boleano != false) {
        //             String lista = objeto.get("listaLibros").getAsString();
        //             String listaRegex = lista.replaceAll("\\[|\\]", "");
        //             ArrayList<String> listaLibrosString = new ArrayList<String>(Arrays.asList(listaRegex.split(",")));
        //             for (Autor autorLibro : listaAutores) {
        //                 if (autorLibro.getId() == idAutor) {
        //                     try {
        //                         for (int i = 0; i < listaLibrosString.size(); i++) {
        //                             Integer idBook = Integer.parseInt(listaLibrosString.get(i));
        //                             for (Libro libro : listaLibros) {
        //                                 if (libro.getCodigo() == idBook) {
        //                                     autorLibro.setLibros(libro);
        //                                 }
        //                             }
        //                         }
        //                     } catch (Exception e) {
        //                         System.err.println("ERROR AL LEER DEPENDENCAIS DE AUTORES");
        //                         e.printStackTrace();
        //                     }
        //                 }
        //             } 
        //         }
        //     }
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
    }

    //ESCRITURA DE DATOS
    //-------------------------------------------------------------------------------------------------------------------//
    public void crearJson(){
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            // CREO EL ARCHIVO JSON DE AUTORES
            try (PrintWriter autores = new PrintWriter(new File("./src/main/java/com/ejer_poo/biblioteca/json/autores.json"));){
                JsonArray listaObjetosAutores = new JsonArray();
                for (Autor autor : listaAutores) {
                    int id = autor.getId();
                    String nombre = autor.getNombre1();
                    String apellido1 = autor.getApellido1();
                    String apellido2 = autor.getApellido2();
                    String email = autor.getEmail();
                    JsonObject objeto = new JsonObject();
                    objeto.addProperty("id", id);
                    objeto.addProperty("nombre1", nombre);
                    objeto.addProperty("apellido1", apellido1);
                    objeto.addProperty("apellido2", apellido2);
                    objeto.addProperty("email", email);
                    // if (autor.booksId().size() != 0) {
                    //      objeto.addProperty("boolean", true);
                    //      String books = gson2.toJson(autor.booksId());
                    //      objeto.addProperty("listaLibros", books);
                    // }else{
                    //     objeto.addProperty("boolean", false);
                    // }
                    listaObjetosAutores.add(objeto);
                }
                String jsonStringAutores = gson.toJson(listaObjetosAutores);
                autores.write(jsonStringAutores);
            } catch (Exception e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
            //CREO JSON DE CLIENTES
            try (PrintWriter clientes = new PrintWriter(new File("./src/main/java/com/ejer_poo/biblioteca/json/clientes.json"));){
                JsonArray listaObjetosClientes = new JsonArray();
                Gson gson2 = new Gson();
                for (Cliente cliente : listaClientes) {
                    int id = cliente.getId();
                    String nombre = cliente.getNombre();
                    String apellido1 = cliente.getApellido1();
                    String apellido2 = cliente.getApellido2();
                    String email = cliente.getEmail();
                    JsonObject objeto = new JsonObject();
                    objeto.addProperty("id", id);
                    objeto.addProperty("nombre", nombre);
                    objeto.addProperty("apellido1", apellido1);
                    objeto.addProperty("apellido2", apellido2);
                    objeto.addProperty("email", email);
                    if (cliente.booksId().size() != 0) {
                         objeto.addProperty("boolean", true);
                         String books = gson2.toJson(cliente.booksId());
                         objeto.addProperty("listaLibrosPrestados", books);
                    }else{
                        objeto.addProperty("boolean", false);
                    }
                    listaObjetosClientes.add(objeto);
                }
                String jsonStringClientes = gson.toJson(listaObjetosClientes);
                clientes.write(jsonStringClientes);
            } catch (Exception e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
            //CREO JSON DE LIBROS
            try {
                FileWriter file = new FileWriter("./src/main/java/com/ejer_poo/biblioteca/json/libros.json");
                JsonArray listaObjetos = new JsonArray();
                for (Libro libro : listaLibros) {
                    int codigo = libro.getCodigo();
                    String titulo = libro.getTitulo();
                    int idAutor = libro.getAutor().getId();
                    int anio = libro.getAño();
                    boolean prestado = libro.getPrestado();
                    JsonObject objeto = new JsonObject();
                    objeto.addProperty("codigo", codigo);
                    objeto.addProperty("titulo", titulo);
                    objeto.addProperty("autor", idAutor);
                    objeto.addProperty("año", anio);
                    objeto.addProperty("prestado", prestado);
                    if(prestado == true){
                        int idCliente = libro.getPrestador().getId();
                        objeto.addProperty("prestador", idCliente);
                    }
                    listaObjetos.add(objeto);
                }
                String jsonStringLibros = gson.toJson(listaObjetos);
                file.write(jsonStringLibros);
                file.flush();
                file.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //MUESTRO RESULTADO EN EL OUTPUT
            System.out.println();
            System.out.println("DATOS GUARDADOS CORRECTAMENTE");

        } catch (Exception e) {
            System.err.println("ALGO HA SALIDO MAL");
            e.printStackTrace();
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
        this.listarClientes();
        System.out.print("Introduce el nombre del cliente: ");
        String clienteInput = System.console().readLine();
        //validar cliente
        boolean aux = validarCliente(clienteInput);
        if (aux == true) {
            this.listarLibros();
            System.out.print("[*] Introduzca el título: ");
            String nombreLibro = System.console().readLine();
            //validarLibro
            boolean auxiliar = validarLibro(nombreLibro);
            if (auxiliar == true) {
                //comprobar que el libro pertenece a dicho autor
                for (Libro libro : listaLibros) {
                    if (libro.getTitulo().equals(nombreLibro)) {
                        if (libro.getPrestador().getNombre() != null) {
                            if (libro.getPrestador().getNombre().equals(clienteInput)) {
                                for (Libro book : listaLibros) {
                                    if (book.getTitulo().equals(nombreLibro)) {
                                        if (book.getPrestado() == true) {
                                            this.devolverLibro(book);
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
                                System.out.println("EL CLIENTE NO ESTA ASOCIADO CON ESE LIBRO, PORFAVOR VUELVA A INTRODUCIR LOS DATOS CORRECTAMENTE");
                            } 
                        }else{
                            System.out.println("EL LIBRO NO TIENE PRESTADOR");
                        }
                    }
                }
            }else{
                System.out.println();
                System.out.println("[-] El libro no existe");
                System.out.println();
            }
        } else {
            System.out.println("[-] EL CLIENTE NO EXISTE");
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
    public void clienteConMasLibros(){
        int aux = 0;
        Cliente clienteValor = new Cliente();
        for (Cliente cliente : listaClientes) {
            int size = cliente.getLibroPrestado().size();
            if (size > aux) {
                aux = size;
                clienteValor = cliente;
            }
        }
        System.out.println("EL CLIENTE CON MAS LIBROS");
        System.out.println("-----------------------------------");
        System.out.println(clienteValor.toString());
        System.out.println("--------------------------------");
    }
    public void borrarAutor(){
        this.listarAutores();;
        System.out.print("Introduzca el nombre del autor: ");
        String nombreAutor = System.console().readLine();
        boolean aux = validarAutor(nombreAutor);
        if (aux == true) {
            int idAutor = autorG.getId();
            setBorrarAutor(idAutor);
            System.out.println("EL AUTOR SE HA BORRADO CORRECTAMENTE");
        }else{
            System.out.println("EL AUTOR NO EXISTE");
        }
    }
    public void borrarLibro(){
        this.listarLibros();
        System.out.print("Introduzca el nombre del Libro: ");
        String nombreLibro = System.console().readLine();
        boolean aux = validarLibro(nombreLibro);
        if (aux == true) {
            int codigLibro = libroG.getCodigo();
            setBorrarLibro(codigLibro);
            System.out.println("EL LIBRO SE HA BORRADO CORRECTAMENTE");
        }else{
            System.out.println("EL LIBRO NO EXISTE");
        }
    }
    public void borrarrCliente(){
        this.listarClientes();
        System.out.print("Introduzca el nombre del Cliente: ");
        String nombreCliente = System.console().readLine();
        boolean aux = validarCliente(nombreCliente);
        if (aux == true) {
            int id = clienteG.getId();
            for (int i = 0; i < listaClientes.size(); i++) {
                if (listaClientes.get(i).getId() == id) {
                    listaClientes.remove(i);
                }
            }
            System.out.println("EL LIBRO SE HA BORRADO CORRECTAMENTE");
        }else{
            System.out.println("EL LIBRO NO EXISTE");
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
                10. Cliente con más libros
                11. Borrar autor
                12. Borrar libro
                13. Borrar cliente
                14. Exit
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
            case "10" -> this.clienteConMasLibros();
            case "11" -> this.borrarAutor();
            case "12" -> this.borrarLibro();
            case "13" -> this.borrarrCliente(); 
            case "14" -> {
                this.crearJson();
                System.exit(0);}
            };
        this.menu();
    }
}