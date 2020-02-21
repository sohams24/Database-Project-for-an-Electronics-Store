
CREATE TABLE Customer (
  cid   VARCHAR(20),
  email VARCHAR(100) ,
  first_name VARCHAR(20),
  middle_name VARCHAR(20),
  last_name VARCHAR(20),
  password VARCHAR(20),
  apartment VARCHAR(20),
  street VARCHAR(20),
  city VARCHAR(20),
  bank_name VARCHAR(20),
  last_order INT DEFAULT 0,
  account_number VARCHAR(20),
  PRIMARY KEY (cid));
  
CREATE TABLE Product (
  pid VARCHAR(20),
  name VARCHAR(20),
  manufacturer VARCHAR(20),
  discription VARCHAR(100),
  price NUMERIC(8,2),
  PRIMARY KEY (pid)
);

CREATE TABLE Store (
  store_id VARCHAR(20),
  street VARCHAR(20),
  city VARCHAR(20),
  PRIMARY KEY (store_id)
);

CREATE TABLE Warehouse (
  wh_id VARCHAR(20),
  street VARCHAR(20),
  city VARCHAR(20),
  PRIMARY KEY (wh_id)
);

CREATE TABLE online_order (
    oid VARCHAR(20),
    cid VARCHAR(20),
    order_time TIMESTAMP,
    PRIMARY KEY (oid),
    FOREIGN KEY (cid) REFERENCES Customer
);

CREATE TABLE product_categories (
    cat_name VARCHAR(20),
    pid VARCHAR(20),
    PRIMARY KEY (pid, cat_name),
    FOREIGN KEY (pid) REFERENCES Product
);

CREATE TABLE Order_Details (
    oid VARCHAR(20),
    pid VARCHAR(20),
    wh_id VARCHAR(20),
    quantity NUMERIC(4,0),
    tracking_id VARCHAR(20),
    shipper_name VARCHAR(20),
    delivery_status VARCHAR(20),
    delivery_date DATE,
    PRIMARY KEY(oid, pid),
    FOREIGN KEY(oid) REFERENCES Online_Order,
    FOREIGN KEY(pid) REFERENCES Product,
    FOREIGN KEY(wh_id) REFERENCES Warehouse
);

CREATE TABLE In_store_purchase (
    cid VARCHAR(50),
    pid VARCHAR(20),
    store_id VARCHAR(20),
    quantity NUMERIC(4,0),
    timestamp TIMESTAMP,
    payment_mode VARCHAR(20),
    PRIMARY KEY(cid, pid, store_id),
    FOREIGN KEY(cid) REFERENCES Customer,
    FOREIGN KEY(pid) REFERENCES Product,
    FOREIGN KEY(store_id) REFERENCES Store
)

CREATE TABLE Warehouse_Inventory (
    wh_id VARCHAR(20),
    pid VARCHAR(20),
    quantity NUMERIC(4,0),
    PRIMARY KEY(wh_id, pid),
    FOREIGN KEY(pid) REFERENCES Product,
    FOREIGN KEY(wh_id) REFERENCES Warehouse
);

CREATE TABLE Store_Inventory (
    store_id VARCHAR(20),
    pid VARCHAR(20),
    quantity NUMERIC(4,0),
    PRIMARY KEY(store_id, pid),
    FOREIGN KEY(pid) REFERENCES Product,
    FOREIGN KEY(store_id) REFERENCES Store
);

CREATE TABLE Warehouse_Reorders (
    wh_id VARCHAR(20),
    pid VARCHAR(20),
    quantity NUMERIC(4,0),
    timestamp TIMESTAMP,
    delivery_status VARCHAR(20),
    PRIMARY KEY(wh_id, pid),
    FOREIGN KEY(pid) REFERENCES Product,
    FOREIGN KEY(wh_id) REFERENCES Warehouse
);

CREATE TABLE Store_Reorders (
    store_id VARCHAR(20),
    pid VARCHAR(20),
    quantity NUMERIC(4,0),
    timestamp TIMESTAMP,
    delivery_status VARCHAR(20),
    PRIMARY KEY(store_id, pid),
    FOREIGN KEY(pid) REFERENCES Product,
    FOREIGN KEY(store_id) REFERENCES Store
);

--autoincrement customer id
CREATE SEQUENCE Cust_sequence
START WITH 1
INCREMENT BY 1 
NOCACHE 
NOCYCLE; 
/
CREATE OR REPLACE TRIGGER Cust_trigger 
BEFORE INSERT ON customer
FOR EACH ROW 
BEGIN 
 SELECT 'C' || ltrim(to_char(Cust_sequence.NEXTVAL, '99999'))
 INTO :new.cid
 FROM dual; 
END; 
/

--autoincrement tracking id
CREATE SEQUENCE tracking_sequence
START WITH 1
INCREMENT BY 1 
NOCACHE 
NOCYCLE; 
/
CREATE OR REPLACE TRIGGER tracking_trigger 
BEFORE INSERT ON order_details
FOR EACH ROW 
BEGIN 
 SELECT 'FDX' || ltrim(to_char(tracking_sequence.NEXTVAL, '99999'))
 INTO :new.tracking_id
 FROM dual; 
END; 
/

--query to filter list of products displayed on customer page
WITH availibility AS
(SELECT pid , SUM(Quantity) stock
FROM warehouse_inventory
GROUP BY pid)
SELECT DISTINCT name, manufacturer, discription, price, stock
FROM product NATURAL JOIN availibility NATURAL JOIN product_categories
WHERE cat_name = 'Smart Phones';


--query to update warehouse inventory after order is placed
update warehouse_inventory 
set quantity = quantity - 2
where wh_id = 'WH1' AND pid = 'P1';

--query to view past orders of a customer
SELECT oid, name, quantity, tracking_id, shipper_name, delivery_status, delivery_date, price Unit_Price, price*quantity amount
FROM online_order natural join order_details natural join product
WHERE cid = 'C1';

--query to update the last order field in customer
UPDATE customer
set last_order = last_order+1
where cid = 'C2';

--query to view sales data for manufacturer
SELECT name, quantity, manufacturer, price, order_date, cat_name
FROM online_order NATURAL JOIN order_details NATURAL JOIN product NATURAL JOIN product_categories

--sales based on manufacturer
SELECT manufacturer, sum(price*quantity) sales
FROM online_order NATURAL JOIN order_details NATURAL JOIN product NATURAL JOIN product_categories
GROUP BY manufacturer;

--sales based on product
SELECT name, sum(price*quantity) sales
FROM online_order NATURAL JOIN order_details NATURAL JOIN product NATURAL JOIN product_categories
GROUP BY name;

--sales based on category
SELECT cat_name, sum(price*quantity) sales
FROM online_order NATURAL JOIN order_details NATURAL JOIN product NATURAL JOIN product_categories
GROUP BY cat_name;

