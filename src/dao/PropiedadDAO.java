/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Cliente;
import model.Propiedad;
/**
 *
 * @author delosrios
 */
public class PropiedadDAO extends ActivableDAO<Propiedad> {
    
    public final ClienteDAO clienteDAO;
    public PropiedadDAO(Connection conn, ClienteDAO clienteDAO) {
        super(Propiedad.class.getSimpleName(), conn);
        this.clienteDAO = clienteDAO;
    }
    
    @Override
    public Propiedad mapper(ResultSet rs) {
        Propiedad propiedad = null;
        try {
            long id = rs.getInt("id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            boolean estaActivo = rs.getBoolean("esta_activo");
            LocalDate fechaDeAlta = LocalDate.parse(rs.getString("fecha_de_alta"), formatter); 
            String ubicacion = rs.getString("ubicacion");
            Double superfice = rs.getDouble("superficie");
            int aniosDeAntiguedad = rs.getInt("anios_de_antiguedad");
            int duenioID = rs.getInt("duenio");
            Cliente cliente = null;
            if(duenioID != 0) {
                cliente = clienteDAO.findById(duenioID);
            }
            propiedad = new Propiedad(id, estaActivo, fechaDeAlta, cliente, ubicacion, superfice, aniosDeAntiguedad);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error de la base de datos al ejecutar la consulta");
        }
        return propiedad;
    }

    @Override
    public void save(Propiedad propiedad) throws SQLException {
        String sqlQuery = "INSERT INTO " + tableName + " (ubicacion, superficie, anios_de_antiguedad, duenio) VALUES (?, ?, ?, ?);";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, propiedad.getUbicacion());
        stmt.setDouble(2, propiedad.getSuperfice());
        stmt.setInt(3, propiedad.getAniosDeAntiguedad());
        stmt.setObject(4, propiedad.getDuenioId(), java.sql.Types.INTEGER);

        stmt.executeUpdate();
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Error al intentar guardar el cliente");
        }    
    }

    @Override
    public void update(Propiedad propiedad) throws SQLException {
        String sqlQuery = "UPDATE " + tableName +" SET ubicacion = ?, superficie = ?, anios_de_antiguedad = ?, duenio = ? WHERE id = ?;";
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setString(1, propiedad.getUbicacion());
        stmt.setDouble(2, propiedad.getSuperfice());
        stmt.setInt(3, propiedad.getAniosDeAntiguedad());
        stmt.setObject(4, propiedad.getDuenioId(), java.sql.Types.INTEGER);
        stmt.setInt(5, (int)propiedad.getId());

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Error al intentar actualizar la propiedad");
        }
    }
    
}
