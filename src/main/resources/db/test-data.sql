
insert into roleentity (id,role) values (0, 'CREATOR' );
insert into roleentity (id,role) values (1, 'ADMIN' );
insert into roleentity (id,role) values (2,'USER' );

insert into userentity (id, email, name, surname)values (1, 'aaa@.ss','Тестовый аккаунт','1');
insert into userentity (id, email, name, surname)values (2, 'bbb@.ss','Тестовый аккаунт','2');
insert into userentity (id, email, name, surname)values (3, 'ccc@.ss','Тестовый аккаунт','3');
insert into userentity (id, email, name, surname)values (4, 'ddd@.ss','Тестовый аккаунт','4');
insert into userentity (id, email, name, surname)values (5, 'eee@.ss','Тестовый аккаунт','5');

insert into boardentity (id,title) values (6,'board1');
insert into boardentity (id,title) values (7,'board2');
insert into boardentity (id,title) values (8,'board3');
insert into boardentity (id,title) values (9,'board4');
insert into boardentity (id,title) values (10,'board5');

insert into columnentity(id, position, title) values (11,1,'board1 –– column 1');
insert into columnentity(id, position, title) values (12,2,'board1 –– column 2');
insert into columnentity(id, position, title) values (13,3,'board1 –– column 3');
insert into columnentity(id, position, title) values (14,4,'board1 –– column 4');
insert into columnentity(id, position, title) values (15,5,'board1 –– column 5');

insert into columnentity(id, position, title) values (16,1,'board2 –– column 1');
insert into columnentity(id, position, title) values (17,2,'board2 –– column 2');
insert into columnentity(id, position, title) values (18,3,'board2 –– column 3');
insert into columnentity(id, position, title) values (19,4,'board2 –– column 4');
insert into columnentity(id, position, title) values (20,5,'board2 –– column 5');

insert into columnentity(id, position, title) values (21,1,'board3 –– column 1');
insert into columnentity(id, position, title) values (22,2,'board3 –– column 2');
insert into columnentity(id, position, title) values (23,3,'board3 –– column 3');
insert into columnentity(id, position, title) values (24,4,'board3 –– column 4');
insert into columnentity(id, position, title) values (25,5,'board3 –– column 5');

insert into columnentity(id, position, title) values (26,1,'board4 –– column 1');
insert into columnentity(id, position, title) values (27,2,'board4 –– column 2');
insert into columnentity(id, position, title) values (28,3,'board4 –– column 3');
insert into columnentity(id, position, title) values (29,4,'board4 –– column 4');
insert into columnentity(id, position, title) values (30,5,'board4 –– column 5');

insert into columnentity(id, position, title) values (31,1,'board5 –– column 1');
insert into columnentity(id, position, title) values (32,2,'board5 –– column 2');
insert into columnentity(id, position, title) values (33,3,'board5 –– column 3');
insert into columnentity(id, position, title) values (34,4,'board5 –– column 4');
insert into columnentity(id, position, title) values (35,5,'board5 –– column 5');

insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (6, 0, 1);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (7, 0, 1);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (8, 0, 1);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (9, 0, 1);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (10, 0, 1);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (6, 1, 2);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (7, 1, 2);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (8, 1, 2);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (9, 1, 2);
insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (10, 1, 2);

insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (6,11);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (6,12);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (6,13);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (6,14);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (6,15);

insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (7,16);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (7,17);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (7,18);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (7,19);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (7,20);

insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (8,21);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (8,22);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (8,23);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (8,24);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (8,25);

insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (9,26);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (9,27);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (9,28);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (9,29);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (9,30);

insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (10,31);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (10,32);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (10,33);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (10,34);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (10,35);

insert into taskentity(id, description, position, text) VALUES (36,'description',1,'b1_c1_task1');
insert into taskentity(id, description, position, text) VALUES (37,'description',2,'b1_c1_task2');
insert into taskentity(id, description, position, text) VALUES (38,'description',3,'b1_c1_task3');
insert into taskentity(id, description, position, text) VALUES (39,'description',4,'b1_c1_task4');

insert into taskentity(id, description, position, text) VALUES (40,'description',1,'b1_c2_task1');
insert into taskentity(id, description, position, text) VALUES (41,'description',2,'b1_c2_task2');
insert into taskentity(id, description, position, text) VALUES (42,'description',3,'b1_c2_task3');
insert into taskentity(id, description, position, text) VALUES (43,'description',4,'b1_c2_task4');

insert into taskentity(id, description, position, text) VALUES (44,'description',1,'b1_c3_task1');
insert into taskentity(id, description, position, text) VALUES (45,'description',2,'b1_c3_task2');
insert into taskentity(id, description, position, text) VALUES (46,'description',3,'b1_c3_task3');
insert into taskentity(id, description, position, text) VALUES (47,'description',4,'b1_c3_task4');

insert into taskentity(id, description, position, text) VALUES (48,'description',1,'b1_c4_task1');
insert into taskentity(id, description, position, text) VALUES (49,'description',2,'b1_c4_task2');
insert into taskentity(id, description, position, text) VALUES (50,'description',3,'b1_c4_task3');
insert into taskentity(id, description, position, text) VALUES (51,'description',4,'b1_c4_task4');

insert into taskentity(id, description, position, text) VALUES (52,'description',1,'b1_c5_task1');
insert into taskentity(id, description, position, text) VALUES (53,'description',2,'b1_c5_task2');
insert into taskentity(id, description, position, text) VALUES (54,'description',3,'b1_c5_task3');
insert into taskentity(id, description, position, text) VALUES (55,'description',4,'b1_c5_task4');

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,36);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,37);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,38);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,39);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (12,40);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (12,41);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (12,42);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (12,43);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (13,44);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (13,45);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (13,46);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (13,47);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (14,48);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (14,49);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (14,50);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (14,51);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (15,52);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (15,53);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (15,54);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (15,55);

---------
insert into taskentity(id, description, position, text) VALUES (56,'description',1,'b2_c1_task1');
insert into taskentity(id, description, position, text) VALUES (57,'description',2,'b2_c1_task2');
insert into taskentity(id, description, position, text) VALUES (58,'description',3,'b2_c1_task3');
insert into taskentity(id, description, position, text) VALUES (59,'description',4,'b2_c1_task4');

insert into taskentity(id, description, position, text) VALUES (60,'description',1,'b2_c2_task1');
insert into taskentity(id, description, position, text) VALUES (61,'description',2,'b2_c2_task2');
insert into taskentity(id, description, position, text) VALUES (62,'description',3,'b2_c2_task3');
insert into taskentity(id, description, position, text) VALUES (63,'description',4,'b2_c2_task4');

insert into taskentity(id, description, position, text) VALUES (64,'description',1,'b2_c3_task1');
insert into taskentity(id, description, position, text) VALUES (65,'description',2,'b2_c3_task2');
insert into taskentity(id, description, position, text) VALUES (66,'description',3,'b2_c3_task3');
insert into taskentity(id, description, position, text) VALUES (67,'description',4,'b2_c3_task4');

insert into taskentity(id, description, position, text) VALUES (68,'description',1,'b2_c4_task1');
insert into taskentity(id, description, position, text) VALUES (69,'description',2,'b2_c4_task2');
insert into taskentity(id, description, position, text) VALUES (70,'description',3,'b2_c4_task3');
insert into taskentity(id, description, position, text) VALUES (71,'description',4,'b2_c4_task4');

insert into taskentity(id, description, position, text) VALUES (72,'description',1,'b2_c5_task1');
insert into taskentity(id, description, position, text) VALUES (73,'description',2,'b2_c5_task2');
insert into taskentity(id, description, position, text) VALUES (74,'description',3,'b2_c5_task3');
insert into taskentity(id, description, position, text) VALUES (75,'description',4,'b2_c5_task4');

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (16,56);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (16,57);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (16,58);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (16,59);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (17,60);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (17,61);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (17,62);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (17,63);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (18,64);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (18,65);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (18,66);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (18,67);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (19,68);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (19,69);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (19,70);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (19,71);

insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (20,72);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (20,73);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (20,74);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (20,75);

----------------------
insert into taskentity_userentity(taskentity_id, makers_id) VALUES (36,1);

insert into tagentity(id, title, color) VALUES (76,'Analysis',0);
insert into tagentity(id, title, color) VALUES (77,'Frontend',1);
insert into tagentity(id, title, color) VALUES (78,'Backend',2);
insert into tagentity(id, title, color) VALUES (79,'Priority task',3);
insert into tagentity(id, title, color) VALUES (80,'Database',4);
insert into tagentity(id, title, color) VALUES (81,'Bugfix',0);
insert into tagentity(id, title, color) VALUES (82,'Testing',1);
insert into tagentity(id, title, color) VALUES (83,'Non-priority task',2);

insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,76);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,77);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,78);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,79);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,80);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,81);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,82);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (6,83);

insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,76);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,77);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,78);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,79);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,80);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,81);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,82);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (7,83);

insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (36,76);
insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (37,77);
insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (38,78);
insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (39,79);

insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (40,80);
insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (41,81);
insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (42,82);
insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (43,83);

-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');