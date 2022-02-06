ALTER TABLE `user` DROP COLUMN username;

ALTER TABLE `user` MODIFY id BIGINT AUTO_INCREMENT;

ALTER TABLE `user`
   ADD COLUMN  chat_id              VARCHAR(50)                NOT NULL,
   ADD COLUMN  first_name           VARCHAR(50)                NOT NULL,
   ADD COLUMN  last_name            VARCHAR(50)                NOT NULL,
   ADD COLUMN  email                VARCHAR(50)                NOT NULL,
   ADD COLUMN  phone_number         VARCHAR(20)                NOT NULL,
   ADD COLUMN  employment_start     DATE                       NOT NULL,
   ADD COLUMN  employment_end       DATE,
   ADD COLUMN  is_fired             BOOLEAN;

CREATE TABLE `vacation`
(
   id                   BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   user_id              BIGINT                     NOT NULL,
   vacation_start       DATE                       NOT NULL,
   vacation_end         DATE                       NOT NULL,
   FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE `role`
(
   id                   BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name                 VARCHAR(50)                NOT NULL
);

CREATE TABLE `user_role`
(
   user_id              BIGINT                     NOT NULL,
   role_id              BIGINT                     NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (user_id) REFERENCES User(id),
   FOREIGN KEY (role_id) REFERENCES Role(id)
);