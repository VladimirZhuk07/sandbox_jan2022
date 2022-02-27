ALTER TABLE User
    MODIFY chatId                    VARCHAR(50) default null,
    MODIFY firstName                 VARCHAR(50) default null,
    MODIFY lastName                  VARCHAR(50) default null,
    MODIFY email                     VARCHAR(50) default null,
    MODIFY phoneNumber               VARCHAR(20) default null,
    MODIFY employmentStart           DATE        default null,
    MODIFY employmentEnd             DATE        default null,
    MODIFY isFired                   BOOLEAN     default false