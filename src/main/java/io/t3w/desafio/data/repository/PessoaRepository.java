package io.t3w.desafio.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.t3w.desafio.data.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
