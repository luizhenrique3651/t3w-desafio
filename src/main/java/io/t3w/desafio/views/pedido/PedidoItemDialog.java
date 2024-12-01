package io.t3w.desafio.views.pedido;

import com.vaadin.flow.component.textfield.NumberField;
import io.t3w.desafio.components.T3WBinder;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WFormLayout;
import io.t3w.desafio.data.entity.PedidoItem;
import io.t3w.desafio.data.entity.Produto;
import io.t3w.desafio.services.ProdutoService;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.combobox.VComboBox;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.html.VDiv;

import java.util.function.Consumer;

public class PedidoItemDialog extends VDialog {

    public PedidoItemDialog(final PedidoItem pedidoItem, final ProdutoService produtoService, final Consumer<PedidoItem> consumer) {
        setHeaderTitle("Adicionar/Editar Item do Pedido");
        setWidth("400px");

        final var form = new T3WFormLayout();
        final var binder = new T3WBinder<>(pedidoItem);

        final var cbProduto = new VComboBox<Produto>("Produto");
        cbProduto.setItems(produtoService.findProdutos()); // Preencher o combo com os produtos disponíveis
        cbProduto.setItemLabelGenerator(Produto::getDescricao);
        binder.forField(cbProduto).bind(PedidoItem::getProduto, PedidoItem::setProduto);
        form.add(cbProduto);

        final var nfQuantidade = new NumberField("Quantidade");
        nfQuantidade.setMin(1); 
        binder.forField(nfQuantidade)
            .withConverter(Double::intValue, Integer::doubleValue)
            .bind(PedidoItem::getQuantidade, PedidoItem::setQuantidade);
        form.add(nfQuantidade);

        final var btnSalvar = new T3WButton("Salvar").themePrimary()
            .withClickListener(ev -> {
                if (binder.validate().isOk()) {
                    consumer.accept(pedidoItem);
                    this.close();
                }
            });

        final var btnCancelar = new VButton("Cancelar").withClickListener(ev -> this.close());

        this.add(form);
        getFooter().add(new VDiv(btnSalvar, btnCancelar).withClassName("dialog-footer"));
    }
}
