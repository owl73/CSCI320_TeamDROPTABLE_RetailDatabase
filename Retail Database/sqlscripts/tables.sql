CREATE TABLE Customer (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(50),
    Email_Address VARCHAR(50),
    Date_Joined TIMESTAMP,
    Phone_Number_1 VARCHAR(12),
    Phone_Number_2 VARCHAR(12));

CREATE TABLE Vendor (
    Name VARCHAR(50) PRIMARY KEY,
    Phone VARCHAR(12),
    Website VARCHAR(50));

CREATE TABLE Brand (
    Name VARCHAR(50) PRIMARY KEY,
    Vendor VARCHAR(50),
    FOREIGN KEY(Vendor) REFERENCES Vendor(Name));

CREATE TABLE Store (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Phone_Number VARCHAR(12),
    Date_Opened DATE,
    Budget NUMERIC(15,2),
    Address VARCHAR(50),
    City VARCHAR(20),
    State VARCHAR(2),
    Zip_code VARCHAR(6));

CREATE TABLE Product (
    UPC INT PRIMARY KEY,
    Description VARCHAR(100),
    Brand VARCHAR(50),
    FOREIGN KEY(Brand) REFERENCES Brand(Name));

CREATE TABLE Computer (
    UPC INT PRIMARY KEY,
    Ram_Size INT,
    Processor VARCHAR(50),
    Hard_Disk VARCHAR(10),
    FOREIGN KEY(UPC) REFERENCES Product(UPC));

CREATE TABLE Desktop (
    UPC INT PRIMARY KEY,
    Power_Supply VARCHAR(10),
    Graphics_Card VARCHAR(10),
    FOREIGN KEY(UPC) REFERENCES Computer(UPC));

CREATE TABLE Monitor (
    UPC INT PRIMARY KEY,
    Screen_size INT,
    Resolution VARCHAR(10),
    PPI INT,
    Panel_Type VARCHAR(20),
    FOREIGN KEY(UPC) REFERENCES Product(UPC));

CREATE TABLE Laptop ( 
    UPC INT PRIMARY KEY,
    Battery_Life INT,
    Screen_Size INT,
    FOREIGN KEY(UPC) REFERENCES Computer(UPC));

CREATE TABLE Shipment (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Date_placed DATE,
    Date_arrived DATE,
    Cost NUMERIC(15,2), 
    Tracking_number VARCHAR(30),
    Vendor_name VARCHAR(50),
    UPC INT,
    Quantity INT,
    Store_ID INT,
    FOREIGN KEY(Vendor_name) REFERENCES Vendor (Name),
    FOREIGN KEY(UPC) REFERENCES Product(UPC),
    FOREIGN KEY(Store_ID) REFERENCES Store(ID));

CREATE TABLE Purchase (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    time_completed TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    Total NUMERIC(10,2),
    Payment_Method VARCHAR(10), 
    Customer_ID INT,
    Store_ID INT,
    FOREIGN KEY(Store_ID) REFERENCES Store(ID),
    FOREIGN KEY(Customer_ID) REFERENCES Customer(ID));

CREATE TABLE Bought_Products(
    Purchase_ID INT PRIMARY KEY,
    Product_UPC INT PRIMARY KEY ,
    Quantity INT,
    Price NUMERIC(15,2),
    FOREIGN KEY(Product_UPC) REFERENCES Product(UPC),
    FOREIGN KEY(Purchase_ID) REFERENCES Purchase(ID));


CREATE TABLE Stock ( 
    Store_ID INT PRIMARY KEY,
    UPC INT PRIMARY KEY,
    Inventory INT, 
    Listed_Price NUMERIC(10,2),
    FOREIGN KEY(Store_ID) REFERENCES Store(ID),
    FOREIGN KEY(UPC) REFERENCES Product(UPC));
