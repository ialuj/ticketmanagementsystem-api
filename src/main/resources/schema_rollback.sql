ALTER TABLE TICKETS DROP CONSTRAINT CHECK_CATEGORY_VALUE;
ALTER TABLE TICKETS DROP CONSTRAINT CHECK_PRIORITY_VALUE;
ALTER TABLE TICKETS DROP CONSTRAINT CHECK_STATUS_VALUE;

ALTER TABLE USERS DROP CONSTRAINT CHECK_ROLE_VALUE;
ALTER TABLE USERS DROP CONSTRAINT UQ_USERNAME;

DROP SEQUENCE AUDIT_LOGS_SEQUENCE;
DROP SEQUENCE COMMENTS_SEQUENCE;
DROP SEQUENCE TICKETS_SEQUENCE;
DROP SEQUENCE USERS_SEQUENCE;

DROP TABLE AUDIT_LOGS;
DROP TABLE COMMENTS;
DROP TABLE TICKETS;
DROP TABLE USERS;