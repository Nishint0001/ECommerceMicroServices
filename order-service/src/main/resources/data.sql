INSERT INTO orderx (order_status, price) VALUES
('PENDING', 49.99),
('CONFIRMED', 89.50),
('CANCELLED', 120.00),
('CONFIRMED', 299.99),
('PENDING', 15.75),
('CONFIRMED', 74.25),
('CANCELLED', 34.10),
('PENDING', 105.00),
('CONFIRMED', 56.60);

-- Insert into order_item (your OrderItem table)
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(101, 2, 1),
(102, 1, 1),
(103, 3, 2),
(104, 1, 3),
(105, 4, 4),
(106, 1, 5);
