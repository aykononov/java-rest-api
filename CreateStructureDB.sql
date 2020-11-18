/* таблица Продукты */
DROP TABLE products PURGE;
/
CREATE TABLE products
(
  product_id INT NOT NULL,
  product_name VARCHAR2(128) NOT NULL,
	PRIMARY KEY (product_id)
);
/

/* таблица Цены */
DROP TABLE prices PURGE;
/
CREATE TABLE prices
(
  price_id INT NOT NULL,
  price VARCHAR2(128) NOT NULL,
	price_date DATE DEFAULT SYSDATE,
	PRIMARY KEY (price_id)
);
/
/* проверка */
SELECT * 
  FROM products pd, 
	     prices pr 
 WHERE pd.product_id = pr.price_id;
