package io.t3w.desafio.services;

import io.t3w.desafio.data.entity.PedidoItem;
import io.t3w.desafio.data.repository.PedidoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoItemService {

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    public List<PedidoItem> findPedidoItems() {
        return pedidoItemRepository.findAll();
    }

    public Optional<PedidoItem> findPedidoItemById(Long id) {
        return pedidoItemRepository.findById(id);
    }

    public PedidoItem save(PedidoItem pedidoItem) {
        return pedidoItemRepository.save(pedidoItem);
    }

    public boolean deleteById(Long id) {
        if (pedidoItemRepository.existsById(id)) {
            pedidoItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
