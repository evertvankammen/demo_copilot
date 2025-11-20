package nl.roxit.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	@Bean
	public CommandLineRunner demo(RequestedValueRepository repository) {
		return (args) -> {
			repository.save(new RequestedValueEntity("Initial Value 1", LocalDateTime.now(), LocalDateTime.now()));
			repository.save(new RequestedValueEntity("Initial Value 2", LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(1)));
			repository.save(new RequestedValueEntity("Initial Value 3", LocalDateTime.now().minusDays(2), LocalDateTime.now().minusHours(2)));
		};
	}

}
