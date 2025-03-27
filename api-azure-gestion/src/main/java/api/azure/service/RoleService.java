package api.azure.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import api.azure.connection.DatabaseConnection;
import api.azure.model.Role;


public class RoleService {

    // Crear un nuevo rol
    public void createRole(Role role) throws SQLException {
        String sql = "INSERT INTO roles (name) VALUES (?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, role.getName());
            stmt.executeUpdate();
        }
    }

    // Obtener todos los roles
    public List<Role> getAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                
                Role role = new Role(id, name);
                roles.add(role);
            }
        }
        return roles;
    }

    // Actualizar un rol
    public void updateRole(Long id, Role role) throws SQLException {
        String sql = "UPDATE roles SET name = ? WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, role.getName());
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }
    }

    // Eliminar un rol
    public void deleteRole(Long id) throws SQLException {
        String sql = "DELETE FROM roles WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
