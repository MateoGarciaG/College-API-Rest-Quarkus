-- First the Tables whose depends on other Tables, it mean that have FK in it
DROP TABLE IF EXISTS Enrollment;
-- Last Tables that doesn't have FK
DROP TABLE IF EXISTS Tuition;
DROP TABLE IF EXISTS Student;


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
    id BIGINT NOT NULL UNIQUE,
    status_t BOOLEAN NOT NULL,
    date_apply DATE NOT NULL,
    amount NUMERIC(8,3) NOT NULL,
    PRIMARY KEY(id)
);
INSERT INTO Tuition (id, status_t, date_apply, amount)
VALUES (134, FALSE, '2017-09-24', 500.78);
INSERT INTO Tuition (id, status_t, date_apply, amount)
VALUES (135, TRUE, '2018-10-10', 600.80);


-- Table Tuition definition without Enrollment Table, student_id FK is in Tuition

-- CREATE TABLE Tuition
-- (
--     id BIGINT(20) NOT NULL UNIQUE,
--     status BOOLEAN NOT NULL,
--     date_apply DATE NOT NULL,
--     amount NUMERIC(0,5) NOT NULL,
--     id_student INTEGER,
--     PRIMARY KEY (id),
--     CONSTRAINT `fk_student_tuition`
--         FOREIGN KEY (id_student) REFERENCES Student(id)
--         ON DELETE SET NULL
--         ON UPDATE SET NULL
-- );
-- INSERT INTO Tuition (id, status, date_apply, amount, id_student)
-- VALUES (134, FALSE, '2017-09-24', 500.78,  1050);
-- INSERT INTO Tuition (id, status, date_apply, amount, id_student)
-- VALUES (135, TRUE, '2018-10-10', 600.80, 2050);


-- Enrollment Table Definition


CREATE TABLE Enrollment(
    id SERIAL NOT NULL,
    id_student INTEGER,
    id_tuition BIGINT,
    -- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    CONSTRAINT fk_student_enrollment
        FOREIGN KEY (id_student) REFERENCES Student(id)
        ON DELETE SET NULL
        ON UPDATE SET NULL,
    CONSTRAINT fk_tuition_enrollment
        FOREIGN KEY (id_tuition) REFERENCES Tuition(id)
        ON DELETE SET NULL
        ON UPDATE SET NULL
);


INSERT INTO Enrollment (id, id_student, id_tuition)
VALUES (89, 1050, 134);
INSERT INTO Enrollment (id, id_student, id_tuition)
VALUES (90, 2050, 135);