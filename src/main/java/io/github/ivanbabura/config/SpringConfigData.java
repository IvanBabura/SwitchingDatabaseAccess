package io.github.ivanbabura.config;

import io.github.ivanbabura.entities.Person;
import io.github.ivanbabura.repositories.PersonRepository;
import io.github.ivanbabura.services.PersonServiceImpl;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan("io.github.ivanbabura")
public class SpringConfigData {
    @Bean
    public DatabaseProperties databaseProperties(){
        DatabaseProperties databaseProperties = new DatabaseProperties();
        databaseProperties.loadFromResourceFile();
        return databaseProperties;
    }

    @Bean
    public DataSource dataSource(DatabaseProperties databaseProperties){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseProperties.getProperty("driver_class"));
        dataSource.setUrl(databaseProperties.getProperty("url"));
        dataSource.setUsername(databaseProperties.getProperty("username"));
        dataSource.setPassword(databaseProperties.getProperty("password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource(databaseProperties()));
    }

    @Bean
    public Connection connection(DatabaseProperties databaseProperties){
        try {
            return DriverManager.getConnection(
                    databaseProperties.getProperty("url"),
                    databaseProperties.getProperty("username"),
                    databaseProperties.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        return Persistence.createEntityManagerFactory("myPersistenceUnit");
    }

    @Bean
    public SessionFactory sessionFactory(DatabaseProperties databaseProperties){
        //В hibernate.cfg.xml нельзя напрямую передать данные в Driver, я придумал только так обойти это:
        DatabaseProperties hibernateDatabaseProperties = databaseProperties.getConvertedToHibernate();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate/hibernate.cfg.xml")
                .applySettings(hibernateDatabaseProperties)
                .build();
        Metadata metadata = new MetadataSources(standardServiceRegistry)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClassName("io.github.ivanbabura.entities.Person")
                .addResource("hibernate/Person.hbn.xml")
                .getMetadataBuilder()
                .build();
        return metadata.getSessionFactoryBuilder().build();
    }

    @Bean
    public PersonServiceImpl personServiceJdbc(@Qualifier(value = "personRepositoryJdbcImpl") PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }

    @Bean
    public PersonServiceImpl personServiceJpa(@Qualifier(value = "personRepositoryJpaImpl") PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }

    @Bean
    public PersonServiceImpl personServiceHibernate(@Qualifier(value = "personRepositoryHibernateImpl") PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }

    @Bean
    public PersonServiceImpl personServiceSpringJdbc(@Qualifier(value = "personRepositorySpringJdbcImpl") PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }

}
