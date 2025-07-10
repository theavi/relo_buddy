

INSERT INTO user (id,name,password,phone_no) VALUES (1,'admin','$2a$12$qzLOzlvGkPsCnRHi5OMOyu5DC43jsPq7I5fJekDVHDToYV8vXuZqe','9063237318');
INSERT INTO user (id,name,password,phone_no) VALUES (2,'admin','$2a$12$qzLOzlvGkPsCnRHi5OMOyu5DC43jsPq7I5fJekDVHDToYV8vXuZqe','9063237318');


INSERT INTO role (id,name) VALUES (1, 'Admin');
INSERT INTO role (id,name) VALUES (2, 'Manager');

INSERT INTO user_roles VALUES (1,1);
INSERT INTO user_roles VALUES (2,2);