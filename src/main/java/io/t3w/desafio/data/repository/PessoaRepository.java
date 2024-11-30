package io.t3w.desafio.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.t3w.desafio.data.entity.Pessoa;
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
