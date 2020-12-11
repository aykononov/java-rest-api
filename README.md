## springboot-hibernate-oracle-opencsv

Web приложение на базе фреймворка Spring Boot и Hibernate с использованием базы данных Oracle. 

### Технологии:
<small>

* Spring Boot - инструмент фреймворка Spring для написания приложений с минимальной конфигурацией (имеет встроенный контейнер сервлетов Tomcat по умолчанию);
* Spring Web - зависимость включает в себя все настройки Spring MVC и позволяет писать REST API без дополнительных настроек;
* Spring Data JPA - позволяет работать с SQL с помощью Java Persistence API, используя Spring Data и Hibernate;
* Lombok - библиотека для сокращения написания стандартного кода на java (геттеры, сеттеры и т.д.);
* OpenCSV - парсер CSV-файлов;
* Oracle - используемая БД [Oracle Database Express Edition (XE) download](https://www.oracle.com/database/technologies/xe-downloads.html "https://www.oracle.com/database/technologies/xe-downloads.html");
* Maven - фреймворк для автоматизации сборки проектов на основе описания их структуры в файлах на языке POM (англ. Project Object Model).

</small>

### Структура таблиц в БД.

```
1. Таблица товар. Хранит название товара.
   Колонки: id, name.
2. Таблица цена товара. Хранит цену на товар и дату с которой цена актуальная. 
   По каждому товару может быть несколько цен с разными датами.
   Колонки: id, price, date, product_id.
```
Таблицы создаются автоматически в БД при старте приложения.  
(также приложен файл со скриптами создания необходимых сущностей)

### Функционал приложения.

```
1. Приложение умеет загружать данные из csv-файла. 
    
    Путь директории с файлами настраивается в конфигурационном файле приложения. 
    Загрузка файла стартует при появлении файла в указанной директории.
    Пример формат данных csv-файла:

       product_id; product_name; price_id; price; price_date

   В логах отмечается факт старта обработки файла и результат обработки файла 
   с количеством обработанных записей(товаров и цен).

2. Приложение предоставляет следующие REST методы.

    POST `http://localhost:8081/loadProducts`: добавить продукты
    GET `http://localhost:8081/listProducts`: получить все продукты
    POST `http://localhost:8081/addProduct`: добавить один продукт
    GET `http://localhost:8081/findProductByName/name=`: найти по имени
    GET `http://localhost:8081/findProductById/id=`: найти продукт по индексу  
    DELETE `http://localhost:8081/deleteProductById/id=`: удалить по индексу
   
   Формат данных ответа - json.
```

### Применение

* Запустите приложение и перейдите по URL-адресу:   
    `http://localhost:8081/`  
 
* В POSTMAN используйте следующие URL-адреса для вызова методов контроллеров и просмотра взаимодействия с базой данных:
    
    POST `http://localhost:8081/loadProducts`: добавить продукты (в теле запроса JSON контент)
    ```json
    [
        {
            "id": 1,
            "name": "product1",
            "prices": [
                {
                    "id": 1,
                    "price": 100.11,
                    "pdate": "2020-11-30",
                    "productId": 1
                }
            ]
        },
        {
            "id": 2,
            "name": "product2",
            "prices": [
                {
                    "id": 2,
                    "price": 22.02,
                    "pdate": "2020-11-30",
                    "productId": 2
                }
            ]
        },
        {
            "id": 3,
            "name": "product3",
            "prices": [
                {
                    "id": 3,
                    "price": 3.03,
                    "pdate": "2020-11-30",
                    "productId": 3
                }
            ]
        }
    ]
     
    ```
  GET `http://localhost:8081/listProducts`: получить все продукты
  POST `http://localhost:8081/addProduct`: добавить один продукт
  ```json
     {
        "id": 4,
        "name": "product4",
        "prices": [
            {
                "id": 4,
                "price": 111.11,
                "pdate": "2020-11-30",
                "productId": 4
            }
        ]
    }
  ```
  GET `http://localhost:8081/findProductByName/name=`: найти по имени
  GET `http://localhost:8081/findProductById/id=`: найти продукт по индексу  
  DELETE `http://localhost:8081/deleteProductById/id=`: удалить по индексу

<details><summary>Скрипты структуры БД ...</summary>

```sql
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

```

</details>
