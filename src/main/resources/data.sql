insert into user_table
(alias, email, password, role) values 
('user', 'test@csumb.edu', '$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue','USER'),
('admin', 'admin@csumb.edu', '$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW' , 'ADMIN');

insert into song values
(1, 'test', 91, 'david', 'link.com'),
(2, 'test2', 92, 'david', 'link.com'),
(3, 'test3', 92, 'david', 'link.com');


insert into user_library 
(user_id, deezer_id) values
(1, 1),
(1 ,2),
(1, 3);