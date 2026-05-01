CREATE DATABASE IF NOT EXISTS test;
USE test;

CREATE TABLE IF NOT EXISTS members (
                                       membername VARCHAR(50) NOT NULL PRIMARY KEY,
    passwd VARCHAR(68) NOT NULL,
    active TINYINT NOT NULL
    );

CREATE TABLE IF NOT EXISTS roles (
                                     membername VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    UNIQUE INDEX ix_auth_membername (membername, role),
    CONSTRAINT fk_authorities_members
    FOREIGN KEY (membername)
    REFERENCES members(membername)
    );

INSERT INTO members VALUES
                        ('sylvester', '{bcrypt}$2a$12$0Hk9c0JhKpccjikRS7R1GepUKwIUQHQdiuN84JTbhRnrdfjop11H6', 1),
                        ('tom', '{bcrypt}$2a$12$0Hk9c0JhKpccjikRS7R1GepUKwIUQHQdiuN84JTbhRnrdfjop11H6', 1),
                        ('arnold', '{bcrypt}$2a$12$0Hk9c0JhKpccjikRS7R1GepUKwIUQHQdiuN84JTbhRnrdfjop11H6', 1);

INSERT INTO roles VALUES
                      ('sylvester', 'ROLE_STUDENT'),
                      ('tom', 'ROLE_TEACHER'),
                      ('arnold', 'ROLE_TEACHER'),
                      ('arnold', 'ROLE_ADMIN');