package io.t3w.desafio.views.pessoa;

import io.t3w.desafio.components.T3WBinder;
import io.t3w.desafio.components.T3WButton;
import io.t3w.desafio.components.T3WFormLayout;
import io.t3w.desafio.data.entity.Pessoa;
import io.t3w.desafio.services.PessoaService;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.textfield.VTextField;

import java.util.function.Consumer;

public class PessoaDialog extends VDialog {

    public PessoaDialog(final Pessoa pessoa, final PessoaService pessoaService, final Consumer<Pessoa> consumer) {
        setHeaderTitle("Pessoa");

        final var form = new T3WFormLayout();
        this.add(form);
        final var binder = new T3WBinder<>(pessoa);

        final var tfID = new VTextField("ID").withReadOnly(true);
        binder.forField(tfID).bindReadOnly(p -> String.valueOf(p.getId()));
        if(tfID.getValue().equals("0")) {
        	tfID.setVisible(false);
        }
        form.add(tfID);

        final var tfCPF = new VTextField("CPF");
        binder.forField(tfCPF).bind(Pessoa::getCpf, Pessoa::setCpf);
        form.add(tfCPF);

        final var tfNome = new VTextField("Nome");
        binder.forField(tfNome).bind(Pessoa::getNome, Pessoa::setNome);
        form.add(tfNome, 2);

        final var btnSalvar = new T3WButton("Salvar").themePrimary()
            .withClickListener(ev -> {
                final var pessoaSalva = pessoaService.save(binder.getObject());
                consumer.accept(pessoaSalva);
                this.close();
            });

        final var btnCancelar = new VButton("Cancelar").withClickListener(ev -> this.close());

        getFooter().add(new VDiv(btnSalvar, btnCancelar).withClassName("dialog-footer"));
    }


}
