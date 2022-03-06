ALTER TABLE `User`
    MODIFY telegramAuthorizationCode        VARCHAR(50)     DEFAULT (UUID()),
    MODIFY password                         VARCHAR(50)     DEFAULT NULL;