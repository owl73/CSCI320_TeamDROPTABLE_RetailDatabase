CREATE TABLE Customer (
    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(10),
    Email_Address VARCHAR(30),
    Date_Joined TIMESTAMP,
    Phone_Number_1 VARCHAR(12),
    Phone_Number_2 VARCHAR(12));

CREATE TABLE Vendor (
    Name VARCHAR(20) PRIMARY KEY,
    Phone VARCHAR(12),
    Website VARCHAR(20));

CREATE TABLE Brand (
    Name VARCHAR(10) PRIMARY KEY,
    Vendor VARCHAR(20),
    FOREIGN KEY(Vendor) REFERENCES Vendor);

CREATE TABLE Store (
    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Phone_Number VARCHAR(12),
    Date_Opened DATE,
    Budget NUMERIC(15,2),
    Address VARCHAR(30));

CREATE TABLE Product (
    UPC INT NOT NULL PRIMARY KEY,
    Description VARCHAR(20),
    Brand VARCHAR(10),
    FOREIGN KEY(Brand) REFERENCES Brand,);

CREATE TABLE Computer (
    UPC INT PRIMARY KEY,
    Ram_Size INT,
    Processor VARCHAR(10),
    Hard_Disk VARCHAR(8));

CREATE TABLE Desktop (
    UPC INT PRIMARY KEY,
    Power_Supply VARCHAR(8),
    Graphics_Card VARCHAR(10));

CREATE TABLE Monitor (
    UPC INT PRIMARY KEY,
    Size INT,
    Resolution VARCHAR(10),
    PPI INT,
    Panel_Type VARCHAR(10));

CREATE TABLE Laptop ( 
    UPC INT NOT NULL PRIMARY KEY,
    Battery_Life INT
    Screen_Size INT);

CREATE TABLE Shipment (
    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Date_placed DATE,
    Date_arrived DATE,
    Cost NUMERIC(15,2), 
    Tracking_number VARCHAR(30),
    Vendor_name VARCHAR(20),
    UPC INT,
    Quantity INT,
    FOREIGN KEY(Vendor_name) REFERENCES Vendor,
    FOREIGN KEY(UPC) REFERENCES Product);

CREATE TABLE Purchase (
    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Date TIMESTAMP NOT NULL DEFAULT ,
    Total NUMERIC(10,2), 
    Payment_Method VARCHAR(10), 
    Customer_ID INT,
    Store_ID INT,
    FOREIGN KEY(Store_ID) REFERENCES Store,
    FOREIGN KEY(Customer_ID) REFERENCES Customer);

CREATE TABLE Bought_Products( 
    Product_UPC INT FOREIGN KEY,
    Purchase_ID INT FOREIGN KEY,
    Quantity INT, 
    Price NUMERIC(15,2),
    FOREIGN KEY(Product_UPC) REFERENCES Product,
    FOREIGN KEY(Purchase_ID) REFERENCES Purchase);


CREATE TABLE Stock ( 
    Store_ID INT,
    UPC INT,
    Inventory INT, 
    Listed_Price NUMERIC(10,2)
    FOREIGN KEY(Store_ID) REFERENCES Store,
    FOREIGN KEY(UPC) REFERENCES Product);



