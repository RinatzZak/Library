DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS book_category CASCADE;

CREATE TABLE Authors
(
    author_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name      varchar(100) NOT NULL
);

CREATE TABLE Books
(
    book_id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    author_id int REFERENCES Authors (author_id) ON DELETE CASCADE ON UPDATE CASCADE,
    book_name varchar(100)
);

CREATE TABLE Categories
(
    category_id   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    category_name varchar(100) NOT NULL
);

CREATE table Book_Category
(
    book_id int REFERENCES books (book_id) ON DELETE CASCADE ON UPDATE CASCADE ,
    category_id int REFERENCES categories (category_id) ON DELETE CASCADE ON UPDATE CASCADE ,
    PRIMARY KEY (book_id, category_id)
);

INSERT INTO authors(name) VALUES ('Александр Сергеевич Пушкин');
INSERT INTO authors(name) VALUES ('Лев Николаевич Толстой');
INSERT INTO authors(name) VALUES ('Николай Васильевич Гоголь');
INSERT INTO authors(name) VALUES ('Федор Михайлович Достоевский');
INSERT INTO authors(name) VALUES ('Антон Павлович Чехов');
INSERT INTO authors(name) VALUES ('Иван Сергеевич Тургенев');

INSERT INTO books(book_name, author_id) VALUES ('Евгений Онегин', 1);
INSERT INTO books(book_name, author_id) VALUES ('Каменный гость', 1);
INSERT INTO books(book_name, author_id) VALUES ('Борис Годунов', 1);
INSERT INTO books(book_name, author_id) VALUES ('Война и Мир', 2);
INSERT INTO books(book_name, author_id) VALUES ('Анна Каренина', 2);
INSERT INTO books(book_name, author_id) VALUES ('Воскресение', 2);
INSERT INTO books(book_name, author_id) VALUES ('Ревизор', 3);
INSERT INTO books(book_name, author_id) VALUES ('Бедные люди', 4);
INSERT INTO books(book_name, author_id) VALUES ('Бесы', 4);
INSERT INTO books(book_name, author_id) VALUES ('Преступление и наказание', 4);
INSERT INTO books(book_name, author_id) VALUES ('Записки из Мертвого Дома', 4);
INSERT INTO books(book_name, author_id) VALUES ('Отцы и дети', 6);
INSERT INTO books(book_name, author_id) VALUES ('Ася', 6);
INSERT INTO books(book_name, author_id) VALUES ('Накануне', 6);
INSERT INTO books(book_name, author_id) VALUES ('Дворянское гнездо', 1);
INSERT INTO books(book_name, author_id) VALUES ('Му-му', 3);
INSERT INTO books(book_name, author_id) VALUES ('Палата №6', 5);

INSERT INTO categories (category_name) VALUES ('Самые читаемые книги');
INSERT INTO categories (category_name) VALUES ('Лучший выбор');
INSERT INTO categories (category_name) VALUES ('Редкие книги');
INSERT INTO categories (category_name) VALUES ('Для детей');
INSERT INTO categories (category_name) VALUES ('Для взрослых');
INSERT INTO categories (category_name) VALUES ('Тяжесть бытия');

INSERT into book_category (book_id, category_id) VALUES (1, 1);
INSERT into book_category (book_id, category_id) VALUES (1, 2);
INSERT into book_category (book_id, category_id) VALUES (1, 5);
INSERT into book_category (book_id, category_id) VALUES (2, 5);
INSERT into book_category (book_id, category_id) VALUES (2, 3);
INSERT into book_category (book_id, category_id) VALUES (3, 1);
INSERT into book_category (book_id, category_id) VALUES (3, 4);
INSERT into book_category (book_id, category_id) VALUES (4, 5);
INSERT into book_category (book_id, category_id) VALUES (4, 6);
INSERT into book_category (book_id, category_id) VALUES (5, 1);
INSERT into book_category (book_id, category_id) VALUES (5, 6);
INSERT into book_category (book_id, category_id) VALUES (6, 3);
INSERT into book_category (book_id, category_id) VALUES (6, 5);
INSERT into book_category (book_id, category_id) VALUES (7, 4);
INSERT into book_category (book_id, category_id) VALUES (7, 1);
INSERT into book_category (book_id, category_id) VALUES (8, 3);
INSERT into book_category (book_id, category_id) VALUES (9, 5);
INSERT into book_category (book_id, category_id) VALUES (9, 6);
INSERT into book_category (book_id, category_id) VALUES (10, 1);
INSERT into book_category (book_id, category_id) VALUES (10, 6);
INSERT into book_category (book_id, category_id) VALUES (11, 2);
INSERT into book_category (book_id, category_id) VALUES (11, 3);
INSERT into book_category (book_id, category_id) VALUES (12, 4);
INSERT into book_category (book_id, category_id) VALUES (13, 1);
INSERT into book_category (book_id, category_id) VALUES (14, 3);
INSERT into book_category (book_id, category_id) VALUES (15, 3);
INSERT into book_category (book_id, category_id) VALUES (15, 5);
INSERT into book_category (book_id, category_id) VALUES (16, 1);
INSERT into book_category (book_id, category_id) VALUES (16, 2);
INSERT into book_category (book_id, category_id) VALUES (16, 4);
INSERT into book_category (book_id, category_id) VALUES (17, 6);