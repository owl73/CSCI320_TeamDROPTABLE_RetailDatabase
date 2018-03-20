CREATE TABLE Product ( 
    UPC INT PRIMARY KEY, 
    Description VARCHAR(20), 
    Brand VARCHAR(10));

CREATE TABLE Laptop ( 
    UPC INT PRIMARY KEY, 
    Battery_Life INT);
    
CREATE TABLE Brand ( 
    Name VARCHAR(10) PRIMARY KEY,
    Vendor VARCHAR(20));

CREATE TABLE Order ( 
    ID INT PRIMARY KEY, 
    Date_placed TIMESTAMP, 
    Date_arrived TIMESTAMP, 
    Cost NUMERIC(15,2), 
    Tracking_number INT, 
    Vendor_name VARCHAR(20), 
    UPC INT, 
    Quantity INT);

CREATE TABLE Transaction ( 
    ID INT PRIMARY KEY, 
    Date TIMESTAMP, 
    Total NUMERIC(10,2), 
    Payment_Method VARCHAR(10), 
    Customer_ID INT, 
    Store_ID INT);

CREATE TABLE Bought_Products( 
    Product_UPC INT, 
    Transaction_ID INT, 
    Quantity INT, 
    Price NUMERIC(15,2));

CREATE TABLE Store ( 
    ID INT PRIMARY KEY, 
    Phone_Number VARCHAR(12), 
    Date_Opened TIMESTAMP, 
    Budget NUMERIC(15,2),
    Address VARCHAR(30));

CREATE TABLE Stock ( 
    Store_ID INT, 
    UPC INT, 
    Inventory INT, 
    Listed_Price NUMERIC(10,2));

CREATE TABLE Desktop ( 
    UPC INT PRIMARY KEY, 
    Power_Supply VARCHAR(8), 
    Graphics_Card VARCHAR(10));

CREATE TABLE Computer ( 
    UPC INT PRIMARY KEY, 
    Ram_Size INT, 
    Processor VARCHAR(10), 
    Hard_Disk VARCHAR(8));

CREATE TABLE TV ( 
    UPC INT PRIMARY KEY, 
    Smart_Features VARCHAR(15), 
    Mount_Type VARCHAR(15));

CREATE TABLE Screen ( 
    UPC INT PRIMARY KEY, 
    Size INT, 
    Resolution VARCHAR(10), 
    PPI INT, 
    Panel_Type VARCHAR(10));

CREATE TABLE Customer ( 
    ID INT PRIMARY KEY, 
    Name VARCHAR(10), 
    Email_Address VARCHAR(30), 
    Date_Joined TIMESTAMP, 
    Phone_Number_1 VARCHAR(12), 
    Phone_Number_2 VARCHAR(12));

CREATE TABLE Vendor ( 
    Name VARCHAR(20) PRIMARY KEY, 
    Phone VARCHAR(12), 
    Website VARCHAR(20));
