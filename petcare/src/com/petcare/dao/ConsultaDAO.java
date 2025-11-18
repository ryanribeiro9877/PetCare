package com.petcare.dao;

import com.petcare.DBConnection;
import com.petcare.model.Consulta;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public int create(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consultas (data_hora, animal_id, veterinario_id, diagnostico, valor) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setInt(2, consulta.getAnimalId());
            stmt.setInt(3, consulta.getVeterinarioId());
            stmt.setString(4, consulta.getDiagnostico());
            stmt.setDouble(5, consulta.getValor());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    consulta.setId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public Consulta getById(int id) throws SQLException {
        String sql = "SELECT * FROM consultas WHERE id = ?";
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

    public List<Consulta> getAll() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consultas ORDER BY data_hora";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                consultas.add(mapRow(rs));
            }
        }
        return consultas;
    }

    public boolean update(Consulta consulta) throws SQLException {
        String sql = "UPDATE consultas SET data_hora = ?, animal_id = ?, veterinario_id = ?, diagnostico = ?, valor = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setInt(2, consulta.getAnimalId());
            stmt.setInt(3, consulta.getVeterinarioId());
            stmt.setString(4, consulta.getDiagnostico());
            stmt.setDouble(5, consulta.getValor());
            stmt.setInt(6, consulta.getId());
            int updated = stmt.executeUpdate();
            return updated > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM consultas WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            return deleted > 0;
        }
    }


    public List<Consulta> getByOwnerCpf(String cpf) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.* FROM consultas c " +
                     "JOIN animais a ON c.animal_id = a.id " +
                     "JOIN proprietarios p ON a.proprietario_id = p.id " +
                     "WHERE p.cpf = ? ORDER BY c.data_hora";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(mapRow(rs));
                }
            }
        }
        return consultas;
    }

    private Consulta mapRow(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setId(rs.getInt("id"));
        Timestamp ts = rs.getTimestamp("data_hora");
        if (ts != null) {
            consulta.setDataHora(ts.toLocalDateTime());
        }
        consulta.setAnimalId(rs.getInt("animal_id"));
        consulta.setVeterinarioId(rs.getInt("veterinario_id"));
        consulta.setDiagnostico(rs.getString("diagnostico"));
        consulta.setValor(rs.getDouble("valor"));
        return consulta;
    }

	public boolean existeConsultaNoHorario(LocalDateTime dataHora) {
		return false;
	}

	public List<LocalTime> horariosDisponiveis(LocalDate data) {
		return null;
	}
}
