package library;

import library.feign.NaverClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = NaverClient.class)
@SpringBootApplication
public class SearchApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchApiApplication.class, args);
	}
}
