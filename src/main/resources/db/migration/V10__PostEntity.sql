ALTER TABLE reminders
DROP
CONSTRAINT fk_reminders_on_user;

ALTER TABLE reminders
    ADD post_id BIGINT;

ALTER TABLE reminders
    ADD CONSTRAINT FK_REMINDERS_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);

ALTER TABLE reminders
DROP
COLUMN user_id;