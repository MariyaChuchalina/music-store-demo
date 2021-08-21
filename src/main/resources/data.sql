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

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  role VARCHAR(50) NOT NULL,
  access_token VARCHAR(50) NOT NULL
);

INSERT INTO users (first_name, last_name, role, access_token) VALUES
  ('Anna', 'Smith', 'client', 'clientaccesstoken'),
  ('Frederic', 'Hudson', 'admin', 'adminaccesstoken'),
  ('Unknown', 'Person', '', 'aaa');
