package io.t3w.desafio.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cpf;
    private String nome;


    @Override
    public String toString() {
        return "Pessoa{id=" + id +
               ", cpf='" + cpf + '\'' +
               ", nome='" + nome + '\'' +
               '}';
    }
}
