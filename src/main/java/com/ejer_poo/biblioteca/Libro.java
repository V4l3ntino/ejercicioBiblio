package com.ejer_poo.biblioteca;

public class Libro {
    private String codigo;
    private String titulo;
    private Autor autor; // 1 libro -> 1 autor
    private int año;
    private Boolean prestado;

    public Libro(String titulo, Autor autor, int año) {
        setCodigo();
        setTitulo(titulo);
        setAutor(autor);
        setAño(año);
        setPrestado(false);
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo() {
        this.codigo = Utilidades.generarCodigoLibro();
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

    public void imprimir() {
        
    }
}
