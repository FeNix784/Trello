insert into roleentity (id,role) values (0, 'CREATOR' );
insert into roleentity (id,role) values (1, 'ADMIN' );
insert into roleentity (id,role) values (2,'USER' );

insert into userentity (id, email, name, surname)values (1, 'eee@.ss','Никита','Литвинков');
insert into userentity (id,  email, name, surname)values (2, 'ee2e@.ss', 'Папаша','Карло');

insert into boardentity (id,title) values (3,'board1');
insert into boardentity (id,title) values (4,'board2');
insert into boardentity (id,title) values (5,'board3');

insert into columnentity(id, position, title) values (6,1,'ANALYSIS (АНАЛИЗ)');
insert into columnentity(id, position, title) values (7,2,'TO DO (К ВЫПОЛНЕНИЮ)');
insert into columnentity(id, position, title) values (8,3,'IN PROGRESS (ВЫПОЛНЯЕТСЯ)');
insert into columnentity(id, position, title) values (9,4,'REVIEW (ПРОВЕРКА)');
insert into columnentity(id, position, title) values (10,5,'DONE (ВЫПОЛНЕНО)');
insert into columnentity(id, position, title) values (11,6,'TESTS (ТЕСТЫ)');


insert into boardentity_roleentity(boardentity_id, usersroles_id, usersroles_key) values (3, 0, 1);

insert into columnentity(id, position, title) values (12,2,'col1');
insert into columnentity(id, position, title) values (13,3,'col1');

insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (3,12);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (4,13);


insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (3,6);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (3,7);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (3,8);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (3,9);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (3,10);
insert into boardentity_columnentity(boardentity_id, columns_id) VALUES (3,11);


insert into taskentity(id, description, position, text) VALUES (14,'etogiswsdfmgimhr',1,'Выявление новых полей сущностей');
insert into taskentity(id, description, position, text) VALUES (15,'lzpurvgrosvxmygs',2,'Определение типа запроса и обрабатывающего его контроллера для прикрепления тега задаче');
insert into taskentity(id, description, position, text) VALUES (16,'tzcjaijdqedaddbi',3,'Определение приходящих данных при drag’n’drop’e колонки и задачи');
insert into taskentity(id, description, position, text) VALUES (17,'jzkkwcehbjhriohj',4,'Определение способа вычисления времени пребывания задачи в колонке, для статистики выполнения');
insert into taskentity(id, description, position, text) VALUES (18,'vszyccezzpfsfqal',5,'Определение формата передачи даты и времени');
insert into taskentity(id, description, position, text) VALUES (19,'optsufgyhfzhkdyy',6,'Добавление поля даты создания к TaskEntity');
insert into taskentity(id, description, position, text) VALUES (20,'tlprxkuqzskuqqyr',7,'Уйти от Date типа данных в пользу Long');
insert into taskentity(id, description, position, text) VALUES (21,'hzcgwcmwsdmvktzp',8,'Парсинг нашей доски в JSON');
insert into taskentity(id, description, position, text) VALUES (22,'xptshdmjskbbhdag',9,'ервичная верстка/макет главной страницы');
insert into taskentity(id, description, position, text) VALUES (23,'ykcoggzovsbthhdo',10,'Авторизация\Регистрация\Аутентификация\OAuth 2.0');
insert into taskentity(id, description, position, text) VALUES (24,'kbpzznfwvrlrgcew',11,'TaskController | Добавление системных комментариев при совершении действий над задачей');
insert into taskentity(id, description, position, text) VALUES (25,'fbybibmyfawkfhfm',12,'Реализовать ссылку приглашение в доску');
insert into taskentity(id, description, position, text) VALUES (26,'elinkjylyihtnjgp',13,'Реализация чата');
insert into taskentity(id, description, position, text) VALUES (27,'kwrjlyureyfcmorr',14,'ColumnEntity добавить поле очередности (порядка) на доске');
insert into taskentity(id, description, position, text) VALUES (28,'ylmxjlgvfvcnmfyk',15,'TaskController | Реализовать удаление тегов и пользователей у задачи');
insert into taskentity(id, description, position, text) VALUES (29,'przsdmsjfysyhopm',16,'Удаление  UsersBoardsRolesEntity, с последующим изменением затрагиваемых ей областей');
insert into taskentity(id, description, position, text) VALUES (30,'gdxfdqtjiqjqnamt',17,'BoardController | Реализовать удаление пользователя из доски');
insert into taskentity(id, description, position, text) VALUES (31,'glgooqciqbrhrrrp',18,'Рефакторинг кода TagController-a');
insert into taskentity(id, description, position, text) VALUES (32,'kwjvlqwytsfbwudi',19,'еализация TagEntityController-a');
insert into taskentity(id, description, position, text) VALUES (33,'zjkkobovvhjltlzq',20,'Создать модель БД');
insert into taskentity(id, description, position, text) VALUES (34,'qgzvbsgqwqhuysbp',21,'TaskController | Реализация перемещения задачи в другую колонку');
insert into taskentity(id, description, position, text) VALUES (35,'dmiqzkcbhmtqrkcm',22,'Написание тестов для BoardController-а');
insert into taskentity(id, description, position, text) VALUES (36,'vrafwxtbummlnisv',23,'Написание тестов для ColumnController-а');
insert into taskentity(id, description, position, text) VALUES (37,'viizkwlgmzstzipk',24,'Написание тестов для TaskController-а');
insert into taskentity(id, description, position, text) VALUES (38,'rzbejjwzjrehoqhd',25,'Написание тестов для TagController-а');
insert into taskentity(id, description, position, text) VALUES (39,'apvdtrfipvlaankt',26,'Написание тестов для UserController-а');
insert into taskentity(id, description, position, text) VALUES (40,'pdsydudoefaznzad',27,'Написание тестов для CommentController-а');
insert into taskentity(id, description, position, text) VALUES (41,'fpgfmawxvoagfyzz',1,'Добавление поля даты создания к TaskEntity');
insert into taskentity(id, description, position, text) VALUES (42,'cnbpqffgqtoklrsd',2,'Уйти от Date типа данных в пользу Long');
insert into taskentity(id, description, position, text) VALUES (43,'jjybyffiazcuasca',3,'Парсинг нашей доски в JSON');
insert into taskentity(id, description, position, text) VALUES (44,'ugemdwxujufobqrl',4,'ервичная верстка/макет главной страницы');
insert into taskentity(id, description, position, text) VALUES (45,'nahhcfbohheqmtmv',5,'Авторизация\Регистрация\Аутентификация\OAuth 2.0');
insert into taskentity(id, description, position, text) VALUES (46,'tgkqshbxlnudvcjz',6,'TaskController | Добавление системных комментариев при совершении действий над задачей');
insert into taskentity(id, description, position, text) VALUES (47,'pngashdsugooybhp',7,'Реализовать ссылку приглашение в доску');
insert into taskentity(id, description, position, text) VALUES (48,'uvdlwnphwrympixv',8,'Реализация чата');
insert into taskentity(id, description, position, text) VALUES (49,'wkhteunhraapjshw',9,'ColumnEntity добавить поле очередности (порядка) на доске');
insert into taskentity(id, description, position, text) VALUES (50,'kwunvictvcoeqdob',10,'TaskController | Реализовать удаление тегов и пользователей у задачи');
insert into taskentity(id, description, position, text) VALUES (51,'jkctzonhpxfsejdd',11,'Удаление  UsersBoardsRolesEntity, с последующим изменением затрагиваемых ей областей');
insert into taskentity(id, description, position, text) VALUES (52,'wvzhejizhkimvlqp',12,'BoardController | Реализовать удаление пользователя из доски');
insert into taskentity(id, description, position, text) VALUES (53,'wvlhcmqcqxrjtptl',13,'Рефакторинг кода TagController-a');
insert into taskentity(id, description, position, text) VALUES (54,'hrwxiioeajplwtjv',14,'еализация TagEntityController-a');
insert into taskentity(id, description, position, text) VALUES (55,'tddezmffuqepojsa',15,'Создать модель БД');
insert into taskentity(id, description, position, text) VALUES (56,'mjhwsjalitoxkmpq',16,'TaskController | Реализация перемещения задачи в другую колонку');
insert into taskentity(id, description, position, text) VALUES (57,'uxkwyorimwdrcowp',17,'Написание тестов для BoardController-а');
insert into taskentity(id, description, position, text) VALUES (58,'drotruobgrbchbdr',18,'Написание тестов для ColumnController-а');
insert into taskentity(id, description, position, text) VALUES (59,'gocvkchsezmzpiqy',19,'Написание тестов для TaskController-а');
insert into taskentity(id, description, position, text) VALUES (60,'uwfdaymaslcmauox',20,'Написание тестов для TagController-а');
insert into taskentity(id, description, position, text) VALUES (61,'ttyrttuehaaypnbb',21,'Написание тестов для UserController-а');
insert into taskentity(id, description, position, text) VALUES (62,'yivalkfgasxydjcr',22,'Написание тестов для CommentController-а');
insert into taskentity(id, description, position, text) VALUES (63,'mkuouwonelkulwwe',1,'ервичная верстка/макет главной страницы');
insert into taskentity(id, description, position, text) VALUES (64,'fzxudgdmjfinubai',2,'Авторизация\Регистрация\Аутентификация\OAuth 2.0');
insert into taskentity(id, description, position, text) VALUES (65,'aozxvkeasckrzflj',3,'TaskController | Добавление системных комментариев при совершении действий над задачей');
insert into taskentity(id, description, position, text) VALUES (66,'uahtmzmfykvugdyj',4,'Реализовать ссылку приглашение в доску');
insert into taskentity(id, description, position, text) VALUES (67,'rwudqvxwbysnlrnq',5,'Реализация чата');
insert into taskentity(id, description, position, text) VALUES (68,'eqxqafxiozvsvwop',6,'ColumnEntity добавить поле очередности (порядка) на доске');
insert into taskentity(id, description, position, text) VALUES (69,'xmleusosuiqcsfoi',7,'TaskController | Реализовать удаление тегов и пользователей у задачи');
insert into taskentity(id, description, position, text) VALUES (70,'knlnvhzbrpfimaok',8,'Удаление  UsersBoardsRolesEntity, с последующим изменением затрагиваемых ей областей');
insert into taskentity(id, description, position, text) VALUES (71,'xjqlyhgymdlmikqf',9,'BoardController | Реализовать удаление пользователя из доски');
insert into taskentity(id, description, position, text) VALUES (72,'idjbouydrohygqfe',10,'Рефакторинг кода TagController-a');
insert into taskentity(id, description, position, text) VALUES (73,'ejkbmmrbbiqpjvla',11,'еализация TagEntityController-a');
insert into taskentity(id, description, position, text) VALUES (74,'zfvmmozykessnuug',12,'Создать модель БД');
insert into taskentity(id, description, position, text) VALUES (75,'vojkyuolzwgjciza',13,'TaskController | Реализация перемещения задачи в другую колонку');
insert into taskentity(id, description, position, text) VALUES (76,'kbxrxlvzfgnkumic',14,'Написание тестов для BoardController-а');
insert into taskentity(id, description, position, text) VALUES (77,'dverwmfrazcqmvoy',15,'Написание тестов для ColumnController-а');
insert into taskentity(id, description, position, text) VALUES (78,'xycibryhjhgqxheo',16,'Написание тестов для TaskController-а');
insert into taskentity(id, description, position, text) VALUES (79,'znbhhsodljthpmsu',17,'Написание тестов для TagController-а');
insert into taskentity(id, description, position, text) VALUES (80,'agxifhowolgqrubx',18,'Написание тестов для UserController-а');
insert into taskentity(id, description, position, text) VALUES (81,'dwbhmtunxgwcyker',19,'Написание тестов для CommentController-а');
insert into taskentity(id, description, position, text) VALUES (82,'kwjmqszkhjynloxr',1,'ColumnEntity добавить поле очередности (порядка) на доске');
insert into taskentity(id, description, position, text) VALUES (83,'bdveonbbzpgvpmvi',2,'TaskController | Реализовать удаление тегов и пользователей у задачи');
insert into taskentity(id, description, position, text) VALUES (84,'baepkbjilpjdvkqq',3,'Удаление  UsersBoardsRolesEntity, с последующим изменением затрагиваемых ей областей');
insert into taskentity(id, description, position, text) VALUES (85,'ymljkpvqmwbdbjyl',4,'BoardController | Реализовать удаление пользователя из доски');
insert into taskentity(id, description, position, text) VALUES (86,'bixveaujgzqqoycr',5,'Рефакторинг кода TagController-a');
insert into taskentity(id, description, position, text) VALUES (87,'rqdjiuuryzpkhgkq',6,'еализация TagEntityController-a');
insert into taskentity(id, description, position, text) VALUES (88,'acfglmkgyosbmhai',7,'Создать модель БД');
insert into taskentity(id, description, position, text) VALUES (89,'cvhrimcvfywpkpzd',8,'TaskController | Реализация перемещения задачи в другую колонку');
insert into taskentity(id, description, position, text) VALUES (90,'vpscruwzcxortftw',9,'Написание тестов для BoardController-а');
insert into taskentity(id, description, position, text) VALUES (91,'vbytfthsxoyzqhre',10,'Написание тестов для ColumnController-а');
insert into taskentity(id, description, position, text) VALUES (92,'ljlchtvcuacetvru',11,'Написание тестов для TaskController-а');
insert into taskentity(id, description, position, text) VALUES (93,'bbbiqntdejhsvucm',12,'Написание тестов для TagController-а');
insert into taskentity(id, description, position, text) VALUES (94,'zplgpsqsmwbhlbrz',13,'Написание тестов для UserController-а');
insert into taskentity(id, description, position, text) VALUES (95,'yidbxkisxnuzpown',14,'Написание тестов для CommentController-а');
insert into taskentity(id, description, position, text) VALUES (96,'obrrtnjpqgpiwniu',1,'ColumnEntity добавить поле очередности (порядка) на доске');
insert into taskentity(id, description, position, text) VALUES (97,'mbmhqckmtiimxpgg',2,'TaskController | Реализовать удаление тегов и пользователей у задачи');
insert into taskentity(id, description, position, text) VALUES (98,'cigwamugqzuzxtrd',3,'Удаление  UsersBoardsRolesEntity, с последующим изменением затрагиваемых ей областей');
insert into taskentity(id, description, position, text) VALUES (99,'sknixggofhlrnhvp',4,'BoardController | Реализовать удаление пользователя из доски');
insert into taskentity(id, description, position, text) VALUES (100,'peatqyqqhxqpzjvf',5,'Рефакторинг кода TagController-a');
insert into taskentity(id, description, position, text) VALUES (101,'dpffnaxgybldunpm',6,'еализация TagEntityController-a');
insert into taskentity(id, description, position, text) VALUES (102,'idefxrocjftdmyqf',7,'Создать модель БД');
insert into taskentity(id, description, position, text) VALUES (103,'dbvgqololtioecpi',8,'TaskController | Реализация перемещения задачи в другую колонку');
insert into taskentity(id, description, position, text) VALUES (104,'cgkurfmgbzbfhqlv',9,'Написание тестов для BoardController-а');
insert into taskentity(id, description, position, text) VALUES (105,'wprvaggzwojqdzxu',10,'Написание тестов для ColumnController-а');
insert into taskentity(id, description, position, text) VALUES (106,'nzfetvlatbagtzxt',11,'Написание тестов для TaskController-а');
insert into taskentity(id, description, position, text) VALUES (107,'mplxsxrpwgriixrs',12,'Написание тестов для TagController-а');
insert into taskentity(id, description, position, text) VALUES (108,'lyalcnzonxyyasau',13,'Написание тестов для UserController-а');
insert into taskentity(id, description, position, text) VALUES (109,'eqmjcyuvcppcbgkg',14,'Написание тестов для CommentController-а');
insert into taskentity(id, description, position, text) VALUES (110,'urlbbknhojvakpns',1,'Написание тестов для BoardController-а');
insert into taskentity(id, description, position, text) VALUES (111,'xgjmwkjrguyzuxat',2,'Написание тестов для ColumnController-а');
insert into taskentity(id, description, position, text) VALUES (112,'wvhxxgrgqerocpjy',3,'Написание тестов для TaskController-а');
insert into taskentity(id, description, position, text) VALUES (113,'piexbbkgyoasvktz',4,'Написание тестов для TagController-а');
insert into taskentity(id, description, position, text) VALUES (114,'tpmwkfuedzqgkkvb',5,'Написание тестов для UserController-а');
insert into taskentity(id, description, position, text) VALUES (115,'nisitqryiolwyctl',6,'Написание тестов для CommentController-а');

insert into taskentity(id, description, position, text) VALUES (116,'pava-pava',1,'Задача 1');


insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,14);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,15);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,16);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,17);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,18);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,19);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,20);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,21);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,22);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,23);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,24);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,25);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,26);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,27);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,28);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,29);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,30);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,31);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,32);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,33);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,34);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,35);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,36);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,37);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,38);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,39);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (6,40);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,41);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,42);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,43);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,44);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,45);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,46);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,47);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,48);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,49);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,50);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,51);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,52);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,53);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,54);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,55);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,56);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,57);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,58);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,59);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,60);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,61);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (7,62);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,63);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,64);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,65);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,66);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,67);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,68);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,69);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,70);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,71);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,72);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,73);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,74);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,75);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,76);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,77);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,78);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,79);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,80);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (8,81);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,82);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,83);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,84);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,85);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,86);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,87);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,88);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,89);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,90);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,91);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,92);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,93);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,94);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (9,95);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,96);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,97);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,98);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,99);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,100);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,101);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,102);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,103);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,104);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,105);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,106);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,107);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,108);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (10,109);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,110);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,111);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,112);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,113);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,114);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (11,115);
insert into columnentity_taskentity(columnentity_id, tasks_id) VALUES (13,116);
insert into taskentity_userentity(taskentity_id, makers_id) VALUES (14,1);

insert into tagentity(id, title, color) VALUES (117,'Analysis',0);
insert into tagentity(id, title, color) VALUES (118,'Frontend',1);
insert into tagentity(id, title, color) VALUES (119,'Backend',2);
insert into tagentity(id, title, color) VALUES (120,'Priority task',3);
insert into tagentity(id, title, color) VALUES (121,'Database',4);
insert into tagentity(id, title, color) VALUES (122,'Bugfix',0);
insert into tagentity(id, title, color) VALUES (123,'Testing',1);
insert into tagentity(id, title, color) VALUES (124,'Non-priority task',2);

insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,117);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,118);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,119);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,120);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,121);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,122);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,123);
insert into boardentity_tagentity(boardentity_id, tags_id) VALUES (3,124);

insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (73,119);
insert into taskentity_tagentity(taskentity_id, tags_id) VALUES (73,120);




-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');