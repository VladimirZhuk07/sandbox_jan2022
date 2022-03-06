-- Countries
insert into country(id, name)
value (1, 'UZBEKISTAN');

insert into country(id, name)
value (2, 'BELARUS');

insert into country(id, name)
value (3, 'TURKEY');

insert into country(id, name)
value (4, 'INDIA');

insert into country(id, name)
value (5, 'SWEDEN');

-- Cities
insert into city(id, countryId, name)
value (1, 1, 'TASHKENT');

insert into city(id, countryId, name)
value (2, 2, 'GOMEL');

insert into city(id, countryId, name)
value (3, 3, 'ANKARA');

insert into city(id, countryId, name)
value (4, 4, 'NEW-DELI');

insert into city(id, countryId, name)
value (5, 5, 'STOCKHOLM');

-- Offices
insert into office (id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (1, 1, 'UzbekistanExBooker',
         'Aviasozlar-1', true, 'admin',
         NOW(),'admin',NOW() );

insert into office (id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (2, 2, 'BelarusExBooker',
         'улица Пионерская', true, 'admin',
         NOW(),'admin',NOW() );

insert into office (id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (3, 3, 'TurkeyЕxBooker',
         'Kocasinan Blv', true, 'admin',
         NOW(),'admin',NOW() );

insert into office (id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (4, 4, 'IndiaЕxBooker',
         'Lajpat-Nagar', true, 'admin',
         NOW(),'admin',NOW() );

insert into office (id, cityId, name,
                     address, parking, createdBy,
                     createdDate, modifiedBy, modifiedDate)
values (5, 5, 'IndiaЕxBooker',
         'SwedenЕxBooker', true, 'admin',
         NOW(),'admin',NOW() );

-- Maps
insert into map (id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (1, 1, 2, 3, 4);

insert into map (id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (2, 2, 2, 3, 4);

insert into map (id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (3, 3, 2, 3, 4);

insert into map (id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (4, 4, 2, 3, 4);

insert into map (id, officeId, floorNum,
                 kitchenNum, confRoomsNum)
values (5, 5, 2, 3, 4);


-- Users
insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired, password)
values (1, null, 'Vladislav',
        'Userovich', 'divergenny@gmail.com', '+998946622397',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        false, '12345');

insert into userrole (userId, roleId)
values (1, 1);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired, password)
values (2, null, 'Azizbek',
        'Systemovich', 'mirzakolonovazizbek@gmail.com', '+998998771917',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        false, '12345');

insert into userrole (userId, roleId)
values (2, 2);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired, password)
values (3, null, 'Ilya',
        'Mapeditorovich', 'divergenny@gmail.com', '+998946622197',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        false, '12345');

insert into userrole (userId, roleId)
values (3, 3);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired, password)
values (4, null, 'Alena',
        'Managerovich', 'mirzakolonovazizbek@gmail.com', '+998998721917',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        false, '12345');

insert into userrole (userId, roleId)
values (4, 4);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired, password)
values (5, null, 'Vladimir',
        'Administratorovich', 'divergenny@gmail.com', '+998998756090',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        false, '12345');

insert into userrole (userId, roleId)
values (5, 5);


insert into user (id, chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy,
                  isFired, password)
values (6, null, 'Nurmukhammad',
        'Sunatullaev', 'nurmukhammad.sunatullaev@gmail.com', '+998998756090',
        '01.03.2022', null, null,
        null, 'new', NOW(),
        NOW(), 'SYSTEM', 'SYSTEM',
        false, 'nur123456');

insert into userrole (userId, roleId)
values (6, 5);
