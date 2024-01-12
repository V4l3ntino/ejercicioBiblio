package com.ejer_poo;

import java.util.Scanner;

import com.ejer_poo.biblioteca.BibliotecaController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        
        var app = new BibliotecaController("a");

        app.menu();
        
    }
}