/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.sql.SQLException;
import java.util.Scanner;

import service.ClienteServiceImpl;
import utils.GeneradorOpcionesCRUD;
import ui.MenuGestionarClientesBuscar;
/**
 *
 * @author delosrios
 */
public class MenuGestionarClientes extends Menu {
    private final ClienteServiceImpl service;
    private final MenuGestionarClientesBuscar menuGestionarClientesBuscar;
    public MenuGestionarClientes(Scanner scanner, ClienteServiceImpl service){        
        super(scanner, GeneradorOpcionesCRUD.generar("cliente", "clientes"));
        this.service = service;
        menuGestionarClientesBuscar = new MenuGestionarClientesBuscar(scanner, service);
    }
    
    
    @Override
    public void invocarAccion(int i) {
        switch(i) {
            case 1:
                menuGestionarClientesBuscar.ejecutar();
                break;
            case 2:
                try {
                    service.getAll();
                } catch(SQLException e) {
                    System.out.println("Error al intentar leer la base de datos");
                } catch(Exception e) {
                    System.out.println("Ha ocurrido un error");
                } finally {
                    System.out.println("");
                }
                break;
            case 3:
                try {
                    service.insertar();
                } catch(SQLException e) {
                    System.out.println("Error al intentar guardar el registro en la base de datos");
                } catch(Exception e) {
                    System.out.println("Ha ocurrido un error");
                } finally {
                    System.out.println("");
                }
                break;
            case 4:
                try {
                    service.actualizar();
                } catch(SQLException e) {
                    System.out.println("Error al intentar actualizar el registro en la base de datos");
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Ha ocurrido un error");
                } finally {
                    System.out.println("");
                }
                break;
            case 5:
                service.activar();
                System.out.println("");
                break;
            case 6:
                service.desactivar();
                System.out.println("");
                break;
            case 7:
                try {
                    service.eliminar();
                } catch(SQLException e) {
                    System.out.println("Error de procesamiento en la base de datos");
                } finally {
                    System.out.println("");
                }
                break;
        }
    }
}
