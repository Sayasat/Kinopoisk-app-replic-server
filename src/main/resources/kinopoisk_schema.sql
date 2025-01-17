create table Director (
                          id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                          full_name varchar(200) NOT NULL UNIQUE,
                          biography TEXT,
                          birth_date DATE
);


create table Genre (
                       id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                       genre varchar(150) NOT NULL
);



create table Movie_Genre (
                             movie_id int NOT NULL REFERENCES Movie(id),
                             genre_id int NOT NULL REFERENCES Genre(id),
                             PRIMARY KEY (movie_id, genre_id)
);


create table Movie (
                       id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                       title varchar(255) NOT NULL,
                       description TEXT,
                       release_date DATE,
                       duration INT,
                       rating DECIMAL(3,2),
                       director_id INT REFERENCES Director(id),
                       genre_id int REFERENCES Genre(id)
);


create table Actor (
                       id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                       full_name varchar(255),
                       biography text,
                       birth_date DATE
);

create table Movie_Actor_Role (
                                  movie_id int REFERENCES Movie(id),
                                  actor_id int REFERENCES Actor(id),
                                  role varchar(255),
    PRIMARY KEY (movie_id, actor_id)
);




create table "User" (
                        id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                        username Varchar(255) NOT NULL,
                        password varchar(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        registration_date TIMESTAMP default current_timestamp
);


create table Review (
                        id int Primary Key GENERATED BY DEFAULT AS IDENTITY,
                        movie_id int REFERENCES Movie(id),
                        user_id int REFERENCES "User"(id),
                        rating int CHECK ( rating >=1 and rating<=10),
                        comment TEXT,
                        review_date TIMESTAMP default current_timestamp
);





