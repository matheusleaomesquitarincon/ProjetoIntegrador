package com.projetoIntegrador.QuizByte;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class QuizByteApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Context loaded successfully!");
    }
}