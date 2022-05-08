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
    id              bigserial primary key,
    title           varchar(255),
    price           numeric(8, 2) not null,
    category_id     bigint not null references categories (id),
    picture_link    varchar(65535),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into products (title, price, category_id, picture_link)
values ('Chicken', 0.20, 1, 'https://avatars.mds.yandex.net/i?id=24f464b431413a6a08532f060b0bafba-5559510-images-thumbs&n=13&exp=1'),
       ('Bread', 0.20, 5, 'https://avatars.mds.yandex.net/i?id=3f7061f41f12b85220af2db2118e4e3b-5882870-images-thumbs&n=13&exp=1'),
       ('Cheese', 0.20, 3, 'https://avatars.mds.yandex.net/i?id=a41fc330095e98877326b2824137c33c-4077354-images-thumbs&n=13&exp=1'),
       ('Beef', 0.40, 1, 'https://yandex-images.clstorage.net/eom5U1172/4b4888ef/Wr-g9d-FdF5Mf5u20bB01d0maR8PZ7pKR_GUVXTUmqi2xMHy45n-zqnZwRecbrAFM6qNvTpv3lBxSIRLOBmm7t_7leELRMW6vN-LtuFg5WVNVtmf_-f7CjfUESTBRBv9QwBQl2afCCnrjqL1ehknQqELSP2_Wo-EoHzynajMo5j6fLPl_ObVyFzum-ITszTFLOc8jM_03pjiEbZyYiT4aIBxkYGAn9rZOywBpzt5dFKR-OvNTyhvUTP_4b1d3VE4iM5CpD10t6ss_fuy0oIm1O2nzs08RM6ZkzE3YFMUiGnAwTFgA11am0i-cgVKOvZgs3vJnF_6fDNBz-M8v44hyipP4pQtZEfbrC1rsCFBZzad59y-3SStidMTJoABxFn74QPBcMC9a4raPrBWG6nF8hJoyX49qL7hwe0T_m58c1sKH8HlPjamGf69ubNhAyYUPQb_ft2XnvvQUJSRI7RrimLC4rChfpvomJzwZui7dQCzS0p_Lblc0eBPwM_-LqIKOt5wVt121iicTFqgUWCGFVznXn9-pLx48FEWIOPEa7gSUaOwUO6aywg9g9YZiXUgYTh4ve9LjCChDCJ8rZ_R2Yi8g_YNxkW63C34IxLRNpVNRO58bHSsqPMh5nEyNZnYYtDjMyKcmvnpbKMW6rimMXDKy72vCk7QMB6h7k-toQi4fZGEj2QmGM6M-SEQA8ak7RctH8wmnyui4qVCsMTq2iAys1PwzAq7WB3T1tt5NmADe4iMbriuIOJdMd6fj7Gr-hwTpg6mdvgMjquiwIJXNw8UTK38tjw4UxG3YkEFqkpDIYGx8e-46sqOsyfoS6WgUMvafb56LXDxzqOMrlxyG4ssIgRdJMf4vB7Ls5FDRgW9tu7tLfavOlFwBzLzpNpqkkCjYJOtmomrvRJkqqqHsEHZ2_08iL0Cgg3Dv_-esMlKn4IHjdY1WC_veqNQADb3_vUNH39UnPnDc0YwwleIM'),
       ('Milk', 1.00, 3, 'https://avatars.mds.yandex.net/i?id=5e1d42b6f3e6688d9baba54c72cc0c76-5865828-images-thumbs&n=13&exp=1'),
       ('Salmon', 0.50, 6, 'https://yandex-images.clstorage.net/eom5U1172/4b4888ef/Wr-g9d-FdF5Mf5u20bB01d0maR8PZ7pKR_GUVXER-t22YbGy46m7m5nM5IKsq-VVFr-Y3X96zkAUCDFLCBmmXs-b9eG7RMW6vN-LtuFg5WVNVtmf_-f7CjfUESTBRBv9QwBQl2afCCnrjqL1ehknQqELSP2_Wo-EoHzynajMo5j6fLPl_ObVyFzum-ITszTFLOc8jM_03pjiEbZyYiT4aIBxkYGAn9rZOywBpzt5dFKR-OvNTyhvUTP_4b1d3VE4iM5CpD10t6ss_fuy0oIm1O2nzs08RM6ZkzE3YFMUiGnAwTFgA11am0i-cgVKOvZgs3vJnF_6fDNBz-M8v44hyipP4pQtZEfbrC1rsCFBZzad59y-3SStidMTJoABxFn74QPBcMC9a4raPrBWG6nF8hJoyX49qL7hwe0T_m58c1sKH8HlPjamGf69ubNhAyYUPQb_ft2XnvvQUJSRI7RrimLC4rChfpvomJzwZui7dQCzS0p_Lblc0eBPwM_-LqIKOt5wVt121iicTFqgUWCGFVznXn9-pLx48FEWIOPEa7gSUaOwUO6aywg9g9YZiXUgYTh4ve9LjCChDCJ8rZ_R2Yi8g_YNxkW63C34IxLRNpVNRO58bHSsqPMh5nEyNZnYYtDjMyKcmvnpbKMW6rimMXDKy72vCk7QMB6h7k-toQi4fZGEj2QmGM6M-SEQA8ak7RctH8wmnyui4qVCsMTq2iAys1PwzAq7WB3T1tt5NmADe4iMbriuIOJdMd6fj7Gr-hwTpg6mdvgMjquiwIJXNw8UTK38tjw4UxG3YkEFqkpDIYGx8e-46sqOsyfoS6WgUMvafb56LXDxzqOMrlxyG4ssIgRdJMf4vB7Ls5FDRgW9tu7tLfavOlFwBzLzpNpqkkCjYJOtmomrvRJkqqqHsEHZ2_08iL0Cgg3Dv_-esMlKn4IHjdY1WC_veqNQADb3_vUNH39UnPnDc0YwwleIM'),
       ('Trout', 0.1, 6, 'https://avatars.mds.yandex.net/i?id=e5d68a0d4e63be0709f958a5c9602fbd-4475612-images-thumbs&n=13&exp=1'),
       ('Avocado ', 0.15, 4, 'https://yandex-images.clstorage.net/eom5U1172/4b4888ef/Wr-g9d-FdF5Mf5u20bB01d0maR8PZ7pKR_GUVXF06q12VKQHtgnejmnZESKsrvVVM_q9_RovzhUxGNT7WBmm7t_7lbELRMW6vN-LtuFg5WVNVtmf_-f7CjfUESTBRBv9QwBQl2afCCnrjqL1ehknQqELSP2_Wo-EoHzynajMo5j6fLPl_ObVyFzum-ITszTFLOc8jM_03pjiEbZyYiT4aIBxkYGAn9rZOywBpzt5dFKR-OvNTyhvUTP_4b1d3VE4iM5CpD10t6ss_fuy0oIm1O2nzs08RM6ZkzE3YFMUiGnAwTFgA11am0i-cgVKOvZgs3vJnF_6fDNBz-M8v44hyipP4pQtZEfbrC1rsCFBZzad59y-3SStidMTJoABxFn74QPBcMC9a4raPrBWG6nF8hJoyX49qL7hwe0T_m58c1sKH8HlPjamGf69ubNhAyYUPQb_ft2XnvvQUJSRI7RrimLC4rChfpvomJzwZui7dQCzS0p_Lblc0eBPwM_-LqIKOt5wVt121iicTFqgUWCGFVznXn9-pLx48FEWIOPEa7gSUaOwUO6aywg9g9YZiXUgYTh4ve9LjCChDCJ8rZ_R2Yi8g_YNxkW63C34IxLRNpVNRO58bHSsqPMh5nEyNZnYYtDjMyKcmvnpbKMW6rimMXDKy72vCk7QMB6h7k-toQi4fZGEj2QmGM6M-SEQA8ak7RctH8wmnyui4qVCsMTq2iAys1PwzAq7WB3T1tt5NmADe4iMbriuIOJdMd6fj7Gr-hwTpg6mdvgMjquiwIJXNw8UTK38tjw4UxG3YkEFqkpDIYGx8e-46sqOsyfoS6WgUMvafb56LXDxzqOMrlxyG4ssIgRdJMf4vB7Ls5FDRgW9tu7tLfavOlFwBzLzpNpqkkCjYJOtmomrvRJkqqqHsEHZ2_08iL0Cgg3Dv_-esMlKn4IHjdY1WC_veqNQADb3_vUNH39UnPnDc0YwwleIM'),
       ('Carrot ', 0.60, 2, 'https://yandex-images.clstorage.net/eom5U1172/4b4888ef/Wr-g9d-FdF5Mf5u20bB01d0maR8PZ7pKR_GUVXFE722DBNSnhhnOXonZ5CLsDrVQE6qNnU9K7iUkfZQOOBmmDj-rRaGLRMW6vN-LtuFg5WVNVtmf_-f7CjfUESTBRBv9QwBQl2afCCnrjqL1ehknQqELSP2_Wo-EoHzynajMo5j6fLPl_ObVyFzum-ITszTFLOc8jM_03pjiEbZyYiT4aIBxkYGAn9rZOywBpzt5dFKR-OvNTyhvUTP_4b1d3VE4iM5CpD10t6ss_fuy0oIm1O2nzs08RM6ZkzE3YFMUiGnAwTFgA11am0i-cgVKOvZgs3vJnF_6fDNBz-M8v44hyipP4pQtZEfbrC1rsCFBZzad59y-3SStidMTJoABxFn74QPBcMC9a4raPrBWG6nF8hJoyX49qL7hwe0T_m58c1sKH8HlPjamGf69ubNhAyYUPQb_ft2XnvvQUJSRI7RrimLC4rChfpvomJzwZui7dQCzS0p_Lblc0eBPwM_-LqIKOt5wVt121iicTFqgUWCGFVznXn9-pLx48FEWIOPEa7gSUaOwUO6aywg9g9YZiXUgYTh4ve9LjCChDCJ8rZ_R2Yi8g_YNxkW63C34IxLRNpVNRO58bHSsqPMh5nEyNZnYYtDjMyKcmvnpbKMW6rimMXDKy72vCk7QMB6h7k-toQi4fZGEj2QmGM6M-SEQA8ak7RctH8wmnyui4qVCsMTq2iAys1PwzAq7WB3T1tt5NmADe4iMbriuIOJdMd6fj7Gr-hwTpg6mdvgMjquiwIJXNw8UTK38tjw4UxG3YkEFqkpDIYGx8e-46sqOsyfoS6WgUMvafb56LXDxzqOMrlxyG4ssIgRdJMf4vB7Ls5FDRgW9tu7tLfavOlFwBzLzpNpqkkCjYJOtmomrvRJkqqqHsEHZ2_08iL0Cgg3Dv_-esMlKn4IHjdY1WC_veqNQADb3_vUNH39UnPnDc0YwwleIM'),
       ('Cucumber ', 0.70, 2, 'https://avatars.mds.yandex.net/i?id=2a0000017a01744fc60b021d99741af5a726-3095911-images-thumbs&n=13&exp=1'),
       ('Apple ', 0.80, 4, 'https://avatars.mds.yandex.net/i?id=d20fd6f86b1af498f3e82713272721b0-4504894-images-thumbs&n=13&exp=1');

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









