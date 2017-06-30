package luxoft.web.application.config;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@ComponentScan({"luxoft.web.application"})
public class HibernateConfig {
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(
                new String[]{"luxoft.web.application.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public javax.sql.DataSource restDataSource() {
        String settings = "?useUnicode=true&" +
                "characterEncoding=utf-8&" +
                "useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&" +
                "serverTimezone=UTC&" +
                "useSSL=false";

        // BasicDataSource dataSource = new BasicDataSource();
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/FilesStatistic" + settings);
        ds.setUsername("root");
        ds.setPassword("slavaraid123");
        return ds;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "validate");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                setProperty("hibernate.show_sql", "true");
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }
}
