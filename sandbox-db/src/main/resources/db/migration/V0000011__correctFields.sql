ALTER TABLE `Office`
    MODIFY createdDate      DATETIME        NOT NULL,
    MODIFY createdBy        VARCHAR(80)     NOT NULL;

ALTER TABLE `User`
    MODIFY modifiedBy       VARCHAR(80)     DEFAULT NULL,
    MODIFY modifiedDate     DATETIME        DEFAULT NULL;
