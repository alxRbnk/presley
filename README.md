># Gateway
### Описание: Единая точка входа для всех API:
### **Gateway:** `port:8084`
*- Auth service:* 8083

*- News service:* 8081

*- Comments service:* 8082

># Auth service
## Auth API
### Регистрация пользователя
**URL:** `POST` `/auth/registration`  
**Описание:** Регистрация нового пользователя с логином и паролем.  
**пример тела запроса json:**
```json
{
  "username": "user123",
  "password": "password123"
}
```
**возвращает JWT, время действия 60мин**

### Авторизация пользователя
**URL:** `POST` `/auth/login`

**Описание: Авторизация пользователя и генерация JWT-токена.**

**пример тела запроса json:**
```json
{
"username": "user123",
"password": "password123"
}
```
**возвращает JWT, время действия 60мин**

## Admin API
### Изменение роли пользователя
**URL:** `POST` `/admin/change`

`доступ только с ролью ADMIN`

**Описание: Изменение роли пользователя**

**(например: ROLE_ADMIN, ROLE_JOURNALIST).**

**пример тела запроса json:**
```json
{
"username": "user123",
"role": "ROLE_ADMIN"
}
```
### Получение списка пользователей
**URL:** `GET` `/admin/users`

**Описание:**
**Получение списка всех зарегистрированных логинов пользователей.**

># News service
## News API
**Получение новости по ID**
**URL:** `GET` `/news/{id}`

**Описание: Получение информации о новости по её ID.**

**Создание новости**

**URL:** `POST` `/news/create`

**Описание: Создание новой новости.**

**пример тела запроса json:**
```json
{
"title": "Новая новость",
"content": "Содержимое новости"
}
```
**Обновление новости**
**URL:** `PUT` `/news/update`

**Описание: Обновление существующей новости.**

**пример тела запроса json:**
```json
{
"id": 1,
"title": "Обновленная новость",
"content": "Обновленное содержимое"
}
```
**Удаление новости**
**URL:** `DELETE` `/news/{id}`

**Описание: Удаление новости по её ID.**

># Comment service

## Comments API
**Получение комментария по ID**
**URL:** `GET` `/comments/{id}`

**Описание: Получение комментария по его ID.**

**Создание комментария**
**URL:** `POST` `/comments`

**Описание: Создание нового комментария.**

**пример тела запроса json:**
```json
{
  "newsId": 1,
  "content": "Текст комментария"
}
```
**Удаление комментария**
**URL:** `DELETE` `/comments/{id}`

**Описание: Удаление комментария по его ID.**

### **примеры запросов на единый `port 8084`:**

**Auth сервис 8083**

`POST` `http://localhost:8084/auth/registration`- регистрация, выдается JWT с ROLE_SUBSCRIBER 

`POST` `http://localhost:8084/auth/login`- вход, получение JWT

`POST` `http://localhost:8084/admin/change`- смена роли (ROLE_ADMIN)

`GET` `http://localhost:8084/admin/users`- получение списка пользователей (ROLE_ADMIN)

**News сервис 8081**

`GET` `http://localhost:8084/news/1` - получение новости c id 1

`GET` `http://localhost:8084/news` - получение всех новостей 

`POST` `http://localhost:8084/news/create` - создание новости (ROLE_JOURNALIST, ROLE_ADMIN)

`PUT` `http://localhost:8084/news/update` - обновление новости (ROLE_JOURNALIST, ROLE_ADMIN)

`DELETE` `http://localhost:8084/news/2` - удаление новости с id 2 (ROLE_JOURNALIST, ROLE_ADMIN)

`GET` `http://localhost:8084/news/paged?page=1&size=5` - получение всех новостей с пагинацией 

`GET` `http://localhost:8084/news/3/comments` - получить все комментарии у новости с id 3

`GET` `http://localhost:8084/news/20/comments/1` - получение комментария с индексом 1, у новости с id 20 

`GET` `http://localhost:8084/news/search?keyword=news 10` - поиск по новостям с ключемым словом - news 10

**Comment сервис  8082**

`GET` `http://localhost:8082/comments/1` - получение по id 1

`GET` `http://localhost:8082/comments` - получение всех комментариев 

`POST` `http://localhost:8082/comments` - создание комментария (ROLE_ADMIN или ROLE_SUBSCRIBER)

`PUT` `http://localhost:8082/comments` - обновление комментария (ROLE_ADMIN или ROLE_SUBSCRIBER)

`DELETE` `http://localhost:8082/comments/22` - удаление комментария (ROLE_ADMIN или ROLE_SUBSCRIBER)
