/* таблица Продукты */
DROP TABLE products PURGE;
/
CREATE TABLE products
(
  id   NUMBER(10,0) NOT NULL,
  name VARCHAR2(255),
	PRIMARY KEY (id)
);
/

/* таблица Цены */
DROP TABLE prices PURGE;
/
CREATE TABLE prices
(
  id         NUMBER(10,0) NOT NULL,
  price      NUMBER,
	pdate      DATE,
	product_id NUMBER(10,0),
	PRIMARY KEY (id),
	CONSTRAINT fk_product_id 
	FOREIGN KEY (product_id)
  REFERENCES PRODUCTS (id)
);
/
/* проверка */
SELECT * 
  FROM products pd, 
	     prices   pr 
 WHERE pd.id = pr.product_id(+);
/
SELECT pd.name, COUNT(*) AS cnt 
  FROM products pd, 
	     prices   pr 
 WHERE pd.id = pr.product_id(+)
 GROUP BY pd.name;
