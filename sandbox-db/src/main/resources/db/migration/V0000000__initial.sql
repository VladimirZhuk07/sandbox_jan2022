CREATE TABLE `User`
(
    id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,

    UNIQUE KEY `username` (`username`)
);