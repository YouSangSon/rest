package yousang.backend.rest.configuration;

import java.io.File;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConfigLoader implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            String configPath = "config.json"; // Adjust the path
            ObjectMapper mapper = new ObjectMapper();
            ConfigProperties config = mapper.readValue(new File(configPath), ConfigProperties.class);

            // Setting Redis properties
            System.setProperty("redis.host", config.getRedis().getHost());
            System.setProperty("redis.port", String.valueOf(config.getRedis().getPort()));

            // Setting Database properties
            System.setProperty("spring.datasource.url", config.getDatabase().getUrl());
            System.setProperty("spring.datasource.username", config.getDatabase().getUsername());
            System.setProperty("spring.datasource.password", config.getDatabase().getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}