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

    public List<Pessoa> findAll() throws SQLException {
        try (PreparedStatement psmt = getConnection().prepareStatement("SELECT * FROM pessoa")) {
            try (ResultSet rs = psmt.executeQuery()) {
                final List<Pessoa> pessoas = new ArrayList<>();
                while (rs.next()) {
                    final var pessoa = new Pessoa()
                        .setId(rs.getLong("id"))
                        .setNome(rs.getString("nome"))
                        .setCpf(rs.getString("cpf"));
                    pessoas.add(pessoa);
                }
                return pessoas;
            }
        }
    }
    
    public Pessoa insert(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, cpf) VALUES (?, ?) RETURNING id";

        try (PreparedStatement psmt = getConnection().prepareStatement(sql)) {
            psmt.setString(1, pessoa.getNome());
            psmt.setString(2, pessoa.getCpf());

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                	// Define o ID retornado na instância da pessoa
                    pessoa.setId(rs.getLong("id")); 
                }
            }
        }

        return pessoa; 
    }

}
