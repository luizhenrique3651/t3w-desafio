CREATE TABLE IF NOT EXISTS  pessoa
(
    id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cpf  VARCHAR(11)  NOT NULL,
    nome VARCHAR(250) NOT NULL
);
