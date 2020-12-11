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
	CONSTRAINT FK_PRODUCT_ID FOREIGN KEY (PRODUCT_ID)
  REFERENCES PRODUCTS (ID)
);
/
/* проверка */
SELECT * 
  FROM products pd, 
	     prices   pr 
 WHERE pd.id = pr.product_id(+)
 ORDER BY pr.id;
/

