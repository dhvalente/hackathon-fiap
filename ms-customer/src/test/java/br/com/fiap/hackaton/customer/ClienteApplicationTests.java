package br.com.fiap.hackaton.customer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest
class ClienteApplicationTests {

	private final TestRestTemplate restTemplate = new TestRestTemplate();
	@Test
	void contextLoads() {
	}
}
