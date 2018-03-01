package pl.sda.finalProject.myOrganizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class BeanConfig {

    @Bean
    public DateTimeFormatter dateFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Bean
    public DateTimeFormatter timeFormatter(){
        return DateTimeFormatter.ofPattern("HH:mm");
    }
}
