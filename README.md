### Задание 
1) Создать приложение с тремя эндпоинтами - эндпоинты могут быть любыми, например могут просто писать что-то в консоль
2) Ограничить каждый запрос ролями : ADMIN, USER и MODERATOR соответственно
3) Интегрировать keycloak и Spring Security в приложение.
5) Добавить регистрацию пользователей с возможностью указания роли
6) Создать в Keycloak Realm с ролями для нашего приложения
7) Создать 3 пользователей с 3 разными ролями и проверить, доступность к эндпоинтам

Комментарий по конфигурации Keycloak:
1) Заходим по адресу http://localhost:8080/admin, вводим логин и пароль admin/admin (в docker-compose)
2) Далее создаем новый Realm, например spring-keycloak-demo
3) В этом Realm создаем Client, указываем client_id (например spring-app), Root URL - http://localhost:8081, Valid Redirect URIs - http://localhost:8081/*, Web Origins - http://localhost:8081. Далее в Authentication Flow ставим галки на Standard Flow и Direct Access Grants. Далее во вкладке Client Scopes у клиента spring-app выбираем spring-app-dedicated и создаем Mapper (новая конфигурация), выбираем тип User Realm Role, прописываем название (любое), в token claim name пишем realm_access.roles, Включаем Add to Id token и Add to Access Token, Сохраняем.
4) Дальше создаем юзеров и назначаем им роли и пароли, в паролях убираем Temporary
5) Идем в Realm Settings -> General -> Frontend URL -> http://keycloak:8080.
   Затем в User Profile идем в каждую из вкладок email/firstName/lastName и убираем Required Field - упростит регистрацию и разрешит получение токена без указания доп инфы о пользователе. Сохраняем.
6) Топаем в постман, запрашиваем POST http://localhost:8080/realms/spring-keycloak-demo/protocol/openid-connect/token с Body x-www-form-urlencoded и указываем key/value:
   grant_type/password, client_id/spring-app, username/(username пользователя), password/(его пароль)
   Получаем токен.
7) Топаем в соответствующий url, admin/moderator/user с этим токеном

8) Также можно зарегистрировать нового пользователя POST http://localhost:8081/api/auth/register:
   {
   "username" : "kuraterut",
   "password" : "qwerty",
   "role" : "ADMIN"
   }
   Вроде все.

