package com.demo.ntfyappapi.conf;

import jakarta.activation.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
public class MultiSchemaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.demo.ntfyappapi.dao");
        em.setJpaVendorAdapter(jpaVendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.default_schema", "ntfy_app");
        em.setJpaProperties(properties);
        return em;
    }
}
