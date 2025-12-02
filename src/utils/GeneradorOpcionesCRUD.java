/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author delosrios
 */
public class GeneradorOpcionesCRUD {
    
   public static String[] generar(String singular, String plural){
       return new String[]{"Buscar " + singular, "Listar " + plural, "Crear " + singular, "Actualizar " + singular, 
           "Activar " + singular, "Desactivar " + singular, "Eliminar " + singular, "Salir" };
   }
}
