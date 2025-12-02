/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.util.Scanner;
import service.EscrituraServiceImpl;
import utils.GeneradorOpcionesCRUD;

/**
 *
 * @author delosrios
 */
public class MenuGestionarEscrituras extends Menu {
    
    private final EscrituraServiceImpl service;
    public MenuGestionarEscrituras(Scanner scanner, EscrituraServiceImpl service) {
        super(scanner, GeneradorOpcionesCRUD.generar("escritura", "escrituras"));
        this.service = service;
    }
    
    @Override
    public void invocarAccion(int i) {
        switch(i) {
            case 1:
                try {
                    service.getById();
                } catch(java.sql.SQLException e) {
                    System.out.println("error db");
                }finally {
                    System.out.println("");
                }
                break;
            case 2:
                try {
                    service.getAll();
                } catch(java.sql.SQLException e) {
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
                } catch(java.sql.SQLException e) {
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
                } catch(java.sql.SQLException e) {
                    System.out.println(e.getMessage());
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
                } catch(java.sql.SQLException e) {
                    System.out.println("Error de procesamiento en la base de datos");
                } finally {
                    System.out.println("");
                }
                break;
        }
    }
    
}
