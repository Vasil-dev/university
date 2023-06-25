DROP SCHEMA IF EXISTS cms CASCADE ;
CREATE SCHEMA cms;
DROP TABLE IF EXISTS cms.groups CASCADE;
CREATE TABLE cms.groups (
                        id SERIAL PRIMARY KEY,
                        group_name VARCHAR NOT NULL
);

DROP TABLE IF EXISTS cms.teachers CASCADE;
CREATE TABLE cms.teachers (
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR NOT NULL,
                         last_name VARCHAR NOT NULL
);

DROP TABLE IF EXISTS cms.timetable CASCADE;
CREATE TABLE cms.timetable (
                               id SERIAL PRIMARY KEY ,
                               start TIMESTAMP,
                               duration INTERVAL

);

DROP TABLE IF EXISTS cms.students CASCADE;
CREATE TABLE cms.students (
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR NOT NULL,
                         last_name VARCHAR NOT NULL,
                         student_group_id INT,

                         FOREIGN KEY (student_group_id) REFERENCES cms.groups(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS cms.lecture CASCADE;
CREATE TABLE cms.lecture (
                         id SERIAL PRIMARY KEY,
                         lecture_name VARCHAR NOT NULL,
                         lecture_teacher_id INT,
                         lecture_group_id INT,
                         lecture_student_id INT,
                         lecture_time_id INT,

                         FOREIGN KEY (lecture_teacher_id) REFERENCES cms.teachers(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (lecture_group_id) REFERENCES cms.groups(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (lecture_student_id) REFERENCES cms.students(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (lecture_time_id) REFERENCES cms.timetable(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS cms.roles CASCADE;
CREATE TABLE cms.roles (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR NOT NULL
);
DROP TABLE IF EXISTS cms.users CASCADE;
CREATE TABLE cms.users (
                           id SERIAL PRIMARY KEY,
                           user_name VARCHAR NOT NULL,
                           email VARCHAR NOT NULL,
                           password VARCHAR NOT NULL
);
DROP TABLE IF EXISTS cms.user_roles CASCADE;
CREATE TABLE cms.user_roles (
                           user_id SERIAL PRIMARY KEY,
                           role_id INT,

                           FOREIGN KEY (user_id) REFERENCES cms.users(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (role_id) REFERENCES cms.roles(id) ON DELETE CASCADE ON UPDATE CASCADE
);