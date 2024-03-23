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
    role         TEXT NOT NULL,
    version      INTEGER NOT NULL
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
    blacklistMotivation TEXT    NULL,
    version            INTEGER NOT NULL
);

CREATE TABLE pae.contacts
(
    idContact     SERIAL PRIMARY KEY,
    idCompany       INTEGER REFERENCES pae.companies (idCompany) NOT NULL,
    idStudent       INTEGER REFERENCES pae.users (iduser)        NOT NULL,
    state         TEXT                                         NOT NULL,
    meetPlace     TEXT                                         NULL,
    refusalReason TEXT                                         NULL,
    academicYear  TEXT                                         NOT NULL,
    version       INTEGER                                      NOT NULL
);

CREATE TABLE pae.internshipSupervisor
(
    idInternshipSupervisor SERIAL PRIMARY KEY,
    lastname               TEXT                                         NOT NULL,
    firstname              TEXT                                         NOT NULL,
    phoneNumber            TEXT                                         NOT NULL,
    email                  TEXT                                         NULL,
    idCompany              INTEGER REFERENCES pae.companies (idCompany) NOT NULL,
    version               INTEGER                                      NOT NULL
);

CREATE TABLE pae.internships
(
    idInternship         SERIAL PRIMARY KEY,
    idStudent            INTEGER REFERENCES pae.contacts (idStudent) NOT NULL,
    internshipProject    TEXT                                        NULL,
    signatureDate        DATE                                        NOT NULL,
    idContact            INTEGER REFERENCES pae.contacts (idContact) NOT NULL,
    internshipSupervisor INTEGER REFERENCES pae.users (idUser)       NOT NULL,
    idCompany            INTEGER REFERENCES pae.contacts (idCompany) NOT NULL,
    version             INTEGER                                     NOT NULL
);

INSERT INTO pae.users (lastname, firstname, email, password, phoneNumber, registerDate, role)
VALUES ('admin', 'admin', 'admin.admin@vinci.be', '$2a$10$aqGDWyP8K9xeO1a8uPzcWuzf1hfiPU8IlAR8GyhgVIYhnuKNSxP7S',
        '0123456789', '2024-02-22', 'administratif');

-- mot de passe : test
INSERT INTO pae.users (lastname, firstname, email, password, phonenumber, registerdate, role)
VALUES ('remmery', 'julien', 'julien.remmery@student.vinci.be', '$2a$10$Y3/IINNLQjpg33uhU2xhce9L5BUxyQ/ABXh35ftx4pan00lBgL8qm',
        '0123456789', '2024-01-01', 'étudiant');

INSERT INTO pae.companies (tradeName, address, blacklisted)
VALUES ('Vinci', 'Bruxelles', false);

INSERT INTO pae.companies (tradeName, address, blacklisted)
VALUES ('Idealis Consulting', 'Mont saint guibert', false);

INSERT INTO pae.companies (tradeName, designation, address, blacklisted)
VALUES ('Sagacify', 'SRL', 'Bruxelles', false);

INSERT INTO pae.companies (tradeName, designation, address, blacklisted)
VALUES ('Sagacify', 'SPRL', 'Bruxelles', false);

INSERT INTO pae.contacts (idCompany, idStudent, state, academicYear) VALUES (1, 2, 'initié', '2023-2024');
INSERT INTO pae.contacts (idCompany, idStudent, state, academicYear) VALUES (2, 2, 'pris', '2023-2024');