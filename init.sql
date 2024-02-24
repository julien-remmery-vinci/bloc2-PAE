DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.users
(
    id_user       SERIAL PRIMARY KEY,
    lastname      TEXT   NOT NULL,
    firstname     TEXT   NOT NULL,
    email         TEXT   NOT NULL,
    password      TEXT   NOT NULL,
    phone_number  TEXT   NOT NULL,
    register_date date   NOT NULL,
    role          TEXT   NOT NULL
);

INSERT INTO pae.users (lastname, firstname, email, password, phone_number, register_date, role)
VALUES ('admin', 'admin', 'admin@vinci.be', '$2a$10$aqGDWyP8K9xeO1a8uPzcWuzf1hfiPU8IlAR8GyhgVIYhnuKNSxP7S', '0123456789', '2024-02-22', 'A');