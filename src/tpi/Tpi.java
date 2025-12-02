/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tpi;
import java.sql.Connection;
import java.sql.SQLException;

import config.DatabaseConnection;
import java.util.Scanner;
import ui.MenuPrincipal;

/**
 *
 * @author delosrios
 */
public class Tpi {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try(Connection conn = DatabaseConnection.getConnection();
            Scanner scanner = new Scanner(System.in);) {
            (new MenuPrincipal(scanner, conn)).ejecutar();
        }catch(SQLException e){
            System.out.println("Error al intentar establecer la conexión a la base de datos");
        }
    }
}
