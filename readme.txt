Проект (Dynamic Web Project) для Eclipse IDE for Enterprise Java Developers.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

1. Запустить сервер MySQL.

2. Зайти в административную консоль и выполнить команду

	source ABSOLUTE_PATH_TO_SCRIPT; (/sql/dbcreate-mysql.sql)

3. Создать пользователя с именем testuser, паролем testpass и дать ему все права на базу данных st4db:

	grant all privileges on st4db.* to testuser@'%' identified by 'testpass';	

4. Открыть проект в Eclipse.

5. Сконфигурировать в Eclipse Apache Tomcat.