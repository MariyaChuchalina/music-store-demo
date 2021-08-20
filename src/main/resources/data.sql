DROP TABLE IF EXISTS guitars;

CREATE TABLE guitars (
  id INT AUTO_INCREMENT PRIMARY KEY,
  brand VARCHAR(50) NOT NULL,
  type VARCHAR(50) NOT NULL,
  model VARCHAR(50) NOT NULL,
  price DECIMAL(7,2) NOT NULL
);

INSERT INTO guitars (brand, type, model, price) VALUES
  ('Gibson', 'acoustic', 'J-45', 2479.00),
  ('Fender', 'hybrid', 'T-Bucket', 250.00),
  ('Schecter', 'electic', 'Apocalypse V1', 1100.99);

DROP TABLE IF EXISTS accessories;

CREATE TABLE accessories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  brand VARCHAR(50) NOT NULL
);

INSERT INTO accessories (brand) VALUES
  ('Dunlop'),
  ('Martin');
