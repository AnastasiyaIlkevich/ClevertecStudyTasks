--Вывести к каждому самолету класс обслуживания и количество мест этого класса
SELECT ac.model AS Модель, s.fare_conditions AS Класс_обслуживания, COUNT(s.seat_no) AS Колличество_мест
FROM aircrafts ac
         INNER JOIN seats s ON ac.aircraft_code = s.aircraft_code
GROUP BY ac.model, s.fare_conditions
ORDER BY ac.model, s.fare_conditions;

--Найти 3 самых вместительных самолета (модель + кол-во мест)
SELECT DISTINCT ac.model AS Модель, COUNT(s.seat_no) AS Колличество_мест
FROM seats s INNER JOIN aircrafts ac ON s.aircraft_code = ac.aircraft_code
GROUP BY ac.model
ORDER BY COUNT(s.seat_no) DESC
LIMIT 3;

--Вывести код,модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам
SELECT ac.aircraft_code AS Код, ac.model AS Модель, s.seat_no AS Места
FROM seats s INNER JOIN aircrafts ac ON s.aircraft_code = ac.aircraft_code
WHERE ac.model LIKE 'Аэробус A321-200'
  AND s.fare_conditions NOT LIKE 'Economy'
ORDER BY s.seat_no;

--Вывести города в которых больше 1 аэропорта (код аэропорта, аэропорт, город)
SELECT airport_code AS код_аэропорта, airport_name AS аэропорт, city AS город
FROM airports
WHERE city IN (SELECT city
               FROM airports
               GROUP BY city
               HAVING COUNT (city) > 1);

-- Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация
SELECT f.flight_no AS ближайший_рейс_из_Екатеринбурга_в_Москву
FROM flights f INNER JOIN airports a_e ON a_e.airport_code = f.departure_airport
               INNER JOIN airports a_m ON a_m.airport_code = f.arrival_airport
WHERE a_e.city LIKE 'Екатеринбург'
  AND a_m.city LIKE 'Москва'
  AND f.status IN ('On Time', 'Delayed');

--Вывести самый дешевый и дорогой билет и стоимость (в одном результирующем ответе)
SELECT MIN(amount) AS минимальная_стоимость_билета, MAX(amount) AS максимальная_стоимость_билета
FROM ticket_flights;

-- Написать DDL таблицы Customers, должны быть поля id, firstName, LastName, email, phone.
-- Добавить ограничения на поля (constraints) .
CREATE TABLE IF NOT EXISTS Customers (
                                         id SERIAL NOT NULL,
                                         first_name VARCHAR (45) NOT NULL UNIQUE,
                                         last_name VARCHAR (45) NOT NULL,
                                         email VARCHAR (45) NOT NULL UNIQUE,
                                         phone VARCHAR (12)
);
ALTER TABLE Customers ADD CONSTRAINT pk_Customer PRIMARY KEY (id);

-- Написать DDL таблицы Orders, должен быть id, customerId, quantity.
-- Должен быть внешний ключ на таблицу customers + ограничения
CREATE TABLE IF NOT EXISTS Orders (
                                      id SERIAL NOT NULL,
                                      customer_id BIGINT NOT NULL,
                                      quantity INT
);
ALTER TABLE Orders ADD CONSTRAINT fk_orders_customers
    FOREIGN KEY (customer_id) REFERENCES Customers (id) ON DELETE CASCADE ON UPDATE CASCADE;

-- Написать 5 insert в эти таблицы
INSERT INTO Customers (first_name, last_name, email, phone)
VALUES ('Аня', 'Фамилия1', 'first@yandex.ru', '+234567890'),
       ('Катя', 'Фамилия2', 'second@yandex.ru', '22222333344'),
       ('Петя', 'Фамилия3', 'third@yandex.ru', '123456789011'),
       ('Люда', 'Фамилия4', 'fourth@yandex.ru', '2323232323'),
       ('Вася', 'Фамилия4', 'fifth@yandex.ru', '222233334444');
INSERT INTO Orders (customer_id, quantity)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

--SELECT * FROM Customers;
--SELECT * FROM Orders;

-- удалить таблицы
DROP TABLE Orders;
DROP TABLE Customers;
