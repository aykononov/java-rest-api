## Spring Boot REST API на Java.
### Java SE + Spring Boot + Hibernate + Oracle Database 

### Стек:

<small>

* **Java SE 11** - Платформа Java, стандартная версия 11 [Java SE 11 Archive Downloads](https://www.oracle.com/cis/java/technologies/javase/jdk11-archive-downloads.html "https://www.oracle.com/cis/java/technologies/javase/jdk11-archive-downloads.html");
* **Spring Boot** - инструмент фреймворка Spring для написания приложений с минимальной конфигурацией (имеет встроенный контейнер сервлетов Tomcat по умолчанию);
* **Spring Web** - включает в себя все настройки Spring MVC и позволяет писать REST API без дополнительных настроек;
* **Spring Data JPA** - позволяет работать с SQL с помощью Java Persistence API, используя Spring Data и Hibernate;
* **Lombok** - библиотека для сокращения написания стандартного кода на java (геттеры, сеттеры и т.д.);
* **OpenCSV** - парсер CSV-файлов;
* **Oracle Data Base** - используемая БД [Oracle Database Express Edition (XE) download](https://www.oracle.com/database/technologies/xe-downloads.html "https://www.oracle.com/database/technologies/xe-downloads.html");
* **Maven** - фреймворк для автоматизации сборки проектов на основе описания их структуры в файлах на языке POM (англ. Project Object Model).

</small>

### Функционал.

<details><summary>1. Это приложение предоставляет REST API для работы с Базой данных (БД).</summary>
<blockquote>

Оно умеет создавать (CREATE), читать (READ), изменять (UPDATE) и удалять (DELETE) информацию в БД.

Эндпоинты:

   ```
   GET http://localhost:8081/listProducts получить все продукты  
   GET http://localhost:8081/getProductById/id= найти продукт по идентификатору  
   GET http://localhost:8081/getProductByName/name= найти продукт по имени  
   POST http://localhost:8081/saveProducts добавляет несколько продуктов  
   POST http://localhost:8081/addProduct добавляет один продукт  
   DELETE http://localhost:8081/deleteProductById/id= удалить продукт по идентификатору  
   DELETE http://localhost:8081/removeAll - удалить все продукты  
   ```
_Формат данных ответа в json._

</blockquote>
</details>

<details><summary>2. Приложение умеет автоматически загружать данные из CSV-файла.</summary>
<blockquote>

Путь директории с файлами настраивается в конфигурационном файле приложения:

        upload.dir = D:/upload/

Загрузка файла стартует при появлении нового файла в указанной директории:

        upload.file = LoadIntoDB.csv

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

</blockquote>
</details>

<details><summary>3. Приложение умеет вести логирование.</summary>
<blockquote>

В логфайле `LoadIntoDB.log` отмечается факт старта обработки файла и результат с количеством обработанных записей (товаров и цен).

</blockquote>
</details>

### Хранение данных.

<details><summary>Структура таблиц Базы данных</summary>
<blockquote>

```
1. Таблица товар. Хранит название товара.
   Колонки: id, name.
2. Таблица цена товара. Хранит цену на товар и дату с которой цена актуальная. 
   По каждому товару может быть несколько цен с разными датами.
   Колонки: id, price, date, product_id.
```

Таблицы в БД создаются автоматически при старте приложения.

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
  FROM products pd
  JOIN prices   pr
    ON pd.id = pr.product_id
 ORDER BY pr.id;
/

```
</details>

_Также прилагаю файл со скриптами для создания необходимых сущностей._ [ScriptDB.sql](https://github.com/aykononov/springboot-hibernate-oracle-opencsv/blob/main/ScriptDB.sql)

</blockquote>
</details>

### Сборка.

<details><summary>Сборка исполняемого jar-файла.</summary>
<blockquote>

Используйте **shell**, перейдите в корневой каталог проекта (*где находится файл pom.xml*) и введите команды:

    mvn clean package  
    cd target  

</blockquote>
</details>

### Запуск.

<details><summary>Запуск и проверка работы приложения.</summary>
<blockquote>

В командной строке выполните команду:  
    
    java -jar demo-0.0.1-SNAPSHOT.jar

Создайте файл для загрузки или возьмите готовый [LoadIntoDB.csv](https://github.com/aykononov/springboot-hibernate-oracle-opencsv/blob/main/LoadIntoDB.csv "https://github.com/aykononov/springboot-hibernate-oracle-opencsv/blob/main/LoadIntoDB.csv")

Cкопируйте файл `LoadIntoDB.csv` в директорию `D:/upload/`

Данные должны автоматически загрузится в БД и вывести информацию о загрузке в логфайл `LoadIntoDB.log`.

<small>

*Файл `LoadIntoDB.csv` можно удалять и копировать заново любое количество раз, приложение будет автоматически загружать в БД и писать в лог.*

</small>

Запустите **POSTMAN** и используйте следующие URL-адреса для вызова методов контроллера и просмотра взаимодействия с базой данных:

* **GET** `http://localhost:8081/listProducts` - получить все продукты
* **GET** `http://localhost:8081/getProductById/id=4` - найти продукт по идентификатору  
* **GET** `http://localhost:8081/getProductByName/name=product4` - найти продукт по имени 
* **DELETE** `http://localhost:8081/deleteProductById/id=4` - удалить продукт по идентификатору  
* **POST** `http://localhost:8081/addProduct` - добавляет один продукт

<details><summary>тело запроса (JSON контент) ...</summary>

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

<details><summary>тело запроса (JSON контент) ...</summary>

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

</blockquote>
</details>
