DROP TABLE `User`;

CREATE TABLE `User` (
   id                   BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   chat_id              VARCHAR(50)                NOT NULL,
   first_name           VARCHAR(50)                NOT NULL,
   last_name            VARCHAR(50)                NOT NULL,
   email                VARCHAR(50)                NOT NULL,
   phone_number         VARCHAR(20)                NOT NULL,
   employment_start     DATE                       NOT NULL,
   employment_end       DATE,
   created_date         DATETIME                   NOT NULL,
   modified_date        DATETIME                   NOT NULL,
   created_by           VARCHAR(50)                NOT NULL,
   modified_by          VARCHAR(50)                NOT NULL,
   is_fired             BOOLEAN
);

CREATE TABLE `Vacation`
(
   id                   BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   user_id              BIGINT                     NOT NULL,
   vacation_start       DATE                       NOT NULL,
   vacation_end         DATE                       NOT NULL,
   FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE `Role`
(
   id                   BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name   ENUM("COMMON_USER", "MAP_EDITOR", "ADMIN", "MANAGER")   DEFAULT NULL
);

CREATE TABLE `User_Role`
(
   user_id              BIGINT                     NOT NULL,
   role_id              BIGINT                     NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (user_id) REFERENCES User(id),
   FOREIGN KEY (role_id) REFERENCES Role(id)
);