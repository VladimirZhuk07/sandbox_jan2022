CREATE TABLE `User`
(
   id                   BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   chat_id              VARCHAR(50)                NOT NULL,
   first_name           VARCHAR(50)                NOT NULL,
   last_name            VARCHAR(50)                NOT NULL,
   email                VARCHAR(50)                NOT NULL,
   employment_start     DATE                       NOT NULL,
   employment_end       DATE,
   is_fired             BOOLEAN
);

CREATE TABLE `Vacation`
(
   id                   INT                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
   user_id              INT                        NOT NULL,
   vacation_start       DATE                       NOT NULL,
   vacation_end         DATE                       NOT NULL,
   FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE `Role`
(
   id                   INT                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name                 VARCHAR(50)                NOT NULL
);

CREATE TABLE `User_role`
(
   user_id              INT                        NOT NULL,
   role_id              INT                        NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (user_id) REFERENCES User(id),
   FOREIGN KEY (role_id) REFERENCES Role(id)
);
