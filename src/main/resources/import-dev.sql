DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Tuition;

CREATE TABLE Student
(
    id SERIAL NOT NULL,
    name VARCHAR (255) NOT NULL,
    surname VARCHAR(255),
    date_birth DATE NOT NULL,
    -- VARCHAR(22) because the number must be indicate the country. example: +34 and the limit is 22 characters
    phone VARCHAR(22),
    PRIMARY KEY (id)
);
INSERT INTO Student (id, name, surname, date_birth, phone)
VALUES (1050, 'Mateo', 'Alvarez', '2005-06-05', '+34 666666666');
INSERT INTO Student (id, name, surname, date_birth, phone)
VALUES (2050, 'Will', 'Smith', '1999-06-17', '+34 677878997');

-- Tuition Table definition

CREATE TABLE Tuition
(
    id BIGINT(20) NOT NULL UNIQUE,
    status BOOLEAN NOT NULL,
    date_apply DATE NOT NULL,
    amount NUMERIC(0,5) NOT NULL,
    id_student INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT `fk_student_tuition`
        FOREIGN KEY (id_student) REFERENCES Student(id)
        ON DELETE SET NULL
        ON UPDATE SET NULL
);
INSERT INTO Tuition (id, status, date_apply, amount, id_student)
VALUES (134, FALSE, '2017-09-24', 500.78,  1050);
INSERT INTO Tuition (id, status, date_apply, amount, id_student)
VALUES (135, TRUE, '2018-10-10', 600.80, 2050);