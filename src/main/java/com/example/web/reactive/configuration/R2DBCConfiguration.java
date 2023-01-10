package com.example.web.reactive.configuration;

import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class R2DBCConfiguration {

  @Bean
  public ConnectionFactory connectionFactory() {
    return ConnectionFactories.get(ConnectionFactoryOptions.builder()
        .option(DRIVER, "postgresql")
        .option(HOST, "localhost")
        .option(PORT, 5432)  // optional, defaults to 5432
        .option(USER, "postgres_user")
        .option(PASSWORD, "postgres_pwd")
        .option(DATABASE, "ods_products")  // optional
        .build());
  }

  @Bean
  public R2dbcEntityTemplate r2dbcEntityTemplate() {
    return new R2dbcEntityTemplate(connectionFactory());
  }

  @Bean
  public DatabaseClient databaseClient() {
    return DatabaseClient.create(connectionFactory());
  }
}
