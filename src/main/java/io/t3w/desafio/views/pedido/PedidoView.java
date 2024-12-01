package io.t3w.desafio.views.pedido;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.t3w.desafio.components.T3WContentLayout;
import io.t3w.desafio.data.entity.Pedido;
import io.t3w.desafio.data.entity.PedidoItem;

import java.math.BigDecimal;

import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.html.VH4;

@PageTitle("pedido")
@Route("pedido/:id?")
public class PedidoView extends T3WContentLayout implements BeforeEnterObserver {

    private final VGrid<PedidoItem> gridItens;
    private final VGrid<Pedido> gridPedido;

    public PedidoView() {
        this.setPadding(true);

        gridPedido = new VGrid<Pedido>().withThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridPedido.addColumn(Pedido::getId).setHeader("ID");
        gridPedido.addColumn(p -> p.getPessoa().getNome()).setHeader("Cliente");
        gridPedido.addColumn(p -> p.getPessoa().getCpf()).setHeader("CPF");

        gridItens = new VGrid<PedidoItem>().withThemeVariants(GridVariant.LUMO_NO_BORDER);
        gridItens.addColumn(PedidoItem::getId).setHeader("ID");
        gridItens.addColumn(p -> p.getProduto().getDescricao()).setHeader("Produto");
        gridItens.addColumn(PedidoItem::getQuantidade).setHeader("Quantidade");
        gridItens.addColumn(p ->{
            // TODO: Implementar cálculo do valor total dos itens do pedido e formatá-lo no padrão brasileiro (1.000,00)
            return p.getProduto().getValorUnitario().multiply(BigDecimal.valueOf(p.getQuantidade()));
        }).setHeader("Valor");

        this.add(createCard("Pedido",gridPedido), createCard("Itens", gridItens));
    }

    private static VDiv createCard(final String titulo, final Component... components) {
        return new VDiv(new VH4(titulo))
            .withComponents(components)
            .withClassName("card");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        final var pedidoId = event.getRouteParameters().getLong("id");
        if (pedidoId.isPresent()) {
            // TODO: Preencha os itens do grid de pedidos e do grid de itens
        }
    }
}
