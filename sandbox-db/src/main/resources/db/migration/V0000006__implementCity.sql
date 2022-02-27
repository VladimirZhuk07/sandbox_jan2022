DROP TABLE `Booking`;
DROP TABLE `Workplace`;
DROP TABLE `Map`;
DROP TABLE `Office`;
DROP TABLE `Country`;

CREATE TABLE `Country`
(
    name                    VARCHAR(50)                   NOT NULL PRIMARY KEY
);

CREATE TABLE `City`
(
    countryName             VARCHAR(50)             NOT NULL,
    name                    VARCHAR(80)             NOT NULL PRIMARY KEY,

    FOREIGN KEY(countryName)
    REFERENCES Country(name)
);


CREATE TABLE `Office`
(

    id                  BIGINT                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cityName                VARCHAR(50)                  NOT NULL,
    name                    VARCHAR(80)                   NOT NULL,
    address                     VARCHAR(120)                   NOT NULL,
    hasParking                  BOOLEAN                 DEFAULT FALSE,
    createdBy                   VARCHAR(80)                    DEFAULT NULL,
    createdDate                 DATETIME                    DEFAULT NULL,
    modifiedBy                  VARCHAR(80)                   DEFAULT NULL,
    modifiedDate                    DATETIME                    DEFAULT NULL,

--     customer_name_fqv
--     CONSTRAINT

    FOREIGN KEY (cityName)
        REFERENCES City(name)
);



CREATE TABLE `Map`
(
    id                  BIGINT                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    officeId                   BIGINT                  NOT NULL,
    floorNum                    INTEGER                 DEFAULT 1,
    kitchenNum                  INTEGER                 DEFAULT 0,
    confRoomsNum                    INTEGER                 DEFAULT 0,

--     office_id_fqv
--     CONSTRAINT

    FOREIGN KEY (officeId)
        REFERENCES Office(id)
);

CREATE TABLE `Workplace` (
                             id                  BIGINT                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             mapId               BIGINT                     NOT NULL,
                             workplaceNumber     BIGINT                     NOT NULL,
                             nextToWindow        BOOLEAN                    NOT NULL,
                             pc                  BOOLEAN                    NOT NULL,
                             monitor             BOOLEAN                    NOT NULL,
                             keyboard            BOOLEAN                    NOT NULL,
                             mouse               BOOLEAN                    NOT NULL,
                             headset             BOOLEAN                    NOT NULL,
                             FOREIGN KEY (mapId) REFERENCES Map(id)
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