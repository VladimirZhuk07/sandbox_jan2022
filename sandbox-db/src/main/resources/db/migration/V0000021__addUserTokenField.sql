ALTER TABLE `User`
    ADD COLUMN   refresh_token       VARCHAR(255)         DEFAULT NULL,
    ADD COLUMN   token_expiry_date   DATETIME             DEFAULT NULL;
