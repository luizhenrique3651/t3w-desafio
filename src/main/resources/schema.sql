CREATE TABLE pessoa
(
    id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cpf  VARCHAR(11)  NOT NULL,
    nome VARCHAR(250) NOT NULL
);

CREATE TABLE produto
(
    id             INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    descricao      VARCHAR(50)    NOT NULL,
    valor_unitario NUMERIC(10, 2) NOT NULL
);


CREATE TABLE pedido (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pessoa_id INTEGER NOT NULL,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);

CREATE TABLE pedido_item (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    produto_id INTEGER NOT NULL,
    pedido_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

CREATE INDEX idx_pedido_pessoa ON pedido(pessoa_id);
CREATE INDEX idx_pedido_item_produto ON pedido_item(produto_id);
CREATE INDEX idx_pedido_item_pedido ON pedido_item(pedido_id);
