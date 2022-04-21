# e-Shop

## Description

e-Shop has front-side and back-side which allow interaction with database Products.<br />
The docker container MySQL (https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/docker-mysql-getting-started.html#docker-download-image) is used for storage. <br />
It is possible to store data both locally in the MySql database and in the Docker container.<br />
Service has actuator definition endpoint.<br />

##Task

Магазин має каталог товарів. Для каталогу реалізувати можливість вибірки товарів:
- за типом;
- за ціною;
- за популярністю.
  Користувач реєструється в системі, обирає товари і робить замовлення. Після замовлення товар має статус 'В обробці'.
  Незареєстрований користувач не має можливості замовляти товари.
  Користувач має особистий кабінет, в якому міститься коротка інформація про нього, а також список замовлених товарів і їх поточний статус (в обробці, скомплектований, скасований).
  Менеджер може встановлювати спеціальні пропозиції для товарів, такі пропозиції завжди відображаються нагорі переліку. Адміністратор системи володіє такими ж правами, як і менеджер, а додатково може:
- додати/видалити товар, змінити інформацію про нього;
- заблокувати/розблокувати користувача.

## Requirements and Tools

1. Застосунок повинен буду структурованим за архітектурою MVC та Spring Boot. 
2. В якості СУБД використовувати MySQL або PostgreSQL. Lombok. 
3. Spring Authorization. 
4. Для доступу до даних використовувати JPA (Spring Data та/або Hibernate). 
5. Застосунок має бути покритим модульними тестами (мінімальний відсоток покриття 40%). 
6. Впровадити у проект журнал подій із використанням бібліотеки log4j. 
7. Реалізувати механізм пагінації сторінок з даними.

## Add requirements

Всі поля введення повинні бути із валідацією даних. HTML, CSS, JS. ThymeLeaf. Swager

## Time

## Available endpoints

- `GET http://localhost:8080/e-shop` - show start page
- `GET  http://localhost:8080/e-shop/actuator/health` - for health checking
- `GET  http://localhost:8080/e-shop/actuator/info` - for info getting

