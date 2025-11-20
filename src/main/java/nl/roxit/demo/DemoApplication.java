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
			repository.save(new RequestedValueEntity("item1", "Initial Value 1", 1, true, LocalDateTime.now(), LocalDateTime.now()));
			repository.save(new RequestedValueEntity("item2", "Initial Value 2", 1, true, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(1)));
			repository.save(new RequestedValueEntity("item3", "Initial Value 3", 1, true, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusHours(2)));
		};
	}

}
