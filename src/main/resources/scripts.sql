use cardapio;

create table item_cardapio (
                               id bigint PRIMARY KEY AUTO_INCREMENT,
                               nome varchar(100) NOT NULL,
                               descricao varchar(1000),
                               categoria ENUM('ENTRADAS', 'PRATOS_PRINCIPAIS', 'BEBIDAS','SOBREMESA') NOT NULL,
                               preco DECIMAL(9,2) NOT NULL,
                               preco_promocional DECIMAL(9,2)
);


SELECT * FROM item_cardapio ic

select count(*) from item_cardapio ic


INSERT INTO item_cardapio (nome, descricao, categoria, preco, preco_promocional)
VALUES ('Refresco do Chvaes', 'Suco de limão que parece tamarindo e tem gosto de groselha.', 'BEBIDAS', 2.99, null),
       ('Sanduíche de Presunto', 'O clássico sanduíche de presunto do Chaves, simples e saboroso', 'PRATOS_PRINCIPAIS', 5.50, null),
       ('Churros da Dona Florinda', 'Churros crocantes recheados com doce de leite cremoso', 'SOBREMESA', 4.75, null),
       ('Suco de Tamarindo', 'Suco de tamarindo bem gelado, especial da Vila', 'BEBIDAS', 3.25, null),
       ('Torta de Jamón', 'Farta fatia de torta salgada recheada com jamón e queijo', 'SOBREMESA', 8.90, null);