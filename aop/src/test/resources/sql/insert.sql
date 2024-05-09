insert into tourists (id, first_name, last_name, email, phone)
values (1, 'Name1', 'Surname1', '1@1.com', '81111111111');
insert into tourists (id, first_name, last_name, email, phone)
values (2, 'Name2', 'Surname2', '2@2.com', '82222222222');
insert into tourists (id, first_name, last_name, email, phone)
values (3, 'Name3', 'Surname3', '3@3.com', '83333333333');

SELECT setval ('tourists_id_seq', (SELECT MAX(id) FROM tourists));

insert into tour_orders (id, country, city, hotel, check_in_date, rest_duration, meals_type, adult_count, child_count, tourist_id)
values (1, 'Turkey', 'Kemer', 'Rixos', '2024-12-05 00:00:00', 7, 'ALL', 2, 1, 1);
insert into tour_orders (id, country, city, hotel, check_in_date, rest_duration, meals_type, adult_count, child_count, tourist_id)
values (2, 'Thailand', 'Pattaya', 'Holiday Inn', '2024-07-15 00:00:00', 10, 'BB', 1, 2, 2);
insert into tour_orders (id, country, city, hotel, check_in_date, rest_duration, meals_type, adult_count, child_count, tourist_id)
values (3, ' Czechia', 'Praga', 'Four Seasons', '2024-09-01 00:00:00', 7, 'HB', 2, 0, 3);
insert into tour_orders (id, country, city, hotel, check_in_date, rest_duration, meals_type, adult_count, child_count, tourist_id)
values (4, 'Dominican', 'Punta cana', 'Hard Rock Hotel', '2024-10-25 00:00:00', 14, 'ALL', 2, 1, 1);

SELECT setval ('tour_orders_id_seq', (SELECT MAX(id) FROM tour_orders));

insert into execution_time (id, method_name, start_time, duration)
values (1, 'addOrder', '2024-05-07 12:25:52.763000', 98);
insert into execution_time (id, method_name, start_time, duration)
values (2, 'getOrderList', '2024-05-07 13:45:21.778000', 76);
insert into execution_time (id, method_name, start_time, duration)
values (3, 'getOrderByOrderId', '2024-05-07 15:29:36.253000', 23);

SELECT setval ('execution_time_id_seq', (SELECT MAX(id) FROM execution_time));