package io.t3w.desafio.data.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    public void testToString(){
        Pessoa pessoa = new Pessoa()
                .setId(666)
                .setCpf("0123456789")
                .setNome("Pessoa Teste");

        Assertions.assertEquals("Pessoa{id=666, cpf='0123456789', nome='Pessoa Teste'}", pessoa.toString());
    }    }

