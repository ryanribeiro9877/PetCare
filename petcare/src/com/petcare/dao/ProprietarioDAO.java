package com.petcare.dao;

import com.petcare.DBConnection;
import com.petcare.model.Proprietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {

    public int create(Proprietario proprietario) throws SQLException {
        String sql = "INSERT INTO proprietarios (nome, cpf, telefone, endereco, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, proprietario.getNome());
            stmt.setString(2, proprietario.getCpf());
            stmt.setString(3, proprietario.getTelefone());
            stmt.setString(4, proprietario.getEndereco());
            stmt.setString(5, proprietario.getEmail());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    proprietario.setId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public Proprietario getById(int id) throws SQLException {
        String sql = "SELECT * FROM proprietarios WHERE id = ?";
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

    public Proprietario getByCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM proprietarios WHERE cpf = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Proprietario> getAll() throws SQLException {
        List<Proprietario> proprietarios = new ArrayList<>();
        String sql = "SELECT * FROM proprietarios ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                proprietarios.add(mapRow(rs));
            }
        }
        return proprietarios;
    }

    public boolean update(Proprietario proprietario) throws SQLException {
        String sql = "UPDATE proprietarios SET nome = ?, cpf = ?, telefone = ?, endereco = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proprietario.getNome());
            stmt.setString(2, proprietario.getCpf());
            stmt.setString(3, proprietario.getTelefone());
            stmt.setString(4, proprietario.getEndereco());
            stmt.setString(5, proprietario.getEmail());
            stmt.setInt(6, proprietario.getId());
            int updated = stmt.executeUpdate();
            return updated > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM proprietarios WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            return deleted > 0;
        }
    }

    private Proprietario mapRow(ResultSet rs) throws SQLException {
        Proprietario proprietario = new Proprietario();
        proprietario.setId(rs.getInt("id"));
        proprietario.setNome(rs.getString("nome"));
        proprietario.setCpf(rs.getString("cpf"));
        proprietario.setTelefone(rs.getString("telefone"));
        proprietario.setEndereco(rs.getString("endereco"));
        proprietario.setEmail(rs.getString("email"));
        return proprietario;
    }
}
