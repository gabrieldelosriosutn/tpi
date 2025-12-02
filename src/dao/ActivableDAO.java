/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author delosrios
 */
public abstract class ActivableDAO<T> extends GenericDAO<T> {
    
    public ActivableDAO(String tableName, Connection conn) {
        super(tableName, conn);
    }
    
    public boolean setActive(int id, boolean active) throws SQLException {
        int activeValue = active ? 1 : 0;
        String sqlQuery = "UPDATE " + tableName +" SET esta_activo = ? WHERE id = ?;";
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, activeValue);
        stmt.setInt(2, id);
        int rowsAffected = stmt.executeUpdate();
        return !(rowsAffected == 0);
    }
}
