CREATE TABLE room
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE users
(
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR NOT NULL,
    is_admin BOOL NOT NULL
);

CREATE TABLE questions
(
    id SERIAL PRIMARY KEY NOT NULL,
    theme_id INT NOT NULL,
    type_id INT NOT NULL,
    body TEXT NOT NULL
);

CREATE TABLE theme
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL
);

CREATE TABLE answers
(
    id SERIAL PRIMARY KEY NOT NULL,
    question_id INT NOT NULL,
    is_correct BOOL NOT NULL,
    body TEXT NOT NULL
);

CREATE TABLE question_type
(
    id SERIAL PRIMARY KEY NOT NULL,
    type VARCHAR NOT NULL
);

CREATE TABLE user_in_room
(
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    PRIMARY KEY (user_id, room_id)
);

CREATE TABLE room_questions
(
    room_id INT NOT NULL,
    question_id INT NOT NULL,
    PRIMARY KEY (room_id, question_id)
);

CREATE TABLE room_settings
(
    room_id INT NOT NULL,
    num_questions INT NOT NULL DEFAULT 10,
    time_limit INT,
    PRIMARY KEY (room_id)
);

CREATE TABLE user_responses
(
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    question_id INT NOT NULL,
    answer_id INT NOT NULL,
    is_correct BOOLEAN NOT NULL,
    answered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, room_id, question_id)
);

CREATE TABLE room_settings_themes
(
    room_id INT NOT NULL,
    theme_id INT NOT NULL,
    PRIMARY KEY (room_id, theme_id)
);

CREATE TABLE room_settings_types
(
    room_id INT NOT NULL,
    type_id INT NOT NULL,
    PRIMARY KEY (room_id, type_id)
);

COMMENT ON COLUMN questions.body IS 'Content of the question';

ALTER TABLE room_settings_themes
    ADD FOREIGN KEY (room_id) REFERENCES room_settings (room_id),
    ADD FOREIGN KEY (theme_id) REFERENCES theme (id);

ALTER TABLE room_settings_types
    ADD FOREIGN KEY (room_id) REFERENCES room_settings (room_id),
    ADD FOREIGN KEY (type_id) REFERENCES question_type (id);

ALTER TABLE user_responses
    ADD FOREIGN KEY (user_id) REFERENCES users (id),
    ADD FOREIGN KEY (room_id) REFERENCES room (id),
    ADD FOREIGN KEY (question_id) REFERENCES questions (id),
    ADD FOREIGN KEY (answer_id) REFERENCES answers (id);

ALTER TABLE user_in_room
    ADD FOREIGN KEY (user_id) REFERENCES users (id),
    ADD FOREIGN KEY (room_id) REFERENCES room (id);

ALTER TABLE answers
    ADD FOREIGN KEY (question_id) REFERENCES questions (id);

ALTER TABLE questions
    ADD FOREIGN KEY (theme_id) REFERENCES theme (id),
    ADD FOREIGN KEY (type_id) REFERENCES question_type (id);

ALTER TABLE room_settings
    ADD FOREIGN KEY (room_id) REFERENCES room (id);

ALTER TABLE room_questions
    ADD FOREIGN KEY (room_id) REFERENCES room (id),
    ADD FOREIGN KEY (question_id) REFERENCES questions (id);