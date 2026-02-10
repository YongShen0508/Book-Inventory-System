-- =====================================================
-- Sample Data for Book Inventory System
-- =====================================================

-- Insert Books
INSERT INTO book (title, author, genre, publication_date, price, stock_quantity) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', '1925-04-10', 10.99, 50),
('To Kill a Mockingbird', 'Harper Lee', 'Classic', '1960-07-11', 7.99, 30),
('1984', 'George Orwell', 'Dystopian', '1949-06-08', 8.99, 40),
('Pride and Prejudice', 'Jane Austen', 'Romance', '1813-01-28', 6.99, 20),
('The Catcher in the Rye', 'J.D. Salinger', 'Classic', '1951-07-16', 9.99, 25),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy', '1937-09-21', 12.99, 15),
('Fahrenheit 451', 'Ray Bradbury', 'Dystopian', '1953-10-19', 11.99, 35),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 'Fantasy', '1997-06-26', 14.99, 100),
('The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy', '1954-07-29', 19.99, 45),
('Animal Farm', 'George Orwell', 'Political Fiction', '1945-08-17', 7.49, 60),
('Brave New World', 'Aldous Huxley', 'Dystopian', '1932-08-30', 9.49, 55),
('The Chronicles of Narnia', 'C.S. Lewis', 'Fantasy', '1950-10-16', 15.99, 38),
('Wuthering Heights', 'Emily Brontë', 'Romance', '1847-12-01', 8.49, 22),
('Jane Eyre', 'Charlotte Brontë', 'Romance', '1847-10-16', 8.99, 28),
('Moby-Dick', 'Herman Melville', 'Adventure', '1851-10-18', 11.49, 18),
('The Odyssey', 'Homer', 'Epic', '800-01-01', 13.99, 42),
('The Divine Comedy', 'Dante Alighieri', 'Epic', '1320-01-01', 16.99, 25),
('Don Quixote', 'Miguel de Cervantes', 'Classic', '1605-01-16', 12.49, 30),
('Hamlet', 'William Shakespeare', 'Drama', '1603-01-01', 9.99, 50),
('Romeo and Juliet', 'William Shakespeare', 'Drama', '1597-01-01', 8.99, 45),
('The Alchemist', 'Paulo Coelho', 'Fiction', '1988-05-01', 10.49, 70),
('The Little Prince', 'Antoine de Saint-Exupéry', 'Fiction', '1943-04-06', 7.99, 80),
('Life of Pi', 'Yann Martel', 'Adventure', '2001-09-11', 11.99, 35),
('The Kite Runner', 'Khaled Hosseini', 'Fiction', '2003-05-29', 12.99, 48),
('Gone with the Wind', 'Margaret Mitchell', 'Historical Fiction', '1936-06-30', 14.49, 26);

-- Insert Customers
INSERT INTO customer (name, email, phone, address) VALUES
('John Doe', 'john.doe@email.com', '555-0101', '123 Main St, New York, NY 10001'),
('Jane Smith', 'jane.smith@email.com', '555-0102', '456 Oak Ave, Los Angeles, CA 90001'),
('Michael Johnson', 'michael.j@email.com', '555-0103', '789 Pine Rd, Chicago, IL 60601'),
('Emily Brown', 'emily.brown@email.com', '555-0104', '321 Elm St, Houston, TX 77001'),
('David Wilson', 'david.wilson@email.com', '555-0105', '654 Maple Dr, Phoenix, AZ 85001'),
('Sarah Davis', 'sarah.davis@email.com', '555-0106', '987 Cedar Ln, Philadelphia, PA 19101'),
('James Miller', 'james.miller@email.com', '555-0107', '147 Birch Ave, San Antonio, TX 78201'),
('Lisa Anderson', 'lisa.anderson@email.com', '555-0108', '258 Spruce St, San Diego, CA 92101'),
('Robert Taylor', 'robert.taylor@email.com', '555-0109', '369 Walnut Rd, Dallas, TX 75201'),
('Jennifer Thomas', 'jennifer.thomas@email.com', '555-0110', '741 Cherry Ct, San Jose, CA 95101'),
('William Martinez', 'william.m@email.com', '555-0111', '852 Ash Dr, Austin, TX 78701'),
('Amanda Garcia', 'amanda.garcia@email.com', '555-0112', '963 Poplar Way, Jacksonville, FL 32099'),
('Christopher Lee', 'chris.lee@email.com', '555-0113', '159 Willow Ln, Fort Worth, TX 76101'),
('Jessica White', 'jessica.white@email.com', '555-0114', '357 Hickory St, Columbus, OH 43201'),
('Daniel Harris', 'daniel.harris@email.com', '555-0115', '486 Dogwood Ave, Charlotte, NC 28201');

-- Insert Orders
INSERT INTO `order` (customer_id, order_date, status, total_amount, order_at, expires_at) VALUES
(1, '2026-01-15 10:30:00', 'COMPLETED', 39.97, '2026-01-15 10:30:00', '2026-01-22 10:30:00'),
(2, '2026-01-15 14:20:00', 'COMPLETED', 52.96, '2026-01-15 14:20:00', '2026-01-22 14:20:00'),
(3, '2026-01-16 09:15:00', 'PROCESSING', 87.94, '2026-01-16 09:15:00', '2026-01-23 09:15:00'),
(4, '2026-01-16 11:45:00', 'SHIPPED', 45.98, '2026-01-16 11:45:00', '2026-01-23 11:45:00'),
(5, '2026-01-16 16:30:00', 'PENDING', 33.97, '2026-01-16 16:30:00', '2026-01-23 16:30:00'),
(6, '2026-01-17 08:00:00', 'CONFIRMED', 71.95, '2026-01-17 08:00:00', '2026-01-24 08:00:00'),
(7, '2026-01-17 10:20:00', 'COMPLETED', 28.98, '2026-01-17 10:20:00', '2026-01-24 10:20:00'),
(8, '2026-01-17 13:40:00', 'PROCESSING', 95.93, '2026-01-17 13:40:00', '2026-01-24 13:40:00'),
(1, '2026-01-17 15:50:00', 'PENDING', 44.97, '2026-01-17 15:50:00', '2026-01-24 15:50:00'),
(9, '2026-01-17 17:10:00', 'CONFIRMED', 62.96, '2026-01-17 17:10:00', '2026-01-24 17:10:00');

INSERT INTO order_item (order_id, book_id, quantity, subtotal, status) VALUES
(1, 1, 2, 21.98, 'DELIVERED'),
(1, 3, 2, 17.98, 'DELIVERED'),

(2, 8, 3, 44.97, 'DELIVERED'),
(2, 5, 1, 9.99, 'DELIVERED'),

(3, 9, 2, 39.98, 'SHIPPED'),
(3, 6, 1, 12.99, 'SHIPPED'),
(3, 15, 3, 34.47, 'SHIPPED'),

(4, 21, 2, 20.98, 'SHIPPED'),
(4, 22, 3, 23.97, 'SHIPPED'),

(5, 4, 2, 13.98, 'PENDING'),
(5, 2, 1, 7.99, 'PENDING'),
(5, 7, 1, 11.99, 'PENDING'),

(6, 12, 2, 31.98, 'COMPLETED'),
(6, 17, 1, 16.99, 'COMPLETED'),
(6, 23, 2, 23.98, 'COMPLETED'),

(7, 10, 2, 14.98, 'DELIVERED'),
(7, 11, 1, 9.49, 'DELIVERED'),

(8, 19, 3, 29.97, 'SHIPPED'),
(8, 20, 4, 35.96, 'SHIPPED'),
(8, 24, 2, 25.98, 'SHIPPED'),

(9, 14, 3, 26.97, 'PENDING'),
(9, 16, 1, 13.99, 'PENDING'),

(10, 25, 2, 28.98, 'COMPLETED'),
(10, 18, 1, 12.49, 'COMPLETED'),
(10, 13, 2, 16.98, 'COMPLETED');


