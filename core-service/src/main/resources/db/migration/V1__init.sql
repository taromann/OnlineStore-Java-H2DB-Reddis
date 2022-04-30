create table products
(
    id         bigserial primary key,
    title      varchar(255),
    price      numeric(8, 2) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Milk', 0.20),
       ('Bread', 0.20),
       ('Cheese', 0.20),
       ('Cheese2', 0.40),
       ('Cheese3', 1.00),
       ('Cheese4', 0.50),
       ('Cheese5', 0.1),
       ('Cheese6', 0.15),
       ('Cheese7', 0.60),
       ('Cheese8', 0.70),
       ('Cheese9', 0.80);

create table orders
(
    id          bigserial primary key,
    username    varchar(255)  not null,
    total_price numeric(8, 2)  not null,
    city     varchar(255),
    street     varchar(255),
    house     int not null,
    apartment     int not null,
    phone       varchar(255),
    status      int not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product numeric(8, 2)    not null,
    price             numeric(8, 2)    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

-- insert into orders (username, total_price, address, phone, status)
-- values ('bob', 200.00, 'address', '12345', 0);
--
-- insert into order_items (product_id, order_id, quantity, price_per_product, price)
-- values (1, 1, 2, 100.00, 200.00);









