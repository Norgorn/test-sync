package ru.norgorn.moneySync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import ru.norgorn.moneySync.config.BeansConfig;

@SpringBootApplication
@ComponentScan(basePackageClasses={BeansConfig.class})
public class MoneySyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneySyncApplication.class, args);
	}
}
