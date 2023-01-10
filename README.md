# Playing with Spring WebFlux and R2DBC

## How to run

* Spin up PostgreSQL 
```shell
$ docker run --name my-postgres \
-e POSTGRES_DB=ods_products \
-e POSTGRES_USER=postgres_user \
-e POSTGRES_PASSWORD=postgres_pwd \
-p 5432:5432 \
-d postgres
```
* `./gradlew bootRun`

See also [spring-web-reactive-web-client](https://github.com/KostyantynPanchenko/spring-web-reactive-web-client)
