package com.ejer_poo.biblioteca;

public class Libro {
    private int codigo;
    private String titulo;
    private Autor autor; // 1 libro -> 1 autor
    private Cliente prestador;
    private int año;
    private Boolean prestado;
    public Cliente cliente = new Cliente();

    public Libro () {};

    public Libro(String titulo, Autor autor, int año, Boolean prestado, Cliente prestador) {
        setCodigo();
        setTitulo(titulo);
        setAutor(autor);
        setAño(año);
        setPrestado(prestado);
        setPrestador(prestador);
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo() {
        this.codigo = Utilidades.generarCodigoLibro();
    }

    public void setCodigManual(int id){
        this.codigo = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setPrestado(Boolean b) {
        this.prestado = b;
    }
    public Boolean getPrestado() {
        return prestado;
    }

    public void setPrestador(Cliente prestador) {
        if (prestador == null) {
            this.prestador = cliente;
        } else {
            this.prestador = prestador;
        }
    }
    public Cliente getPrestador() {
        return prestador;
    }

}
