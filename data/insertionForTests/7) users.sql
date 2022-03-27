insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired)
values (1, null, 'Vladislav',
        'Userovich', 'divergenny@gmail.com', '+998946622097',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'admin', 'admin',
        false);

INSERT INTO userrole (userId, roleId)
VALUES (1, 1);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired)
values (2, null, 'Aizizbek',
        'Systemovich', 'mirzakolonovazizbek@gmail.com', '+998998771917',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'admin', 'admin',
        false);

INSERT INTO userrole (userId, roleId)
VALUES (2, 2);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired)
values (3, null, 'Ilya',
        'Mapeditorovich', 'divergenny@gmail.com', '++998946622197',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'admin', 'admin',
        false);

INSERT INTO userrole (userId, roleId)
VALUES (3, 3);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired)
values (4, null, 'Alena',
        'Managerovich', 'mirzakolonovazizbek@gmail.com', '+998998721917',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'admin', 'admin',
        false);

INSERT INTO userrole (userId, roleId)
VALUES (4, 4);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired)
values (5, null, 'Vladimir',
        'Administratorovich', 'divergenny@gmail.com', '+998998756090',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'admin', 'admin',
        false);

INSERT INTO userrole (userId, roleId)
VALUES (5, 5);
