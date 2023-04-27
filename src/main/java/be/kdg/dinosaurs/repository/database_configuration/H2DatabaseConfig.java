package be.kdg.dinosaurs.repository.database_configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("jdbcbrepo")
public class H2DatabaseConfig {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Bean
    public DataSource dataSource(){
        LOGGER.info("H2DatabaseConfig creating H2 database");
        DataSource dataSource = DataSourceBuilder
                .create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:dinosaurdb")
                .username("sa")
                .password("")
                .build();
        return dataSource;
    }
}