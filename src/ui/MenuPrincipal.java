/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import dao.ClienteDAO;
import dao.EscrituraDAO;
import dao.PropiedadDAO;
import java.util.Scanner;
import service.ClienteServiceImpl;
import java.sql.Connection;
import service.EscrituraServiceImpl;
import service.PropiedadServiceImpl;

/**
 *
 * @author delosrios
 */
public class MenuPrincipal extends Menu {
    
    
    MenuGestionarClientes menuGestionarClientes;
    MenuGestionarEscrituras menuGestionarEscrituras;
    MenuGestionarPropiedades menuGestionarPropiedades;
    
    public MenuPrincipal(Scanner scanner, Connection conn) {
        super(scanner, new String[]{"Gestionar clientes", "Gestionar propiedades", "Gestionar escrituras", "Salir"});
        ClienteDAO clienteDAO = new ClienteDAO(conn);
        PropiedadDAO propiedadDAO = new PropiedadDAO(conn, clienteDAO);
        EscrituraDAO escrituraDAO = new EscrituraDAO(conn, clienteDAO, propiedadDAO);
        this.menuGestionarClientes = new MenuGestionarClientes(scanner, new ClienteServiceImpl(clienteDAO, scanner));
        this.menuGestionarEscrituras = new MenuGestionarEscrituras(scanner, new EscrituraServiceImpl(escrituraDAO, scanner));
        this.menuGestionarPropiedades = new MenuGestionarPropiedades(scanner, new PropiedadServiceImpl(propiedadDAO, scanner));
    }

    @Override
    public void invocarAccion(int i) {
        switch(i) {
            case 1:
                menuGestionarClientes.ejecutar();
                break;
            case 2:
                menuGestionarPropiedades.ejecutar();
                break;
            case 3:
                menuGestionarEscrituras.ejecutar();
                break;
        }
        
        
    }
       
}
