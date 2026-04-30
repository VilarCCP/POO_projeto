package rh.dao;

import rh.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public void adicionar(Funcionario f) {
        String sql = "INSERT INTO funcionarios (tipo, nome, depto, ativo, salario_base, valor_hora, horas_trabalhadas, valor_contrato) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getDepto());
            stmt.setBoolean(4, f.isAtivo());
            
            // Valores default para evitar null onde não se aplica
            stmt.setNull(5, Types.DECIMAL);
            stmt.setNull(6, Types.DECIMAL);
            stmt.setNull(7, Types.INTEGER);
            stmt.setNull(8, Types.DECIMAL);

            if (f instanceof FuncionarioCLT) {
                stmt.setString(1, "CLT");
                stmt.setDouble(5, ((FuncionarioCLT) f).getSalarioBase());
            } else if (f instanceof FuncionarioHorista) {
                stmt.setString(1, "HORISTA");
                stmt.setDouble(6, ((FuncionarioHorista) f).getValorHora());
                stmt.setInt(7, ((FuncionarioHorista) f).getHorasTrabalhadas());
            } else if (f instanceof FuncionarioPJ) {
                stmt.setString(1, "PJ");
                stmt.setDouble(8, ((FuncionarioPJ) f).getValorContrato());
            }

            stmt.executeUpdate();

            // Obter ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    f.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage(), e);
        }
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String nome = rs.getString("nome");
                String depto = rs.getString("depto");
                boolean ativo = rs.getBoolean("ativo");

                Funcionario f = null;
                switch (tipo) {
                    case "CLT":
                        f = new FuncionarioCLT(id, nome, depto, ativo, rs.getDouble("salario_base"));
                        break;
                    case "HORISTA":
                        f = new FuncionarioHorista(id, nome, depto, ativo, rs.getDouble("valor_hora"), rs.getInt("horas_trabalhadas"));
                        break;
                    case "PJ":
                        f = new FuncionarioPJ(id, nome, depto, ativo, rs.getDouble("valor_contrato"));
                        break;
                }
                if (f != null) {
                    funcionarios.add(f);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }
        return funcionarios;
    }

    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    String nome = rs.getString("nome");
                    String depto = rs.getString("depto");
                    boolean ativo = rs.getBoolean("ativo");
                    
                    switch (tipo) {
                        case "CLT":
                            return new FuncionarioCLT(id, nome, depto, ativo, rs.getDouble("salario_base"));
                        case "HORISTA":
                            return new FuncionarioHorista(id, nome, depto, ativo, rs.getDouble("valor_hora"), rs.getInt("horas_trabalhadas"));
                        case "PJ":
                            return new FuncionarioPJ(id, nome, depto, ativo, rs.getDouble("valor_contrato"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Funcionario f) {
        String sql = "UPDATE funcionarios SET nome=?, depto=?, ativo=?, salario_base=?, valor_hora=?, horas_trabalhadas=?, valor_contrato=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getDepto());
            stmt.setBoolean(3, f.isAtivo());
            
            stmt.setNull(4, Types.DECIMAL);
            stmt.setNull(5, Types.DECIMAL);
            stmt.setNull(6, Types.INTEGER);
            stmt.setNull(7, Types.DECIMAL);

            if (f instanceof FuncionarioCLT) {
                stmt.setDouble(4, ((FuncionarioCLT) f).getSalarioBase());
            } else if (f instanceof FuncionarioHorista) {
                stmt.setDouble(5, ((FuncionarioHorista) f).getValorHora());
                stmt.setInt(6, ((FuncionarioHorista) f).getHorasTrabalhadas());
            } else if (f instanceof FuncionarioPJ) {
                stmt.setDouble(7, ((FuncionarioPJ) f).getValorContrato());
            }
            
            stmt.setInt(8, f.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário: " + e.getMessage(), e);
        }
    }
}
