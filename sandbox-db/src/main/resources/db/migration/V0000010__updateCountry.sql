DROP TABLE `Booking`;
DROP TABLE `Workplace`;
DROP TABLE `Map`;
DROP TABLE `Office`;
DROP TABLE `Country`;

CREATE TABLE `Country`
(
    id                      BIGINT                        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                    VARCHAR(50)                   NOT NULL
);

CREATE TABLE `City`
(
    id                      BIGINT                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    countryId               BIGINT                  NOT NULL,
    name                    VARCHAR(80)             NOT NULL,

    FOREIGN KEY(countryId)
        REFERENCES Country(id)
);

CREATE TABLE `Office`
(

    id                  BIGINT                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cityId                BIGINT                  NOT NULL,
    name                    VARCHAR(80)                   NOT NULL,
    address                     VARCHAR(120)                   NOT NULL,
    parking                  BOOLEAN                 DEFAULT FALSE,
    createdBy                   VARCHAR(80)                    DEFAULT NULL,
    createdDate                 DATETIME                    DEFAULT NULL,
    modifiedBy                  VARCHAR(80)                   DEFAULT NULL,
    modifiedDate                    DATETIME                    DEFAULT NULL,

    FOREIGN KEY (cityId)
        REFERENCES City(id)
);



CREATE TABLE `Map`
(
    id                  BIGINT                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    officeId                   BIGINT                  NOT NULL,
    floorNum                    INTEGER                 DEFAULT 1,
    kitchenNum                  INTEGER                 DEFAULT 0,
    confRoomsNum                    INTEGER                 DEFAULT 0,

    FOREIGN KEY (officeId)
        REFERENCES Office(id)
);

CREATE TABLE `Workplace` (
                             id                  BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             mapId               BIGINT                     NOT NULL,
                             workplaceNumber     BIGINT                     NOT NULL,
                             nextToWindow        BOOLEAN                    DEFAULT FALSE,
                             pc                  BOOLEAN                    DEFAULT FALSE,
                             monitor             BOOLEAN                    DEFAULT FALSE,
                             keyboard            BOOLEAN                    DEFAULT FALSE,
                             mouse               BOOLEAN                    DEFAULT FALSE,
                             headset             BOOLEAN                    DEFAULT FALSE,
                             FOREIGN KEY (mapId) REFERENCES Map(id)
);

CREATE TABLE `Booking` (
                           id                  BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           workplaceId         BIGINT                     NOT NULL,
                           userId              BIGINT                     NOT NULL,
                           startDate           DATE                       NOT NULL,
                           endDate             DATE                       NOT NULL,
                           recurring           BOOLEAN                    DEFAULT FALSE,
                           monday              BIT(1)                     DEFAULT NULL,
                           tuesday             BIT(1)                     DEFAULT NULL,
                           wednesday           BIT(1)                     DEFAULT NULL,
                           thursday            BIT(1)                     DEFAULT NULL,
                           friday              BIT(1)                     DEFAULT NULL,
                           saturday            BIT(1)                     DEFAULT NULL,
                           sunday              BIT(1)                     DEFAULT NULL,
                           createdDate         DATETIME                   NOT NULL,
                           modifiedDate        DATETIME                   DEFAULT NULL,
                           createdBy           VARCHAR(50)                NOT NULL,
                           modifiedBy          VARCHAR(50)                DEFAULT NULL,
                           FOREIGN KEY (workplaceId) REFERENCES Workplace(id),
                           FOREIGN KEY (userId) REFERENCES User(id)
);