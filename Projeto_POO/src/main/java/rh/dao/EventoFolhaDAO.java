package rh.dao;

import rh.modelo.EventoFolha;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoFolhaDAO {
    private Connection connection;

    public EventoFolhaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public void adicionar(EventoFolha e) {
        String sql = "INSERT INTO eventos_folha (funcionario_id, tipo_evento, descricao, valor) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, e.getFuncionarioId());
            stmt.setString(2, e.getTipoEvento());
            stmt.setString(3, e.getDescricao());
            stmt.setDouble(4, e.getValor());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar evento da folha: " + ex.getMessage(), ex);
        }
    }

    public List<EventoFolha> buscarPorFuncionario(int funcionarioId) {
        List<EventoFolha> eventos = new ArrayList<>();
        String sql = "SELECT * FROM eventos_folha WHERE funcionario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EventoFolha e = new EventoFolha(
                        rs.getInt("id"),
                        rs.getInt("funcionario_id"),
                        rs.getString("tipo_evento"),
                        rs.getString("descricao"),
                        rs.getDouble("valor")
                    );
                    eventos.add(e);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar eventos: " + ex.getMessage(), ex);
        }
        return eventos;
    }
}
