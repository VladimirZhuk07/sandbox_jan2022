insert into `sandbox`.`user`
(`chatId`, `firstName`, `lastName`,
`email`, `phoneNumber`, `employmentStart`,
`employmentEnd`, `telegramAuthorizationCode`, `telegramState`,
`status`, `createdDate`, `modifiedDate`,
`createdBy`, `modifiedBy`, `isFired`)
VALUES
(12, 'Vladislav', 'Test2',
'divergenny@gmail.com', 123121232,'2021.12.02',
'2021.12.02', 12131234, null,
'NEW', '2022.01.03', '2022.01.03',
'vlad', 'vlad', false);

insert into `sandbox`.`user`
(`chatId`, `firstName`, `lastName`,
`email`, `phoneNumber`, `employmentStart`,
`employmentEnd`, `telegramAuthorizationCode`, `telegramState`,
`status`, `createdDate`, `modifiedDate`,
`createdBy`, `modifiedBy`, `isFired`)
VALUES
(12, 'Aizizbek', 'Test2',
'mirzakolonovazizbek@gmail.com', 12312123,'2021.12.02',
'2021.12.02', 121312344, null,
'NEW', '2022.01.03', '2022.01.03',
'vlad', 'vlad', false);

insert into user (chatId, firstName,
                  lastName, email, phoneNumber,
                  employmentStart, employmentEnd, telegramAuthorizationCode,
                  telegramState, status, createdDate,
                  modifiedDate, createdBy, modifiedBy, isFired)
values (null, 'Vladislav', 'Userovich', 'divergenny@gmail.com', '+998946622097',
        '01.03.2022', null, null, null, 'new_user', NOW(), NOW(), 'admin', 'admin', false);



