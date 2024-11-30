package io.t3w.desafio.data.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    public void testToString(){
        Pessoa pessoa = new Pessoa(666, "0123456789", "Pessoa Teste");

        Assertions.assertEquals("Pessoa{id=666, cpf='0123456789', nome='Pessoa Teste'}", pessoa.toString());
    }    }

