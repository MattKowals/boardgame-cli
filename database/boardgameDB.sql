DROP TABLE IF EXISTS board_games;
DROP SEQUENCE IF EXISTS game_serial;

CREATE SEQUENCE game_serial;
CREATE TABLE board_games (
    game_id int NOT NULL DEFAULT nextval('game_serial'),
    game_name varchar(100) not null,
    publisher varchar(35),
    year_published int,
    date_purchased date,
    price decimal(4,2),
    time_to_teach_in_minutes int,
    time_to_play_in_minutes_min int,
    time_to_play_in_minutes_max int,
    min_players int NOT NULL,
    max_players int,
    expansion boolean NOT NULL,
    description varchar(255),
    CONSTRAINT PK_board_games PRIMARY KEY(game_id));

INSERT INTO board_games (game_id, game_name, publisher, year_published, date_purchased,
    price, time_to_teach_in_minutes, time_to_play_in_minutes_min, 
    time_to_play_in_minutes_max, min_players, max_players, expansion, description) 
    VALUES (
        DEFAULT, 'Senjutsu Battle for Japan', 'Stone Swrod Games', '2024', '2024/08/15', 
        '40.00', '30', '15', '20', '1', '4', FALSE, 'Tactical card and minis game'
        ), 
        (
        DEFAULT, 'Railroad Ink Canyon Red', 'Horrible Guild', '2021', null, '20.00', 
        '5', '20', '30', '1', '6', FALSE, 'Roll and write strategy puzzle game');