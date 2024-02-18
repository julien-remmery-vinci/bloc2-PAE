DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.users
(
    id_user       SERIAL PRIMARY KEY,
    lastname     VARCHAR(50)  NOT NULL,
    firstname    VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL,
    password      CHAR(60)     NOT NULL,
    phone_number  CHAR(13)     NOT NULL,
    register_date date         NOT NULL,
    role          CHAR(1)      NOT NULL
);