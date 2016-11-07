package ru.norgorn.moneySync.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ru.norgorn.moneySync.persistence.NOOPRepositoryMarker;
import ru.norgorn.moneySync.persistence.entity.NOOPEntityMarker;

@Configuration
@EnableJpaRepositories(basePackageClasses = {NOOPRepositoryMarker.class})
@EntityScan(basePackageClasses={NOOPEntityMarker.class})
@EnableTransactionManagement
public class DataConfig {
	
}
