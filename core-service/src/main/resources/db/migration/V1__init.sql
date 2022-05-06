create table categories
(
    id          bigserial primary key,
    title       varchar(255)  not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into categories (title)
values ('Meat'),
       ('Vegetables'),
       ('Dairy'),
       ('Fruits'),
       ('Bakery'),
       ('Fish');

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       numeric(8, 2) not null,
    category_id bigint not null references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into products (title, price, category_id)
values ('Chicken', 0.20, 1),
       ('Bread', 0.20, 5),
       ('Cheese', 0.20, 3),
       ('Beef', 0.40, 1),
       ('Milk', 1.00, 3),
       ('Salmon', 0.50, 6),
       ('Trout', 0.1, 6),
       ('Avocado ', 0.15, 4),
       ('Carrot ', 0.60, 2),
       ('Cucumber ', 0.70, 2),
       ('Apple ', 0.80, 4);

create table orders
(
    id          bigserial primary key,
    username    varchar(255)  not null,
    total_price numeric(8, 2)  not null,
    city        varchar(255),
    street      varchar(255),
    house       int,
    apartment   int,
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









