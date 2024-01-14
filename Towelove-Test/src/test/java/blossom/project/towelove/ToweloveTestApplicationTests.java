package blossom.project.towelove;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToweloveTestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void vitualThreadTest(){
        Thread thread = Thread.startVirtualThread(() -> {
            System.out.println("this is virtual thread...");
        });
        thread.start();
    }
}
