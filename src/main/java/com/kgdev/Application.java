package com.kgdev;

import com.kgdev.service.FollowerInMemoryCacheService;
import com.kgdev.service.LeaderInMemoryCacheService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        new Thread(()->{
//            try {
//                LeaderInMemoryCacheService leader = new LeaderInMemoryCacheService(9090, "logs/commitLog.txt");
//                System.out.println("Server is listening on port 9090");
//                leader.acceptFollowerConnections();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }).start();

    }

}
