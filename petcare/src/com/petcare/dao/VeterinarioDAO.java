package com.petcare.dao;

import com.petcare.DBConnection;
import com.petcare.model.Veterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {

    public int create(Veterinario veterinario) throws SQLException {
        String sql = "INSERT INTO veterinarios (nome, crmv, especialidade, telefone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getCrmv());
            stmt.setString(3, veterinario.getEspecialidade());
            stmt.setString(4, veterinario.getTelefone());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    veterinario.setId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public Veterinario getById(int id) throws SQLException {
        String sql = "SELECT * FROM veterinarios WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public Veterinario getByCrmv(String crmv) throws SQLException {
        String sql = "SELECT * FROM veterinarios WHERE crmv = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, crmv);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Veterinario> getAll() throws SQLException {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                veterinarios.add(mapRow(rs));
            }
        }
        return veterinarios;
    }

    public boolean update(Veterinario veterinario) throws SQLException {
        String sql = "UPDATE veterinarios SET nome = ?, crmv = ?, especialidade = ?, telefone = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getCrmv());
            stmt.setString(3, veterinario.getEspecialidade());
            stmt.setString(4, veterinario.getTelefone());
            stmt.setInt(5, veterinario.getId());
            int updated = stmt.executeUpdate();
            return updated > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM veterinarios WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            return deleted > 0;
        }
    }

    private Veterinario mapRow(ResultSet rs) throws SQLException {
        Veterinario veterinario = new Veterinario();
        veterinario.setId(rs.getInt("id"));
        veterinario.setNome(rs.getString("nome"));
        veterinario.setCrmv(rs.getString("crmv"));
        veterinario.setEspecialidade(rs.getString("especialidade"));
        veterinario.setTelefone(rs.getString("telefone"));
        return veterinario;
    }
}
