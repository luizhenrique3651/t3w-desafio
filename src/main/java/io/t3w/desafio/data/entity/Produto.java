package io.t3w.desafio.data.entity;


import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descricao;
    private BigDecimal valorUnitario;


    @Override
    public String toString() {
        return "Produto{id=" + id + ", descricao='" + descricao + "', valorUnitario=" + valorUnitario + '}';
    }
}
