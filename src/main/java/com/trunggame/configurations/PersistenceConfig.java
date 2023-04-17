package com.trunggame.configurations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public EntityManager getEntityManager() {
        return entityManager;
    }

}

