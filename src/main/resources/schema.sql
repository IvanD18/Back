-- H2 - in memory умирает при каждом перезапуске
CREATE TABLE posts (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    media TEXT,
    removed BOOLEAN DEFAULT FALSE,
    likes INTEGER NOT NULL DEFAULT 0,
    since INTEGER default 5
);
