package io.t3w.desafio.views.pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.combobox.VComboBox;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.textfield.VTextField;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import io.t3w.desafio.T3WUtils;
import io.t3w.desafio.components.T3WBinder;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WFormLayout;
import io.t3w.desafio.data.entity.Pedido;
import io.t3w.desafio.data.entity.PedidoItem;
import io.t3w.desafio.data.entity.Pessoa;
import io.t3w.desafio.services.PedidoItemService;
import io.t3w.desafio.services.PedidoService;
import io.t3w.desafio.services.PessoaService;
import io.t3w.desafio.services.ProdutoService;
import jakarta.persistence.EntityManager;

public class PedidoDialog extends VDialog {

	private static final long serialVersionUID = 1L;

	
	
	public PedidoDialog(final Pedido pedido, final PedidoService pedidoService, PedidoItemService pedidoItemService,
			PessoaService pessoaService, ProdutoService produtoService, final Consumer<Pedido> consumer) {
		setHeaderTitle("Pedido");
		setWidthFull();

		final var form = new T3WFormLayout();
		final var binder = new T3WBinder<>(pedido);

		// TODO: Nao renderize o id caso seja uma inclusao
		final var tfID = new VTextField("ID").withReadOnly(true);
		binder.forField(tfID).bindReadOnly(p -> String.valueOf(p.getId()));
		if (tfID.getValue().equals("0")) {
			tfID.setVisible(false);
		}
		form.add(tfID);

		// TODO: Preencher combobox com pessoas para o usuario selecionar
		final var cbPessoa = new VComboBox<Pessoa>("Pessoa");
		cbPessoa.setItems(pessoaService.findPessoas());

		binder.forField(cbPessoa).bind(Pedido::getPessoa, Pedido::setPessoa);

		cbPessoa.setItemLabelGenerator(Pessoa::getNome);
		form.add(cbPessoa, 2);

		final var gridItens = new VGrid<PedidoItem>().withThemeVariants(GridVariant.LUMO_NO_BORDER);
		gridItens.addColumn(PedidoItem::getId).setHeader("ID");
		gridItens.addColumn(p -> p.getProduto().getDescricao()).setHeader("Produto");
		gridItens.addColumn(PedidoItem::getQuantidade).setHeader("Quantidade");
		gridItens.addColumn(p -> {
			BigDecimal valor = p.getProduto().getValorUnitario().multiply(BigDecimal.valueOf(p.getQuantidade()));
		    return T3WUtils.formataValor(valor);
		})
				.setHeader("Valor");
		if (pedido.getItens() != null) {
			if (!pedido.getItens().isEmpty()) {
				gridItens.setItems(pedido.getItens());
			}
		}
		final var btnAdicionar = new T3WButton("Adicionar").themeTertiaryInline().themeSmall()
				.withClassName("grid-actions")
				.withClickListener(ev -> new PedidoItemDialog(new PedidoItem(), produtoService, c -> {
					// TODO: Adicionar pessoa ao grid
					if (pedido.getItens() != null) {
						pedido.getItens().add(c);
					} else {
						List<PedidoItem> listaItens = new ArrayList<>();
						listaItens.add(c);
						pedido.setItens(listaItens);
					}
					gridItens.setItems(pedido.getItens());
				}).open());

		// TODO: Abrir um Dialog para seleção do item e quantidade

		gridItens.addColumn(new ComponentRenderer<>(pedidoOnDialog -> {
			final var btnEditar = new T3WButton("Editar").themeTertiaryInline().themeSmall()
					.withClickListener(ev -> new PedidoItemDialog(pedidoOnDialog, produtoService, c -> {
						// TODO: Permita editar o item através do dialog de seleção
						pedidoOnDialog.setProduto(c.getProduto());
						pedidoOnDialog.setQuantidade(c.getQuantidade());
						pedidoOnDialog.setPedido(c.getPedido());
						pedidoItemService.save(pedidoOnDialog);
						gridItens.setItems(pedido.getItens());

					}).open());

			final var btnRemover = new T3WButton("Excluir").themeTertiaryInline().themeError().themeSmall()
					.withClickListener(ev -> {
						// TODO: Remova o item do grid
						pedidoItemService.deleteById(pedidoOnDialog.getId());
						pedido.getItens().removeIf(item -> item.getId() == pedidoOnDialog.getId());
						gridItens.setItems(pedido.getItens());
					});

			return new VDiv(btnEditar, btnRemover).withClassName("grid-actions");
		})).setHeader(btnAdicionar);

		final var btnSalvar = new T3WButton("Salvar").themePrimary().withClickListener(ev -> {
			// TODO: Salve o pedido
			pedido.setItens(new ArrayList<>(pedido.getItens()));
			final var pedidoSalvo = pedidoService.save(pedido);
			consumer.accept(pedidoSalvo);

			this.close();
		});

		this.add(form, gridItens);

		final var btnCancelar = new VButton("Cancelar").withClickListener(ev -> this.close());

		getFooter().add(new VDiv(btnSalvar, btnCancelar).withClassName("dialog-footer"));
	}

}
