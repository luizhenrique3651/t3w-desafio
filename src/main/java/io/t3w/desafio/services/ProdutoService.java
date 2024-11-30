package io.t3w.desafio.services;

import io.t3w.desafio.data.entity.Produto;
import io.t3w.desafio.data.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Método para buscar todos os produtos
    public List<Produto> findProdutos() {
        return produtoRepository.findAll();
    }

    // Método para buscar um produto por ID
    public Optional<Produto> findProdutoById(Long id) {
        return produtoRepository.findById(id);
    }

    // Método para salvar ou atualizar produto
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Método para deletar produto por ID
    public boolean deleteById(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
