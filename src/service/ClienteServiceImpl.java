/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.util.Scanner;

import dao.ClienteDAO;
import java.util.List;
import model.Cliente;
import utils.Validadores;
import utils.TextosCliente; 
import utils.TextosComun; 

/**
 *
 * @author delosrios
 */
public class ClienteServiceImpl implements GenericService, Activable {
    private final ClienteDAO dao;
    private final Scanner scanner;
    
    private static final int MAX_LEN_NOM_Y_APELLIDO = 60;
    private static final int MAX_LEN_DNI = 8;
    
    public ClienteServiceImpl(ClienteDAO dao, Scanner scanner) {
        this.dao = dao;
        this.scanner = scanner;
    }

    @Override
    public void activar() {
        System.out.print(TextosCliente.TEXTO_INGRESE_EL_ID + " " + TextosCliente.TEXTO_DEL_CLIENTE + " " + TextosComun.TEXTO_QUE_DESEA + " activar: ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)) {
            int id = Integer.parseInt(input);
            if(modificarActivo(id, true)) {
                System.out.println(TextosComun.TEXTO_ACTIVACION_SATISFACTORIA);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
    }
    
    @Override
    public void desactivar() {
        System.out.print(TextosCliente.TEXTO_INGRESE_EL_ID + " " + TextosCliente.TEXTO_DEL_CLIENTE + " " + TextosComun.TEXTO_QUE_DESEA + " desactivar: ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)) {
            int id = Integer.parseInt(input);
            if(modificarActivo(id, false)) {
                System.out.println(TextosComun.TEXTO_DESACTIVACION_SATISFACTORIA);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
    }
    
    @Override
    public void actualizar() throws SQLException {
        System.out.print(TextosCliente.TEXTO_INGRESE_EL_ID + " " + TextosComun.TEXTO_QUE_DESEA + " modificar: ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)){
            int id = Integer.parseInt(input);
            Cliente cliente = dao.findById(id);
            if(cliente != null) {
                String nombre = Validadores.promptConConfirmacionYValidacion(scanner, 
                    TextosComun.TEXTO_DESEA_EDITAR_EL+ " nombre ", 
                    cliente.getNombre(),
                    TextosCliente.TEXTO_NOMBRE,
                    MAX_LEN_NOM_Y_APELLIDO
                );
                cliente.setNombre(nombre);
                String apellido = Validadores.promptConConfirmacionYValidacion(scanner, 
                    TextosComun.TEXTO_DESEA_EDITAR_EL + " apellido ", 
                    cliente.getApellido(),
                    TextosCliente.TEXTO_APELLIDO,
                    MAX_LEN_NOM_Y_APELLIDO
                );
                cliente.setApellido(apellido);
                String dni = Validadores.promptConConfirmacionYValidacion(scanner, 
                    TextosComun.TEXTO_DESEA_EDITAR_EL + " dni ", 
                    cliente.getDni(),
                    TextosCliente.TEXTO_INGRESE_DNI,
                    MAX_LEN_DNI
                );
                cliente.setDni(dni);
                dao.update(cliente);
                System.out.println(TextosCliente.TEXTO_EL_CLIENTE_HA + " actualizado " + TextosComun.TEXTO_SATISFACTORIO);
            } else {
                System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + " " + id);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.print(TextosCliente.TEXTO_INGRESE_EL_ID + " " + TextosComun.TEXTO_A_ELIMINAR + ": ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)) {
            int id = Integer.parseInt(input);
            if(dao.delete(id)) {
                System.out.println(TextosCliente.TEXTO_EL_CLIENTE_HA + " eliminado " + TextosComun.TEXTO_SATISFACTORIO);
            } else {
                System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + " " + id);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
    }

    @Override
    public void getAll() throws SQLException {
        List<Cliente> clientes = dao.findAll();
        if(!clientes.isEmpty()) {
            for(Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }else {
            System.out.println(TextosCliente.TEXTO_NO_CLIENTES);
        }
    }

    @Override
    public void getById() throws SQLException {
        System.out.print(TextosCliente.TEXTO_INGRESE_EL_ID + TextosComun.TEXTO_DESEA_BUSCAR + " ");
        int id;
        id = Integer.parseInt(scanner.nextLine());
        Cliente cliente = dao.findById(id);
        if(cliente != null) {
            System.out.println(cliente);
        } else {
                System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + " " + id);
        }   
    }
    
    public void getByDNI() throws SQLException {
        String dni = Validadores.promptConValidacion(scanner, TextosCliente.TEXTO_INGRESE_EL_DNI + " " + TextosComun.TEXTO_DESEA_BUSCAR, MAX_LEN_DNI);
        Cliente cliente = dao.findByDNI(dni);
        if(cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println(TextosCliente.TEXTO_NO_CLIENTE_DNI + " " + dni);
        }
    }

    @Override
    public void insertar() throws SQLException {
        String nombre = Validadores.promptConValidacion(scanner, TextosCliente.TEXTO_NOMBRE, MAX_LEN_NOM_Y_APELLIDO);
        String apellido = Validadores.promptConValidacion(scanner, TextosCliente.TEXTO_APELLIDO,MAX_LEN_NOM_Y_APELLIDO);
        String dni = Validadores.promptConValidacion(scanner, TextosCliente.TEXTO_INGRESE_DNI, MAX_LEN_DNI);
        
        Cliente cliente = new Cliente(nombre,apellido,dni);
        if(dao.findByDNI(dni) == null ) {
            dao.save(cliente);
            System.out.println(TextosCliente.TEXTO_CLIENTE_GUARDADO_SATISFACTORIAMENTE);
        } else {
            System.out.println("Ya existe un cliente registrado con ese DNI");
           
        }
    }
    
    private boolean modificarActivo(int id, boolean activo) {
        try {
            Cliente cliente = dao.findById(id);
            if(cliente != null) {
                return dao.setActive(id, activo);
            } else {
                System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + " " + id);
            }
        } catch(SQLException e) {
            System.out.println("Error al intentar actualizar el registro en la base de datos");
        }
        return false;
    }
    
}
