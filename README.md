## springboot-hibernate-oracle-opencsv

Web приложение на базе фреймворка Spring Boot и Hibernate с использованием базы данных Oracle. 

### Технологии:

<small>

 * **Spring Boot** - инструмент фреймворка Spring для написания приложений с минимальной конфигурацией (имеет встроенный контейнер сервлетов Tomcat по умолчанию);
 * **Spring Web** - зависимость включает в себя все настройки Spring MVC и позволяет писать REST API без дополнительных настроек;
 * **Spring Data JPA** - позволяет работать с SQL с помощью Java Persistence API, используя Spring Data и Hibernate;
 * **Lombok** - библиотека для сокращения написания стандартного кода на java (геттеры, сеттеры и т.д.);
 * **OpenCSV** - парсер CSV-файлов;
 * **Oracle Data Base** - используемая БД [Oracle Database Express Edition (XE) download](https://www.oracle.com/database/technologies/xe-downloads.html "https://www.oracle.com/database/technologies/xe-downloads.html");
 * **Maven** - фреймворк для автоматизации сборки проектов на основе описания их структуры в файлах на языке POM (англ. Project Object Model).

</small>

### Структура БД.

```
1. Таблица товар. Хранит название товара.
   Колонки: id, name.
2. Таблица цена товара. Хранит цену на товар и дату с которой цена актуальная. 
   По каждому товару может быть несколько цен с разными датами.
   Колонки: id, price, date, product_id.
```
Таблицы в БД создаются автоматически при старте приложения.  
(также приложен файл со скриптами [ScriptDB.sql](https://github.com/aykononov/springboot-hibernate-oracle-opencsv/blob/main/ScriptDB.sql) 
для создания необходимых сущностей)


<details><summary>Скрипт для создания структуры БД ...</summary>

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

### Функционал.

#### 1. Приложение умеет автоматически загружать данные из *CSV-файла*. 
    
   Путь директории с файлами настраивается в конфигурационном файле приложения:
    
        upload.dir = D:/upload/
    
   Загрузка файла стартует при появлении нового файла в указанной директории:
    
        upload.file = LoadIntoDB.csv
   
<details><summary>Пример формата данных CSV-файла ...</summary>

```csv
product_id, product_name, price_id, price, price_date
1,product1,1,100.11,2020-11-30
2,product2,2,22.02,2020-11-30
3,product3,3,3.03,2020-11-30
4,product4,4,100.01,2020-11-30
1,product1,5,111.01,2020-12-01
2,product2,6,22.22,2020-12-01
3,product3,7,3.33,2020-12-01
4,product4,8,100.10,2020-12-01
```
</details>

В логфайле `LoadIntoDB.log` отмечается факт старта обработки файла и результат с количеством обработанных записей (товаров и цен).
   

#### 2. Приложение предоставляет следующие REST методы. 
   ```
   GET http://localhost:8081/listProducts получить все продукты  
   GET http://localhost:8081/getProductById/id= найти продукт по идентификатору  
   GET http://localhost:8081/getProductByName/name= найти продукт по имени  
   POST http://localhost:8081/saveProducts добавляет несколько продуктов  
   POST http://localhost:8081/addProduct добавляет один продукт  
   DELETE http://localhost:8081/deleteProductById/id= удалить продукт по идентификатору  
   DELETE http://localhost:8081/removeAll - удалить все продукты  
   ```
   Формат данных ответа - json.

### Проверка.

Создайте файл для загрузки или возьмите готовый [LoadIntoDB.csv](https://github.com/aykononov/springboot-hibernate-oracle-opencsv/blob/main/LoadIntoDB.csv "https://github.com/aykononov/springboot-hibernate-oracle-opencsv/blob/main/LoadIntoDB.csv")   

Запустите приложение и *скопируйте* файл `LoadIntoDB.csv` в директорию `D:/upload/`

Данные должны автоматически загрузится в БД и вывести информацию о загрузке в логфайл `LoadIntoDB.log`.

<small>

*Файл можно удалять и копировать заново любое количество раз, приложение будет автоматически загружать в БД и писать в лог.*

</small>

Запустите **POSTMAN** и используйте следующие URL-адреса для вызова методов контроллера и просмотра взаимодействия с базой данных:

* **GET** `http://localhost:8081/listProducts` - получить все продукты
* **GET** `http://localhost:8081/getProductById/id=4` - найти продукт по идентификатору  
* **GET** `http://localhost:8081/getProductByName/name=product4` - найти продукт по имени 
* **DELETE** `http://localhost:8081/deleteProductById/id=4` - удалить продукт по идентификатору  
* **POST** `http://localhost:8081/addProduct` - добавляет один продукт

<details><summary>в теле запроса JSON контент ...</summary>

 ```json
{
    "id": 4,
    "name": "product4",
    "prices": [
        {
            "id": 4,
            "price": 111.11,
            "pdate": "2020-12-17",
            "productId": 4
        }
    ]
}
```

</details>

* **DELETE** `http://localhost:8081/removeAll` - удалить все продукты
* **POST** `http://localhost:8081/saveProducts` - добавляет несколько продуктов

<details><summary>в теле запроса JSON контент ...</summary>

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

</details>

* **GET** `http://localhost:8081/listProducts` - получить все продукты  