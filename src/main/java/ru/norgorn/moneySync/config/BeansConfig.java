package ru.norgorn.moneySync.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.norgorn.moneySync.service.MoneySyncServiceImpl;
import ru.norgorn.moneySync.web.controllers.MoneyController;

@Configuration
@ComponentScan(basePackageClasses={MoneyController.class, MoneySyncServiceImpl.class})
public class BeansConfig {
	
}
