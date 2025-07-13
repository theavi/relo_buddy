-- Insert Users
INSERT INTO users (id, name, password, phone_no) VALUES
(1, 'Admin', '$2a$12$NiVXOIrsIBRh54d2LhRCUuuc1rljrtuQDjiqn8.mtCjPa6C5VPtTi', '9063237318'),
(2, 'Manager', '$2a$12$NiVXOIrsIBRh54d2LhRCUuuc1rljrtuQDjiqn8.mtCjPa6C5VPtTi', '9063237318');

-- Insert Roles
INSERT INTO role (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER');

-- Map Users to Roles
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 2);