
CREATE TABLE `Country`
(
    name                    VARCHAR(50)                   NOT NULL PRIMARY KEY
);


CREATE TABLE `Office`
(

    id                  BIGINT                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    countryName                  VARCHAR(50)                  NOT NULL,
    city                    VARCHAR(50)                   NOT NULL,
    name                    VARCHAR(80)                   NOT NULL,
    address                     VARCHAR(120)                   NOT NULL,
    hasParking                  BOOLEAN                 DEFAULT FALSE,
    createdBy                   VARCHAR(80)                    NOT NULL,
    createdDate                 DATE                    NOT NULL,
    modifiedBy                  VARCHAR(80)                   DEFAULT NULL,
    modifiedDate                    DATE                    DEFAULT NULL,

    CONSTRAINT customer_name_fqv FOREIGN KEY (countryName)
    REFERENCES Country(name)
);



CREATE TABLE `Map`
(
    id                  BIGINT                  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    officeId                   BIGINT                  NOT NULL,
    floorNum                    INTEGER                 DEFAULT 1,
    kitchenNum                  INTEGER                 DEFAULT 0,
    confRoomsNum                    INTEGER                 DEFAULT 0,

    CONSTRAINT office_id_fqv FOREIGN KEY (officeId)
    REFERENCES Office(id)
);
