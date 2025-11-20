package nl.roxit.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class DemoApplicationTest {

    @Test
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }

    @Test
    void applicationHasSpringBootApplicationAnnotation() {
        assertThat(DemoApplication.class.isAnnotationPresent(
            org.springframework.boot.autoconfigure.SpringBootApplication.class))
            .isTrue();
    }

    @Test
    void mainMethodRunsWithoutException() {
        assertDoesNotThrow(() -> {
            DemoApplication.main(new String[]{
                "--spring.main.web-application-type=none",
                "--spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
            });
        });
    }

    @Test
    void applicationContextContainsDemoApplication(ApplicationContext context) {
        assertThat(context.getBean(DemoApplication.class)).isNotNull();
    }

    @Test
    void springApplicationCanBeInstantiated() {
        DemoApplication application = new DemoApplication();
        assertThat(application).isNotNull();
    }
}
