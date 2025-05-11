-- Create roles
INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');

-- Create users
INSERT INTO users (id, username) VALUES (1, 'Abc');
INSERT INTO users (id, username) VALUES (2, 'Def');

-- Assign roles to users (Many-to-Many relationship)
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); --  -> ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2); --  -> USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); --  -> USER