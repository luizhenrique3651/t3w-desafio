package io.t3w.desafio.views.pedido;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.firitin.components.formlayout.VFormLayout;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.t3w.desafio.T3WUtils;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WContentLayout;
import io.t3w.desafio.data.entity.Pedido;
import io.t3w.desafio.data.entity.PedidoItem;
import io.t3w.desafio.services.PedidoItemService;
import io.t3w.desafio.services.PedidoService;
import io.t3w.desafio.services.PessoaService;
import io.t3w.desafio.services.ProdutoService;

@Route("pedidos")
@PageTitle("Pedidos")
@Menu(order = 0, title = "Pedidos")
public class PedidosView extends T3WContentLayout implements BeforeEnterObserver {

	private final VGrid<Pedido> gridPedidos;

	@Autowired
	private PedidoItemService pedidoItemService;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PedidoService pedidoService;

	public PedidosView(final ProdutoService produtoService) {
		gridPedidos = new VGrid<>();
		gridPedidos.withThemeVariants(GridVariant.LUMO_NO_BORDER);
		gridPedidos.addColumn(Pedido::getId).setHeader("ID");
		gridPedidos.addColumn(pedido -> pedido.getPessoa().getNome()).setHeader("Cliente");
		gridPedidos.addColumn(pedido -> pedido.getItens().size()).setHeader("Qt. itens");
		gridPedidos.addColumn(pedido -> {

			// TODO: Implementar cálculo do valor total dos itens do pedido e formatá-lo no
			// padrão brasileiro (1.000,00)
			BigDecimal valorTotal = BigDecimal.ZERO;
			for (PedidoItem pedidoItem : pedido.getItens()) {
				valorTotal = valorTotal.add((pedidoItem.getProduto().getValorUnitario()
						.multiply(BigDecimal.valueOf(pedidoItem.getQuantidade()))));
			}

			return T3WUtils.formataValor(valorTotal);
		}).setHeader("Valor total");

		final var btnAdicionar = new T3WButton("Adicionar").themeTertiaryInline().themeSmall()
				.withClassName("grid-actions").withClickListener(ev -> new PedidoDialog(new Pedido(), pedidoService,
						pedidoItemService, pessoaService, produtoService, consumerProduto -> {
							// TODO: Incluir pedido adicionado
							pedidoService.save(consumerProduto);
							gridPedidos.setItems(pedidoService.findPedidos());
						}).open());

		gridPedidos.addColumn(new ComponentRenderer<>(pedido -> {
			final var btnEditar = new T3WButton("Editar").themeTertiaryInline().themeSmall()
					.withClickListener(ev -> new PedidoDialog(pedido, pedidoService, pedidoItemService, pessoaService,
							produtoService, consumerProduto -> {
								// TODO: Atualizar pedido alterado
//						pedidoService.save(consumerProduto); o save ja foi feito dentro do PedidoDialog
								gridPedidos.setItems(pedidoService.findPedidos());

							}).open());

			final var btnRemover = new T3WButton("Excluir").themeTertiaryInline().themeError().themeSmall()
					.withClickListener(ev -> {
						// TODO: Remover pedido excluido
						pedidoService.deleteById(pedido.getId());
						gridPedidos.getListDataView().removeItem(pedido);
					});
			return new VDiv(btnEditar, btnRemover).withClassName("grid-actions");
		})).setHeader(btnAdicionar);

		final var options = new VFormLayout().withFullWidth();

		this.add(options, gridPedidos);
	}

	@Override
	public void beforeEnter(final BeforeEnterEvent beforeEnterEvent) {
		// TODO: Adicionar itens ao grid
		gridPedidos.setItems(pedidoService.findPedidos());

	}
}