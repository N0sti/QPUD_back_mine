INSERT INTO room (name, capacity) VALUES
                                      ('General Quiz Room', 15),
                                      ('Science Room', 10),
                                      ('History Room', 20);

INSERT INTO users (username, is_admin) VALUES
                                           ('alice', false),
                                           ('bob', true),
                                           ('charlie', false),
                                           ('dave', false),
                                           ('eve', true);

INSERT INTO theme (name) VALUES
                             ('General Knowledge'),
                             ('Science'),
                             ('History');

INSERT INTO question_type (type) VALUES
                                     ('Multiple Choice'),
                                     ('True or False');

INSERT INTO questions (theme_id, type_id, body) VALUES
                                                    (1, 1, 'What is the capital of France?'),
                                                    (2, 2, 'Water boils at 100 degrees Celsius.'),
                                                    (3, 1, 'Who was the first president of the United States?');

INSERT INTO answers (question_id, is_correct, body) VALUES
                                                        (1, true, 'Paris'),
                                                        (1, false, 'London'),
                                                        (1, false, 'Berlin'),
                                                        (1, false, 'Madrid'),
                                                        (2, true, 'True'),
                                                        (2, false, 'False'),
                                                        (3, true, 'George Washington'),
                                                        (3, false, 'Abraham Lincoln'),
                                                        (3, false, 'Thomas Jefferson'),
                                                        (3, false, 'John Adams');

INSERT INTO user_in_room (user_id, room_id) VALUES
                                                (1, 1), -- Alice in General Quiz Room
                                                (2, 2), -- Bob in Science Room
                                                (3, 3); -- Charlie in History Room

INSERT INTO room_questions (room_id, question_id) VALUES
                                                      (1, 1), -- General Quiz Room gets question about France
                                                      (2, 2), -- Science Room gets question about boiling water
                                                      (3, 3); -- History Room gets question about George Washington

INSERT INTO room_settings (room_id, num_questions, time_limit) VALUES
                                                                   (1, 5, 300),
                                                                   (2, 10, 600),
                                                                   (3, 15, 900);

INSERT INTO user_responses (user_id, room_id, question_id, answer_id, is_correct) VALUES
                                                                                      (1, 1, 1, 1, true),  -- Alice answered the France question correctly
                                                                                      (2, 2, 2, 5, true),  -- Bob answered the boiling water question correctly
                                                                                      (3, 3, 3, 7, true);  -- Charlie answered the George Washington question correctly

INSERT INTO room_settings_themes (room_id, theme_id) VALUES
                                                         (1, 1), -- General Quiz Room uses General Knowledge theme
                                                         (2, 2), -- Science Room uses Science theme
                                                         (3, 3); -- History Room uses History theme

INSERT INTO room_settings_types (room_id, type_id) VALUES
                                                       (1, 1), -- General Quiz Room uses Multiple Choice
                                                       (2, 2), -- Science Room uses True or False
                                                       (3, 1); -- History Room uses Multiple Choice