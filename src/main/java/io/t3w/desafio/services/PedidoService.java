package io.t3w.desafio.services;

import io.t3w.desafio.data.entity.Pedido;
import io.t3w.desafio.data.entity.PedidoItem;
import io.t3w.desafio.data.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findPedidoById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        return pedidoRepository.save(pedido);
    }

    public boolean deleteById(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
