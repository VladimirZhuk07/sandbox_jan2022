DROP TABLE `User`;

CREATE TABLE `User`
(
    id              BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    chatId          VARCHAR(50) NOT NULL,
    firstName       VARCHAR(50) NOT NULL,
    lastName        VARCHAR(50) NOT NULL,
    email           VARCHAR(50) NOT NULL,
    phoneNumber     VARCHAR(20) NOT NULL,
    employmentStart DATE        NOT NULL,
    employmentEnd   DATE,
    createdDate     DATETIME    NOT NULL,
    modifiedDate    DATETIME    NOT NULL,
    createdBy       VARCHAR(50) NOT NULL,
    modifiedBy      VARCHAR(50) NOT NULL,
    isFired         BOOLEAN
);

CREATE TABLE `Vacation`
(
    id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId        BIGINT NOT NULL,
    vacationStart DATE   NOT NULL,
    vacationEnd   DATE   NOT NULL,
    FOREIGN KEY (userId) REFERENCES User (id)
);

CREATE TABLE `Role`
(
    id   BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name ENUM('USER', 'MAP_EDITOR', 'ADMIN', 'MANAGER') DEFAULT NULL
);

CREATE TABLE `UserRole`
(
    userId BIGINT NOT NULL,
    roleId BIGINT NOT NULL,
    PRIMARY KEY (userId, roleId),
    FOREIGN KEY (userId) REFERENCES User (id),
    FOREIGN KEY (roleId) REFERENCES Role (id)
);