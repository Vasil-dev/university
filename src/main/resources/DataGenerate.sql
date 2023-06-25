INSERT INTO cms.groups (group_name) VALUES ('Group A');
INSERT INTO cms.groups (group_name) VALUES ('Group B');
INSERT INTO cms.groups (group_name) VALUES ('Group C');

INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Vasil', 'Martsyniuk', 1);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Stepan', 'Stepanenko', 1);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Ivan', 'Ivanenko', 1);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Natalia', 'Martyniuk', 2);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Eddy', 'Merfy', 2);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Silvester', 'Stalone', 2);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Igor', 'Stethem', 3);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Ivan', 'Korol', 3);
INSERT INTO cms.students (first_name, last_name, student_group_id) VALUES ('Vasil', 'Boiko', 3);

INSERT INTO cms.teachers (first_name, last_name) VALUES ('Danylo', 'Bubnii');
INSERT INTO cms.teachers (first_name, last_name) VALUES ('Serhiy', 'Nemchinsky');

INSERT INTO cms.timetable (id, start, duration) VALUES (1, '2023-12-23 08:00:00', '1:00');
INSERT INTO cms.timetable (id, start, duration) VALUES (2, '2023-12-23 09:00:00', '1:00');
INSERT INTO cms.timetable (id, start, duration) VALUES (3, '2023-12-23 10:00:00', '1:00');
INSERT INTO cms.timetable (id, start, duration) VALUES (4, '2023-12-23 11:00:00', '1:00');
INSERT INTO cms.timetable (id, start, duration) VALUES (5, '2023-12-23 12:00:00', '1:00');

INSERT INTO cms.lecture (id, lecture_name, lecture_teacher_id, lecture_group_id,  lecture_time_id) VALUES (1, 'Math', 1, 3, 1);
INSERT INTO cms.lecture (id, lecture_name, lecture_teacher_id, lecture_group_id, lecture_time_id) VALUES (2, 'Biology', 2, 2,2);
INSERT INTO cms.lecture (id, lecture_name, lecture_teacher_id, lecture_group_id, lecture_time_id) VALUES (3, 'Programing', 1, 3, 3);
INSERT INTO cms.lecture (id, lecture_name, lecture_teacher_id, lecture_group_id, lecture_time_id) VALUES (4, 'History', 2, 1, 4);
INSERT INTO cms.lecture (id, lecture_name, lecture_teacher_id, lecture_group_id, lecture_time_id) VALUES (5, 'Chemistry', 1, 1, 5);

INSERT INTO cms.roles (id, name) VALUES (1,'ADMIN');
INSERT INTO cms.roles (id, name) VALUES (2,'USER');

INSERT INTO cms.user_roles (user_id, role_id) VALUES (1,1);

INSERT INTO cms.users (id, user_name, email, password) VALUES (1,'admin', 'qwerty', 'admin');
