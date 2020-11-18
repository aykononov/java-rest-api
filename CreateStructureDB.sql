DROP TABLE products PURGE;
/
CREATE TABLE products
(
  id INT NOT NULL,
  name VARCHAR2(128) NOT NULL,
	PRIMARY KEY (id)
);
/
DROP TABLE prices PURGE;
/
CREATE TABLE prices
(
  id INT NOT NULL,
  price VARCHAR2(128) NOT NULL,
	dates DATE DEFAULT SYSDATE,
	PRIMARY KEY (id)
);
/
DROP SEQUENCE id_seq;
/
CREATE SEQUENCE id_seq
MINVALUE 1
START WITH 1
INCREMENT BY 1;
/
DROP TRIGGER products_id_bri;
/
CREATE OR REPLACE TRIGGER products_id_bri
BEFORE INSERT ON products
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
WHEN (new.id IS NULL)
BEGIN
     SELECT id_seq.nextval
		 INTO :new.id
		 FROM dual;
END;
/
INSERT ALL
INTO products(NAME) VALUES('phone')
INTO products(NAME) VALUES('battery')
INTO products(NAME) VALUES('case')
SELECT *
FROM dual;
/
-- проверка
SELECT * FROM products;
