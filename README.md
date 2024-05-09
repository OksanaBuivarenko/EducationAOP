# EducationAOP
EducationAOP - это учебный проект на Java, созданный для демонстрации парадигмы аспектно-ориентированное программирования. В основе лежит REST-сервис получения заказа на тур.

![addOrder](https://github.com/OksanaBuivarenko/EducationAOP/assets/144807983/457a8d3c-3610-4e57-83bd-91334beb4b77)

Методы сервиса помечены аннотациями `@TrackTime` - для синхронно работающих и `@TrackAsyncTime` - для асинхронно работающих методов. `TrackTimeAspect` при помощи АОП отслеживает время выполнения этих методов, логирует старт и окончание метода и вызывает сервис сохраняющий данные о времени выполнения методов в базе данных. Реализован REST API для получения статистики по времени выполнения методов.

![getStatistic](https://github.com/OksanaBuivarenko/EducationAOP/assets/144807983/50457b10-4717-4b7e-97e1-36a68b98a4d5)

`ExceptionHandlerAspect` логирует ошибки.

Использование АОП в можно отключить, поменяв значение `@ConditionalOnProperty(name = "myapp.feature.enabled", havingValue = "true")` в конфигурационном файле `application.yml` на `false`.

## Начало работы
1. Установите на свой компьютер [JDK](https://www.oracle.com/cis/java/technologies/downloads/) и среду разработки [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/download/?section=windows), если они ещё не установлены.
2. Загрузите проект-заготовку из Git-репозитория.
3. Запустите базу данных Postgres в [Docker](https://www.docker.com/products/docker-desktop/), выполнив в терминале команду `docker compose up`.
4. Запустите приложение. Для просмотра статистики можно выполнять запросы через браузер. Для тестирования полной функциональности можно использовать [Postman](https://www.postman.com/downloads/).
