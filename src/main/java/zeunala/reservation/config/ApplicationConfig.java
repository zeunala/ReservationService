package zeunala.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "zeunala.reservation.dao", "zeunala.reservation.service", "zeunala.reservation.controller"})
public class ApplicationConfig {

}