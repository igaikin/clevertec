--DROP TABLE IF EXISTS cards;
--DROP TABLE IF EXISTS products;

CREATE DATABASE clevertec_check;

CREATE TABLE IF NOT EXISTS cards
(
    id       BIGSERIAL PRIMARY KEY,
    discount DECIMAL(4, 2)
);

CREATE TABLE IF NOT EXISTS products
(
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255)  NOT NULL,
    cost        DECIMAL(8, 2) NOT NULL,
    promo       BOOLEAN       NOT NULL DEFAULT FALSE
);