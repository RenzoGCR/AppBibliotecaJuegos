CREATE TABLE IF NOT EXISTS user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    is_admin TINYINT(1)   NULL
    );
INSERT INTO user (email, password, is_admin)
SELECT 'f@cesur.com', '1234', 1 WHERE NOT EXISTS (SELECT 1 FROM user)
UNION ALL
SELECT 'r@cesur.com', '1234', 0 WHERE NOT EXISTS (SELECT 1 FROM user)
UNION ALL
SELECT 'sergio@cesur.com', '1234', 0 WHERE NOT EXISTS (SELECT 1 FROM user)
UNION ALL
SELECT 'g@cesur.com', '1234', 0 WHERE NOT EXISTS (SELECT 1 FROM user);