package com.example.demo;

import com.example.demo.config.Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.demo.persistence")
public class DemoApplication {

    public static void main(String[] args) {
        //Config.init();
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * 使用 websockt注解的时候，使用@EnableScheduling注解
     * 启动的时候一直报错，增加这个bean 则报错解决。
     * 报错信息：  Unexpected use of scheduler.
     * https://stackoverflow.com/questions/49343692/websocketconfigurer-and-scheduled-are-not-work-well-in-an-application
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
