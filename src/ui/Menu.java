/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.Scanner;

/**
 *
 * @author delosrios
 */
public abstract class Menu {
    private String[] opciones = {};
    public Scanner scanner;
    
    public Menu(Scanner scanner, String[] opciones){
        this.scanner = scanner;
        this.opciones = opciones;
    }
    
    public void ejecutar() {
        int senialDeSalida = -1;
        while(senialDeSalida != opciones.length) {
            imprimir();
            System.out.print("Ingrese una opcion: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                System.out.println("");

                if(opcion >= 1 && opcion <= opciones.length) {
                   invocarAccion(opcion);
                   senialDeSalida = opcion;
                } else {
                    System.out.println("Opcion invalida");
                }
            } catch(NumberFormatException e) {
                System.out.println("Opcion invalida");
            }
        }
    }
    
            
    private void imprimir() {
        for (int i = 0; i < opciones.length; i++) {
            System.out.println(i+1 + ". " + opciones[i]);
        }
    }
    

    
    public int getOpcionesLen() {
        return this.opciones.length;
    }
    
    public abstract void invocarAccion(int i);
}
