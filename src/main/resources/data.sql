DROP TABLE IF EXISTS guitars;

CREATE TABLE guitars (
  id INT AUTO_INCREMENT PRIMARY KEY,
  brand VARCHAR(50) NOT NULL,
  type VARCHAR(50) NOT NULL
);

INSERT INTO guitars (brand, type) VALUES
  ('Gibson', 'acoustic'),
  ('Fender', 'hybrid'),
  ('Schecter', 'electic');

DROP TABLE IF EXISTS accessories;

CREATE TABLE accessories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  brand VARCHAR(50) NOT NULL
);

INSERT INTO accessories (brand) VALUES
  ('Dunlop'),
  ('Martin');
