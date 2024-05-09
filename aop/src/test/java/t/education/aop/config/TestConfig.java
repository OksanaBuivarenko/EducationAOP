package t.education.aop.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
//@PropertySource("classpath:application-test.yaml")
public class TestConfig {

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.4")
                .withUsername("testUser")
                .withPassword("testSecret")
                .withDatabaseName("testDatabase");
        return postgreSQLContainer;
    }
}


