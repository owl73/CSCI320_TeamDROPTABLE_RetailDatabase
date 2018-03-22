

-- Getting the inventory below some amount

SELECT Store_ID, UPC, Inventory
FROM Stock
Where Inventory < 10;

-- Getting lowest inventory

SELECT Store_ID, UPC, Inventory
FROM Stock
Where Inventory in (SELECT min(Inventory)
                    from Stock);

-- Getting lowest inventory from each store

WITH (SELECT ID from Store) as Stores
SELECT Store_ID, UPC, Inventory
FROM Stock
WHERE UPC in (SELECT min(Inventory))

-- Checking specific stores

SELECT UPC
FROM Stock
Where Inventory < 10
    AND Store_ID = 123;

-- Getting revenue from specific products

WITH laptops as (Select UPC from Laptops)
SELECT sum(Quantity * Price)
from Bought_Products
Where Product_UPC in laptops
-- OR
SELECT sum(Quantity * Price)
from Bought_Products
Where Product_UPC = 123456789011

