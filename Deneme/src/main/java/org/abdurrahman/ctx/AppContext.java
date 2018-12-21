package org.abdurrahman.ctx;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.SybaseAnywhereDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "org.abdurrahman.db")
@EnableTransactionManagement
@EnableJpaRepositories
public class AppContext {
	@Bean
	public DataSource dataSource() {
		return new SingleConnectionDataSource("jdbc:sybase:Tds:localhost:2638", "DBA", "sql", true);
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setPackagesToScan("org.abdurrahman.model");
		Properties properties = new Properties();
		properties.put("hibernate.dialect", SybaseAnywhereDialect.class.getName());
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.format_sql", false);
		properties.put("hibernate.jdbc.use_get_generated_keys", true);
		properties.put("hibernate.jdbc.use_scrollable_resultset", false);
		properties.put("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		properties.put("hibernate.connection.JCONNECT_VERSION", 7);
		properties.put("hibernate.connection.DYNAMIC_PREPARE", true);
//		properties.put("hibernate.hbm2ddl.auto", "create"); // (this line is exist to create new table into database.)
		factoryBean.setHibernateProperties(properties);
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
}
