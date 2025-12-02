/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.sql.SQLException;
import java.util.Scanner;

import service.PropiedadServiceImpl;
import utils.GeneradorOpcionesCRUD;

/**
 *
 * @author delosrios
 */
public class MenuGestionarPropiedades extends Menu {
    private final PropiedadServiceImpl service;
    public MenuGestionarPropiedades(Scanner scanner, PropiedadServiceImpl service){        
        super(scanner, GeneradorOpcionesCRUD.generar("propiedad", "propiedades"));
        this.service = service;
    }

    @Override
    public void invocarAccion(int i) {
        switch(i) {
            case 1:
                try {
                    service.getById();
                } catch(SQLException e) {
                    System.out.println("error db");
                }finally {
                    System.out.println("");
                }
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
