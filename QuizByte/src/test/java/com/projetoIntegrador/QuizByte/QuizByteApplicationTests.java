package com.projetoIntegrador.QuizByte;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;

@SpringBootTest
@Import(SecurityConfig.class) // sua configuração de senha/encoder sem filtros
class QuizByteApplicationTests {

	@Test
	void contextLoads() {
	}

}
