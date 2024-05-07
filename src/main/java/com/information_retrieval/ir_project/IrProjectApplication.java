package com.information_retrieval.ir_project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;

@SpringBootApplication
public class IrProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                ResourceLoader resourceLoader = new DefaultResourceLoader();
                 Resource resource = resourceLoader.getResource("archive");
                 Directory.setArchivePath(resource.getURI().getPath());
            }
        };
    }
}
