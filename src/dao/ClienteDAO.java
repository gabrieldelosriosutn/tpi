/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Cliente;
/**
 *
 * @author delosrios
 */
public class ClienteDAO extends ActivableDAO<Cliente> {

    public ClienteDAO(Connection conn) {
        super(Cliente.class.getSimpleName(), conn);
    }

    @Override
    public Cliente mapper(ResultSet rs) {
        Cliente cliente = null;
        try {
            long id = rs.getInt("id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            boolean estaActivo = rs.getBoolean("esta_activo");
            LocalDate fechaDeAlta = LocalDate.parse(rs.getString("fecha_de_alta"), formatter); 
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String dni = rs.getString("dni");
            cliente = new Cliente(id, estaActivo, fechaDeAlta, nombre, apellido, dni);
            
        } catch(SQLException e) {
            System.out.println("Error de la base de datos al ejecutar la consulta");
        }
        return cliente;
    }

    @Override
    public void save(Cliente cliente) throws SQLException {
        String sqlQuery = "INSERT INTO " + tableName +" (nombre, apellido, dni) VALUES (?, ?, ?);";
        PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, cliente.getNombre());
        stmt.setString(2, cliente.getApellido());
        stmt.setString(3, cliente.getDni());
        stmt.executeUpdate();
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Error al intentar guardar el cliente");
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        String sqlQuery = "UPDATE " + tableName +" SET nombre = ?, apellido = ?, dni = ? WHERE id = ?;";
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setString(1, cliente.getNombre());
        stmt.setString(2, cliente.getApellido());
        stmt.setString(3, cliente.getDni());
        stmt.setInt(4, (int)cliente.getId());

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Error al intentar actualizar el cliente");
        }
        
    }
    
    public Cliente findByDNI(String dni) throws SQLException {
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE dni = ? ;";
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setString(1, dni);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            return mapper(rs);
        }
        return null;
    }
    
}
