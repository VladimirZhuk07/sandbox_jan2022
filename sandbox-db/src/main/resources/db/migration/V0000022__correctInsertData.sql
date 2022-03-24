INSERT INTO `Workplace` (id, mapId, workplaceNumber,
                       nextToWindow, pc, monitor,
                       keyboard, mouse, headset)
VALUES (1, 1, 21,
        true, true, true,
        true, true, true);

INSERT INTO `Workplace` (id, mapId, workplaceNumber,
                       nextToWindow, pc, monitor,
                       keyboard, mouse, headset)
VALUES (2, 2, 22,
        true, true, true,
        true, true, true);

INSERT INTO `Workplace` (id, mapId, workplaceNumber,
                       nextToWindow, pc, monitor,
                       keyboard, mouse, headset)
VALUES (3, 3, 22,
        true, true, true,
        true, true, true);

INSERT INTO `Workplace` (id, mapId, workplaceNumber,
                       nextToWindow, pc, monitor,
                       keyboard, mouse, headset)
VALUES (4, 4, 22,
        true, true, true,
        true, true, true);


INSERT INTO `Workplace` (id, mapId, workplaceNumber,
                       nextToWindow, pc, monitor,
                       keyboard, mouse, headset)
VALUES (5, 5, 22,
        true, true, true,
        true, true, true);


UPDATE `User` u set phoneNumber = '+9989466948822' where u.id = 5;

UPDATE `User` u set email = 'divergenny1@gmail.com' where u.id = 3;

UPDATE `User` u set email = 'divergenny@gmail.com' where u.id = 5;

UPDATE `User` u set email = 'vlad_2397@mail.ru', phoneNumber = '+998938022308' where u.id = 1;

UPDATE `User` u set phoneNumber = '+998998771017' where id = 2;