package ui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.SQLException;
import java.util.Scanner;
import service.ClienteServiceImpl;
import ui.Menu;

/**
 *
 * @author delosrios
 */
public class MenuGestionarClientesBuscar extends Menu {
    private ClienteServiceImpl service;
    
    public MenuGestionarClientesBuscar(Scanner scanner, ClienteServiceImpl service) {
    super(scanner, new String[]{"Buscar por ID", "Buscar por DNI", "Salir"});
        this.service = service;
    }
    
    @Override
    public void invocarAccion(int i) {
        switch(i) {
            case 1:
                try {
                    service.getById();
                } catch(SQLException e) {
                    System.out.println("Error al intentar leer la base de datos");
                } catch(Exception e) {
                    System.out.println("Ha ocurrido un error");
                }
                System.out.println("");
                break;
            case 2:
                try {
                    service.getByDNI();
                } catch(SQLException e) {
                    System.out.println("Error al intentar leer la base de datos");
                } catch(Exception e) {
                    System.out.println("Ha ocurrido un error");
                }
                System.out.println("");
                break;
        }
    }
}
