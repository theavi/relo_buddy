CREATE TABLE IF NOT EXISTS user (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  password VARCHAR(255),
  phone_no VARCHAR(15)
);
CREATE TABLE IF NOT EXISTS role (
  id INT PRIMARY KEY,
  name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
);
