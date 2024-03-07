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
    designation         TEXT    NOT NULL,
    address             TEXT    NOT NULL,
    phoneNumber         TEXT    NOT NULL,
    email               TEXT    NOT NULL,
    blacklisted         BOOLEAN NOT NULL,
    blacklistMotivation TEXT    NOT NULL
);

CREATE TABLE pae.contacts
(
    idContact     SERIAL PRIMARY KEY,
    company       INTEGER REFERENCES pae.companies (idCompany) NOT NULL,
    student       INTEGER REFERENCES pae.users (iduser)        NOT NULL,
    state         TEXT                                         NOT NULL,
    meetPlace     TEXT                                         NOT NULL,
    refusalReason TEXT                                         NOT NULL,
    academicYear  TEXT                                         NOT NULL
);

INSERT INTO pae.users (lastname, firstname, email, password, phoneNumber, registerDate, role)
VALUES ('admin', 'admin', 'admin.admin@vinci.be', '$2a$10$aqGDWyP8K9xeO1a8uPzcWuzf1hfiPU8IlAR8GyhgVIYhnuKNSxP7S',
        '0123456789', '2024-02-22', 'A');