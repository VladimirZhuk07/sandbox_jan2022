-- возможно не нужен
INSERT INTO booking (id, workplaceId, userId,
                     startDate, endDate, recurring,
                     monday, tuesday, wednesday,
                     thursday, friday, saturday,
                     sunday, createdDate, modifiedDate,
                     createdBy, modifiedBy)
VALUES (1, 1, 1,
        '01-06-2022', '15-06-2022', false,
        true, true, true,
        true, true, true,
        true, NOW(), NOW(),
        'admin', 'admin');

INSERT INTO booking (id, workplaceId, userId,
                     startDate, endDate, recurring,
                     monday, tuesday, wednesday,
                     thursday, friday, saturday,
                     sunday, createdDate, modifiedDate,
                     createdBy, modifiedBy)
VALUES (2, 2, 2,
        '01-07-2022', '15-07-2022', false,
        true, true, true,
        true, true, true,
        true, NOW(), NOW(),
        'admin', 'admin');

INSERT INTO booking (id, workplaceId, userId,
                     startDate, endDate, recurring,
                     monday, tuesday, wednesday,
                     thursday, friday, saturday,
                     sunday, createdDate, modifiedDate,
                     createdBy, modifiedBy)
VALUES (3, 3, 3,
        '01-08-2022', '15-08-2022', true,
        true, true, true,
        true, true, true,
        true, NOW(), NOW(),
        'admin', 'admin');

INSERT INTO booking (id, workplaceId, userId,
                     startDate, endDate, recurring,
                     monday, tuesday, wednesday,
                     thursday, friday, saturday,
                     sunday, createdDate, modifiedDate,
                     createdBy, modifiedBy)
VALUES (4, 4, 4,
        '01-09-2022', '15-09-2022', false,
        true, true, true,
        true, true, true,
        true, NOW(), NOW(),
        'admin', 'admin');

INSERT INTO booking (id, workplaceId, userId,
                     startDate, endDate, recurring,
                     monday, tuesday, wednesday,
                     thursday, friday, saturday,
                     sunday, createdDate, modifiedDate,
                     createdBy, modifiedBy)
VALUES (5, 5, 5,
        '01-10-2022', '15-10-2022', false,
        true, true, true,
        true, true, true,
        true, NOW(), NOW(),
        'admin', 'admin');
