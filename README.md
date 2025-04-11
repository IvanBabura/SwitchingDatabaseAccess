# Сервис переключения различных реализаций доступа к базе данных.

____

## 1 Краткое описание
Сервис, который использует разные реализации доступа к базе данных:
- Native JDBC;
- JPA (Hibernate);
- Native Hibernate;
- Spring JDBC.

При переключении радиобаттона на сайте или отправки соответствующего запроса к сервису происходит смена способа обращений к базе данных.


## 2 Стэк используемых технологий
- Java 17;
- Tomcat 10;
- Maven;
- Spring 6: Core, WebMVC, JDBC;
- Hibernate 6;
- Thymeleaf Spring6;
- Jackson (POJO to JSON);
- JUnit 5;
- Mockito 5;
- PostgreSQL 8.13;
- HTML;
- JavaScript.

## 3 Особенности проекта
### 3.1 Эндпоинты
Вроде как это не чистый REST, но исправлять не стал, цель не RESTfull была.

- POST /sdba/type — Изменить способ доступа к БД, отправляется в text_body{ один из (JDBC, JPA, Hibernate, SpringJDBC) } ;
- GET /sdba?id={id} — Найти по {id}, отправляется JSON {"id"=id};
- POST /sdba — Сохранить по {имени}, отправляется text_body{name};
- PUT /sdba — Обновить по {id и имени}, отправляется JSON {"id":id,"name":name};
- DELETE /sdba?id={id} — Удалить по {id}, отправляется JSON {"id":id};
- GET /sdba/all — Получить список всех;
- GET /sdba/like?startOfName= — Получить список всех, начало которых совпадает с {начало имени(любой длины)}, отправляется JSON {"startOfName":startOfName}.
- GET /sdba/likeNameOnly?startOfName= — Получить список только имён всех, начало которых совпадает с {начало имени(любой длины)}, отправляется JSON {"startOfName":startOfName}.

### 3.2 Загрузка конфигов.
Для каждого способа доступа к БД необходимы свои способы внедрения конфигурации БД. Всё приведено к единому месту хранения: "pom.xml". Во все "дочерние" конфиги подтянется отсюда при сборке проекта. Другого способа я не нашёл, придумал только так.

## 4 Что можно улучшить
- [ ] Добавить/изменить методы для чистого RESTfull;
- [ ] Юнит тесты контроллеру;
- [ ] Логирование;
- [ ] Перехватчик и обработчик ошибок (ControllerAdvice).

## 5 "Не баг, а фича"
1) Методы "Update" в сервисах "JDBС" и "SpringJDBC" возвращают TRUE, даже если при нахождении сущности не было изменений, тк я не делал расширенный SQL-запрос с проверкой на существования элемента и его соответствие с изменяемыми данными. Boolean там не особо-то и нужен в рамках этого проекта, но почему бы и нет.
2) Методы "getAllStartsWith" в сервисах "Hibernate" и "JPA" фильтруют данные не внутри SQL-запроса, а после, т.к. я не нашёл быстрого решения на "Hibernate Query Language".

## 6 SQL код для создания БД в POSTGRES
CREATE DATABASE switchjdbc;

\c switchjdbc;

CREATE TABLE IF NOT EXISTS people(id serial PRIMARY KEY, name VARCHAR(30) NOT NULL);

INSERT INTO people (name) VALUES ('John'), ('Sonya'), ('Stepfen'), ('Ivan'), ('Robert'), ('Robecka'), ('Roman'), ('Dariya'), ('Rotan'), ('Eva'), ('Robot'), ('Inga'), ('Roberto'), ('Toma');
