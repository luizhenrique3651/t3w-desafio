CREATE TABLE IF NOT EXISTS  pedido (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pessoa_id INTEGER NOT NULL,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);