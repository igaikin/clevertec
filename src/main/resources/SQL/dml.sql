--TRUNCATE cards;
--TRUNCATE products;

INSERT INTO cards (id, discount)
VALUES (1111, 3.0),
       (2222, 5.0),
       (3333, 7.0),
       (4444, 4.0),
       (5555, 10.0);

INSERT INTO products (id, description, cost, promo)
VALUES (1, 'Milk', 2.99, false),
       (2, 'Butter', 4.99, false),
       (3, 'Sugar', 2.49, false),
       (4, 'Bread', 3.49, true),
       (5, 'French Bread', 1.99, false),
       (6, 'Potato', 2.99, false),
       (7, 'Sweet Paper', 1.69, false),
       (8, 'Cucumber Organic', 2.19, false),
       (9, 'Tomatoes', 2.99, false),
       (10, 'Bananas', 0.99, false),
       (11, 'Orange', 1.49, false),
       (12, 'Apple', 0.99, true),
       (13, 'Beef Meat', 3.99, false),
       (14, 'Pork Meat', 4.49, false),
       (15, 'Turkey', 2.34, true),
       (16, 'Chicken', 1.99, false),
       (17, 'Cola', 1.99, true),
       (18, 'Mineral Water', 0.69, true),
       (19, 'Juice', 4.69, true),
       (20, 'Coffee', 10.99, false),
       (21, 'Tea', 2.99, false);