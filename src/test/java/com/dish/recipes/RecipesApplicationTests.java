package com.dish.recipes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("local")
class RecipesApplicationTests {

    @Test
    void contextLoads() {
    }

}
