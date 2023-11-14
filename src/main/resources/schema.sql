create table user_table (
	user_id int NOT NULL AUTO_INCREMENT,  
	alias varchar(25) unique, 
	email varchar(25) unique,
	password varchar(100) NOT NULL, 
	role varchar(25),
	primary key (user_id)
);

create table song(
	deezer_id int NOT NULL primary key,
	title varchar(45),
	duration int,
	artist varchar(45),
	cover_art varchar(150),
	preview varchar(150)
);

create table user_library(
	library_id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	deezer_id int,
	primary key (library_id),
	FOREIGN KEY (user_id) REFERENCES user_table (user_id) on delete cascade,
	FOREIGN KEY (deezer_id) REFERENCES song (deezer_id) on delete cascade
);
