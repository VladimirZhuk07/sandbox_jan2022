CREATE TABLE `Workplace` (
   id                  BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   workplaceNumber     BIGINT                     NOT NULL,
   nextToWindow        BOOLEAN                    NOT NULL,
   pc                  BOOLEAN                    NOT NULL,
   monitor             BOOLEAN                    NOT NULL,
   keyboard            BOOLEAN                    NOT NULL,
   mouse               BOOLEAN                    NOT NULL,
   headset             BOOLEAN                    NOT NULL
);

CREATE TABLE `Booking` (
   id                  BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
   workplaceId         BIGINT                     NOT NULL,
   userId              BIGINT                     NOT NULL,
   startDate           DATE                       NOT NULL,
   endDate             DATE                       NOT NULL,
   recurring           BOOLEAN                    NOT NULL,
   monday              BIT(1)                     DEFAULT NULL,
   tuesday             BIT(1)                     DEFAULT NULL,
   wednesday           BIT(1)                     DEFAULT NULL,
   thursday            BIT(1)                     DEFAULT NULL,
   friday              BIT(1)                     DEFAULT NULL,
   saturday            BIT(1)                     DEFAULT NULL,
   sunday              BIT(1)                     DEFAULT NULL,
   createdDate         DATETIME                   NOT NULL,
   modifiedDate        DATETIME                   NOT NULL,
   createdBy           VARCHAR(50)                NOT NULL,
   modifiedBy          VARCHAR(50)                NOT NULL,
   FOREIGN KEY (workplaceId) REFERENCES Workplace(id),
   FOREIGN KEY (userId) REFERENCES User(id)
);