package com.petcare.dao;

import com.petcare.DBConnection;
import com.petcare.model.Animal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public int create(Animal animal) throws SQLException {
        String sql = "INSERT INTO animais (nome, especie, raca, data_nascimento, peso, proprietario_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            if (animal.getDataNascimento() != null) {
                stmt.setDate(4, Date.valueOf(animal.getDataNascimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            stmt.setDouble(5, animal.getPeso());
            stmt.setInt(6, animal.getProprietarioId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    animal.setId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public Animal getById(int id) throws SQLException {
        String sql = "SELECT * FROM animais WHERE id = ?";
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

    public List<Animal> getAll() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animais ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                animals.add(mapRow(rs));
            }
        }
        return animals;
    }

    public List<Animal> getByProprietarioId(int proprietarioId) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animais WHERE proprietario_id = ? ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proprietarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    animals.add(mapRow(rs));
                }
            }
        }
        return animals;
    }

    public boolean update(Animal animal) throws SQLException {
        String sql = "UPDATE animais SET nome = ?, especie = ?, raca = ?, data_nascimento = ?, peso = ?, proprietario_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            if (animal.getDataNascimento() != null) {
                stmt.setDate(4, Date.valueOf(animal.getDataNascimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            stmt.setDouble(5, animal.getPeso());
            stmt.setInt(6, animal.getProprietarioId());
            stmt.setInt(7, animal.getId());
            int updated = stmt.executeUpdate();
            return updated > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM animais WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            return deleted > 0;
        }
    }

    private Animal mapRow(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setId(rs.getInt("id"));
        animal.setNome(rs.getString("nome"));
        animal.setEspecie(rs.getString("especie"));
        animal.setRaca(rs.getString("raca"));
        Date date = rs.getDate("data_nascimento");
        if (date != null) {
            animal.setDataNascimento(date.toLocalDate());
        }
        animal.setPeso(rs.getDouble("peso"));
        animal.setProprietarioId(rs.getInt("proprietario_id"));
        return animal;
    }
}
