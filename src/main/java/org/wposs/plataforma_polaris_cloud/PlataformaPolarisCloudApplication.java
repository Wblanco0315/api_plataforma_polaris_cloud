package org.wposs.plataforma_polaris_cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlataformaPolarisCloudApplication {

    private static final Logger log = LoggerFactory.getLogger(PlataformaPolarisCloudApplication.class);


    public static void main(String[] args) {
        log.info("Iniciando aplicación Plataforma Polaris Cloud...");
        log.info("Version:");
        SpringApplication.run(PlataformaPolarisCloudApplication.class, args);
    }

}
