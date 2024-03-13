DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.users
(
    idUser       SERIAL PRIMARY KEY,
    lastname     TEXT NOT NULL,
    firstname    TEXT NOT NULL,
    email        TEXT NOT NULL,
    password     TEXT NOT NULL,
    phoneNumber  TEXT NOT NULL,
    registerDate date NOT NULL,
    role         TEXT NOT NULL
);

CREATE TABLE pae.companies
(
    idCompany           SERIAL PRIMARY KEY,
    tradeName           TEXT    NOT NULL,
    designation         TEXT    NULL,
    address             TEXT    NOT NULL,
    phoneNumber         TEXT    NULL,
    email               TEXT    NULL,
    blacklisted         BOOLEAN NOT NULL,
    blacklistMotivation TEXT    NULL
);

CREATE TABLE pae.contacts
(
    idContact     SERIAL PRIMARY KEY,
    company       INTEGER REFERENCES pae.companies (idCompany) NOT NULL,
    student       INTEGER REFERENCES pae.users (iduser)        NOT NULL,
    state         TEXT                                         NOT NULL,
    meetPlace     TEXT                                         NULL,
    refusalReason TEXT                                         NULL,
    academicYear  TEXT                                         NOT NULL
);

INSERT INTO pae.users (lastname, firstname, email, password, phoneNumber, registerDate, role)
VALUES ('admin', 'admin', 'admin.admin@vinci.be', '$2a$10$aqGDWyP8K9xeO1a8uPzcWuzf1hfiPU8IlAR8GyhgVIYhnuKNSxP7S',
        '0123456789', '2024-02-22', 'A');

-- mot de passe : test
INSERT INTO pae.users (lastname, firstname, email, password, phonenumber, registerdate, role)
VALUES ('remmery', 'julien', 'julien.remmery@student.vinci.be', '$2a$10$Y3/IINNLQjpg33uhU2xhce9L5BUxyQ/ABXh35ftx4pan00lBgL8qm',
        '0123456789', '2024-01-01', 'E');

INSERT INTO pae.companies (tradeName, address, blacklisted)
VALUES ('Vinci', 'Bruxelles', false);

INSERT INTO pae.companies (tradeName, address, blacklisted)
VALUES ('Idealis Consulting', 'Mont saint guibert', false);

INSERT INTO pae.contacts (company, student, state, academicYear) VALUES (1, 2, 'initié', '2023-2024');
INSERT INTO pae.contacts (company, student, state, academicYear) VALUES (2, 2, 'pris', '2023-2024');