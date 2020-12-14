DROP TABLE IF EXISTS student;

CREATE TABLE student
(
    student_id          int(20)       not null,
    surname             varchar(18)   not null,
    name                varchar(17)   not null
);

DROP TABLE IF EXISTS lecturer;

CREATE TABLE lecturer
(
    lecturer_id          int(20)       not null,
    surname             varchar(18)   not null,
    name                varchar(17)   not null
);

