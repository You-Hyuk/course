package com.example.course

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    properties = [
        "spring.profiles.active=test",
        "spring.config.name=application-test"
    ]
)
class CourseApplicationTests {

    @Test
    fun contextLoads() {
    }

}
