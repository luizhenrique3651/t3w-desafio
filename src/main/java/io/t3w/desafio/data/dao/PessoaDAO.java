package io.t3w.desafio.data.dao;

import io.t3w.desafio.data.entity.Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends DAO {

    private static Pessoa parse(final ResultSet rs) throws SQLException {
        return new Pessoa()
            .setId(rs.getLong("id"))
            .setNome(rs.getString("nome"))
            .setCpf(rs.getString("cpf"));
    }

    public PessoaDAO(final Connection connection) {
        super(connection);
    }

    public Pessoa insert(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, cpf) VALUES (?, ?) RETURNING id";

        try (PreparedStatement psmt = getConnection().prepareStatement(sql)) {
            psmt.setString(1, pessoa.getNome());
            psmt.setString(2, pessoa.getCpf());

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    pessoa.setId(rs.getLong("id"));
                }
            }
        }

        return pessoa;
    }

    public List<Pessoa> findAll() throws SQLException {
        String sql = "SELECT * FROM pessoa";

        try (PreparedStatement psmt = getConnection().prepareStatement(sql)) {
            try (ResultSet rs = psmt.executeQuery()) {
                List<Pessoa> pessoas = new ArrayList<>();
                while (rs.next()) {
                    pessoas.add(parse(rs));
                }
                return pessoas;
            }
        }
    }

    public Pessoa findById(long id) throws SQLException {
        String sql = "SELECT * FROM pessoa WHERE id = ?";

        try (PreparedStatement psmt = getConnection().prepareStatement(sql)) {
            psmt.setLong(1, id);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return parse(rs);
                }
                return null; 
            }
        }
    }

    public Pessoa update(Pessoa pessoa) throws SQLException {
        String sql = "UPDATE pessoa SET nome = ?, cpf = ? WHERE id = ?";

        try (PreparedStatement psmt = getConnection().prepareStatement(sql)) {
            psmt.setString(1, pessoa.getNome());
            psmt.setString(2, pessoa.getCpf());
            psmt.setLong(3, pessoa.getId());

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Falha ao atualizar: Pessoa não encontrada.");
            }
        }

        return pessoa;
    }

    public boolean deleteById(long id) throws SQLException {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (PreparedStatement psmt = getConnection().prepareStatement(sql)) {
            psmt.setLong(1, id);

            int rowsAffected = psmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
