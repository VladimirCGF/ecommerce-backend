-- -- States
INSERT INTO state (abbreviations, name) VALUES ('AC', 'Acre');
INSERT INTO state (abbreviations, name) VALUES ('AL', 'Alagoas');
INSERT INTO state (abbreviations, name) VALUES ('AP', 'Amapá');
INSERT INTO state (abbreviations, name) VALUES ('AM', 'Amazonas');
INSERT INTO state (abbreviations, name) VALUES ('BA', 'Bahia');
INSERT INTO state (abbreviations, name) VALUES ('CE', 'Ceará');
INSERT INTO state (abbreviations, name) VALUES ('DF', 'Distrito Federal');
INSERT INTO state (abbreviations, name) VALUES ('ES', 'Espírito Santo');
INSERT INTO state (abbreviations, name) VALUES ('GO', 'Goiás');
INSERT INTO state (abbreviations, name) VALUES ('MA', 'Maranhão');
INSERT INTO state (abbreviations, name) VALUES ('MT', 'Mato Grosso');
INSERT INTO state (abbreviations, name) VALUES ('MS', 'Mato Grosso do Sul');
INSERT INTO state (abbreviations, name) VALUES ('MG', 'Minas Gerais');
INSERT INTO state (abbreviations, name) VALUES ('PA', 'Pará');
INSERT INTO state (abbreviations, name) VALUES ('PB', 'Paraíba');
INSERT INTO state (abbreviations, name) VALUES ('PR', 'Paraná');
INSERT INTO state (abbreviations, name) VALUES ('PE', 'Pernambuco');
INSERT INTO state (abbreviations, name) VALUES ('PI', 'Piauí');
INSERT INTO state (abbreviations, name) VALUES ('RJ', 'Rio de Janeiro');
INSERT INTO state (abbreviations, name) VALUES ('RN', 'Rio Grande do Norte');
INSERT INTO state (abbreviations, name) VALUES ('RS', 'Rio Grande do Sul');
INSERT INTO state (abbreviations, name) VALUES ('RO', 'Rondônia');
INSERT INTO state (abbreviations, name) VALUES ('RR', 'Roraima');
INSERT INTO state (abbreviations, name) VALUES ('SC', 'Santa Catarina');
INSERT INTO state (abbreviations, name) VALUES ('SP', 'São Paulo');
INSERT INTO state (abbreviations, name) VALUES ('SE', 'Sergipe');
INSERT INTO state (abbreviations, name) VALUES ('TO', 'Tocantins');

-- -- Municipalities

INSERT INTO municipality (state_id, name) VALUES (1, 'Rio Branco');
INSERT INTO municipality (state_id, name) VALUES (2, 'Maceió');
INSERT INTO municipality (state_id, name) VALUES (3, 'Macapá');
INSERT INTO municipality (state_id, name) VALUES (4, 'Manaus');
INSERT INTO municipality (state_id, name) VALUES (5, 'Salvador');
INSERT INTO municipality (state_id, name) VALUES (6, 'Fortaleza');
INSERT INTO municipality (state_id, name) VALUES (7, 'Brasília');
INSERT INTO municipality (state_id, name) VALUES (8, 'Vitória');
INSERT INTO municipality (state_id, name) VALUES (9, 'Goiânia');
INSERT INTO municipality (state_id, name) VALUES (10, 'São Luís');
INSERT INTO municipality (state_id, name) VALUES (11, 'Cuiabá');
INSERT INTO municipality (state_id, name) VALUES (12, 'Campo Grande');
INSERT INTO municipality (state_id, name) VALUES (13, 'Belo Horizonte');
INSERT INTO municipality (state_id, name) VALUES (14, 'Belém');
INSERT INTO municipality (state_id, name) VALUES (15, 'João Pessoa');
INSERT INTO municipality (state_id, name) VALUES (16, 'Curitiba');
INSERT INTO municipality (state_id, name) VALUES (17, 'Recife');
INSERT INTO municipality (state_id, name) VALUES (18, 'Teresina');
INSERT INTO municipality (state_id, name) VALUES (19, 'Rio de Janeiro');
INSERT INTO municipality (state_id, name) VALUES (20, 'Natal');
INSERT INTO municipality (state_id, name) VALUES (21, 'Porto Alegre');
INSERT INTO municipality (state_id, name) VALUES (22, 'Porto Velho');
INSERT INTO municipality (state_id, name) VALUES (23, 'Boa Vista');
INSERT INTO municipality (state_id, name) VALUES (24, 'Florianópolis');
INSERT INTO municipality (state_id, name) VALUES (25, 'São Paulo');
INSERT INTO municipality (state_id, name) VALUES (26, 'Aracaju');
INSERT INTO municipality (state_id, name) VALUES (27, 'Palmas');

-- -- Users
INSERT INTO users (email, password, role)
VALUES ('Admin', '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 1);

INSERT INTO users (email, password, role)
VALUES ('Client', '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 3);

-- -- Client
INSERT INTO employee (firstName, lastName, user_id)
VALUES ('Vladimir', 'Coutinho',  1);

-- -- Client
INSERT INTO client (firstName, lastName, cpf, user_id)
VALUES ('Bob', 'Silva', '123.456.789-00', 2);

-- Address
INSERT INTO address (address, state_id, municipality_id, cep, client_id)
VALUES ('307 SUL', 27, 27, '77015-468', 1);

-- -- Watch
INSERT INTO watch (name, description, price, material, color, gender, brand, format, mechanism, imagePerfil)
VALUES ('Relógio XYZ',
        'Relógio moderno com design elegante',
        300.00,
        'Aço inoxidável',
        'Prata',
        'Masculino',
        'Marca ABC',
        'Redondo',
        'Automático', 'perfil.jpg');

INSERT INTO watch (name, description, price, material, color, gender, brand, format, mechanism, imagePerfil)
VALUES ('Relógio',
        'Relógio moderno com design elegante',
        100.00,
        'Aço inoxidável',
        'Prata',
        'Masculino',
        'Marca ABC',
        'Redondo',
        'Automático', 'perfil.jpg');

-- -- Stock
INSERT INTO stock (watch_id, quantity)
VALUES (1, 50);
INSERT INTO stock (watch_id, quantity)
VALUES (2, 50);

-- Coupon
INSERT INTO coupon (code, discountPercentage, validUntil)
VALUES ('10COUPON', 10, '2025-03-10');

-- -- Orders
INSERT INTO orders (orderDate, totalPrice, status, client_id, address_id)
VALUES ('2024-09-22 08:46:46.159923', 0, 2, 1, 1);

