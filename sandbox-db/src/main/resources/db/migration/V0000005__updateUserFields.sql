ALTER TABLE User
    MODIFY status ENUM('NEW','INVITED','WAIT_PHONE_AUTHORIZATION', 'UNAUTHORIZED', 'ACTIVE', 'BLOCKED') DEFAULT 'NEW'