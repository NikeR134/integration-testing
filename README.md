# Интеграционное тестирование

Приложение собирает два Docker-образа с разными профилями Spring Boot. Интеграционные тесты запускают оба образа через Testcontainers и проверяют ответы `GET /profile`.

Для сборки нужны JDK 21, Maven и запущенный Docker Engine.

```bash
mvn clean package -DskipTests
docker build --build-arg APP_PORT=8080 --build-arg NETOLOGY_PROFILE_DEV=true -t devapp .
docker build --build-arg APP_PORT=8081 --build-arg NETOLOGY_PROFILE_DEV=false -t prodapp .
mvn test
```

