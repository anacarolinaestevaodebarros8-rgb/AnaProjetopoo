package com.folhear.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuração explícita do JPA/Hibernate.
 * Complementa as propriedades do application.properties.
 */
@Configuration
@EnableTransactionManagement
public class JpaConfig {

    @Value("${spring.jpa.hibernate.ddl-auto:validate}")
    private String ddlAuto;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.folhear.entity");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(false);
        em.setJpaVendorAdapter(adapter);

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect",              "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto",         ddlAuto);
        props.put("hibernate.show_sql",              true);
        props.put("hibernate.format_sql",            true);
        props.put("hibernate.default_schema",        "public");
        props.put("hibernate.jdbc.time_zone",        "UTC");
        // Previne N+1 queries em coleções
        props.put("hibernate.default_batch_fetch_size", "16");
        // Naming strategy (snake_case automático)
        props.put("hibernate.physical_naming_strategy",
                  "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        em.setJpaPropertyMap(props);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory.getObject());
        return tm;
    }
}