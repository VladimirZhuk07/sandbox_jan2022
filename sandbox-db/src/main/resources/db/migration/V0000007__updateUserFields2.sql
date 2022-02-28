ALTER TABLE User
    MODIFY firstName                 VARCHAR(50) NOT NULL,
    MODIFY lastName                  VARCHAR(50) NOT NULL,
    MODIFY email                     VARCHAR(50) NOT NULL,
    MODIFY phoneNumber               VARCHAR(20) NOT NULL,
    ADD COLUMN password              VARCHAR(50) NOT NULL