package com.server.RandomDungeonFighter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@Configuration
public class RandomDungeonMain {

    public static void main(String[] args) {
        SpringApplication.run(RandomDungeonMain.class, args);
    }

    @Bean
    public JpaTransactionManager transactionManager() {return new JpaTransactionManager();}

    @Bean
    public XmlMapper xmlMapper() {return new XmlMapper();}
}
