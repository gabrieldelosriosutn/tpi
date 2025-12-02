package utils;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author delosrios
 */
public class Validadores {
    
    public static boolean validarString(String str) {
        boolean validacion = str.isEmpty() || str.isBlank();
        if(validacion) System.out.println("El campo no puede estar vacio");
        return !(validacion);
    }
    
    public static boolean validarString(String str, int maxLen) {
        boolean validacion = str.length() <= maxLen;
        if(!validacion) System.out.println("El máximo de carácteres permitidos para este campo es de " + maxLen);
        return validarString(str) && validacion;
    }
        
    public static boolean validarString(String str, int maxLen, int minLen) {
        if (minLen < 0) minLen = 0;
        boolean validacion = str.length() >= minLen;
        if(!validacion) System.out.println("El mínimo de carácteres permitidos para este campo es de " + minLen);
        return validarString(str, maxLen) && validacion; 
    }
    
    public static boolean validarInt(String str) {
        boolean valid = false;
        try{
            Integer.parseInt(str);
            valid = true;
        } catch(NumberFormatException e) {}
        return valid;
    }
    
    public static boolean validarDouble(String str) {
        boolean valid = false;
        try{
            Double.parseDouble(str);
            valid = true;
        } catch(NumberFormatException e) {}
        return valid;
    }
    
    public static String promptConValidacion(Scanner scanner, String prompt) {
        String input = "";
        boolean esValido = false;
        while(!esValido) {
            System.out.print(prompt);
            input = scanner.nextLine();
            esValido = Validadores.validarString(input);
        }
        return input;
    }
    
    public static String promptConValidacion(Scanner scanner, String prompt, int maxLen) {
        String input = "";
        boolean esValido = false;
        while(!esValido) {
            System.out.print(prompt);
            input = scanner.nextLine();
            esValido = Validadores.validarString(input, maxLen);
        }
        return input;
    }
    
    public static String promptConValidacion(Scanner scanner, String prompt, int maxLen, int minLen) {
        String input = "";
        boolean esValido = false;
        while(!esValido) {
            System.out.print(prompt);
            input = scanner.nextLine();
            esValido = Validadores.validarString(input, maxLen, minLen);
        }
        return input;
    }
        
    public static String promptConConfirmacion(Scanner scanner, String mensajeConfirmacion, String valorPorDefecto, String prompt) {
        mensajeConfirmacion = mensajeConfirmacion + "(s/n): ";
        String sn = "";
        String input = valorPorDefecto;
        while(sn.compareToIgnoreCase("s") != 0 && 
              sn.compareToIgnoreCase("n") != 0 )
        {
            System.out.print(mensajeConfirmacion);
            sn = scanner.nextLine();
            if(sn.compareToIgnoreCase("s") == 0) {
                System.out.print(prompt);
                input = scanner.nextLine();
            }
        }
        return input;
    } 
    
    //Oportunidad de usar callbacks
    public static String promptConConfirmacionYValidacion(Scanner scanner, String mensajeConfirmacion, String valorPorDefecto, String prompt) {
        mensajeConfirmacion = mensajeConfirmacion + "(s/n): ";
        String sn = "";
        String input = valorPorDefecto;
        while(sn.compareToIgnoreCase("s") != 0 && 
              sn.compareToIgnoreCase("n") != 0 )
        {
            System.out.print(mensajeConfirmacion);
            sn = scanner.nextLine();
            if(sn.compareToIgnoreCase("s") == 0) {
                input = promptConValidacion( scanner, prompt);
            }
        }
        return input;
    }
    
    public static String promptConConfirmacionYValidacion(Scanner scanner, String mensajeConfirmacion, String valorPorDefecto, String prompt, int maxLen) {
        mensajeConfirmacion = mensajeConfirmacion + "(s/n): ";
        String sn = "";
        String input = valorPorDefecto;
        while(sn.compareToIgnoreCase("s") != 0 && 
              sn.compareToIgnoreCase("n") != 0 )
        {
            System.out.print(mensajeConfirmacion);
            sn = scanner.nextLine();
            if(sn.compareToIgnoreCase("s") == 0) {
                input = promptConValidacion(scanner, prompt, maxLen);
            }
        }
        return input;        
    }
    
    public static String promptConConfirmacionYValidacion(Scanner scanner, String mensajeConfirmacion, String valorPorDefecto, String prompt, int maxLen, int minLen) {
        mensajeConfirmacion = mensajeConfirmacion + "(s/n): ";
        String sn = "";
        String input = valorPorDefecto;
        while(sn.compareToIgnoreCase("s") != 0 && 
              sn.compareToIgnoreCase("n") != 0 )
        {
            System.out.print(mensajeConfirmacion);
            sn = scanner.nextLine();
            if(sn.compareToIgnoreCase("s") == 0) {
                input = promptConValidacion(scanner, prompt, maxLen, minLen);
            }
        }
        return input;          
    }
    
    public static int promptConConfirmacionYValidacion(Scanner scanner, String mensajeConfirmacion, int valorPorDefecto, String prompt) {
        mensajeConfirmacion = mensajeConfirmacion + "(s/n): ";
        String sn = "";
        int resultado = valorPorDefecto;
        while(sn.compareToIgnoreCase("s") != 0 && 
              sn.compareToIgnoreCase("n") != 0 )
        {
            System.out.print(mensajeConfirmacion);
            sn = scanner.nextLine();
            if(sn.compareToIgnoreCase("s") == 0) {
                System.out.print(prompt);
                String input = scanner.nextLine();
                while(!validarInt(input)) {
                    System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
                    System.out.print(prompt);
                    input = scanner.nextLine();
                }
                resultado = Integer.parseInt(input);
            }
        }
        return resultado;          
    }
    
    public static Double promptConConfirmacionYValidacion(Scanner scanner, String mensajeConfirmacion, double valorPorDefecto, String prompt) {
        mensajeConfirmacion = mensajeConfirmacion + "(s/n): ";
        String sn = "";
        double resultado = valorPorDefecto;
        while(sn.compareToIgnoreCase("s") != 0 && 
              sn.compareToIgnoreCase("n") != 0 )
        {
            System.out.print(mensajeConfirmacion);
            sn = scanner.nextLine();
            if(sn.compareToIgnoreCase("s") == 0) {
                System.out.print(prompt);
                String input = scanner.nextLine();
                while(!validarDouble(input)) {
                    System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
                    System.out.print(prompt);
                    input = scanner.nextLine();
                }
                resultado = Double.parseDouble(input);
            }
        }
        return resultado;          
    }
}
