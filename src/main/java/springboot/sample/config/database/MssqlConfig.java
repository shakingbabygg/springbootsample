package springboot.sample.config.database;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import springboot.sample.config.Config;

@Configuration
@EnableJpaRepositories(basePackages = "springboot.sample.repositories.mssql", transactionManagerRef = "jpaTransactionManager")
@EnableTransactionManagement
public class MssqlConfig {

	private static final String[] ENTITYMANAGER_PACKAGES_TO_SCAN = { "springboot.sample.models.entities.mssql" };

	@Autowired
	private Config config;

	@Bean
	public DataSource dataSource() {
		String username = config.get("db.user");
		String password = config.get("db.pswd");
		String driverClass = config.get("db.driverClass");
		String url = config.get("db.jdbc");

		Properties properties = new Properties();
		properties.setProperty("driverClassName", driverClass);
		properties.setProperty("jdbcUrl", url);
		properties.setProperty("username", username);
		properties.setProperty("password", password);

		HikariConfig config = new HikariConfig(properties);

		return new HikariDataSource(config);
	}

	@Bean(name = "jpaTransactionManager")
	public JpaTransactionManager jpaTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	private HibernateJpaVendorAdapter vendorAdaptor() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		return vendorAdapter;
	}

	@Bean(name = "entityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		entityManagerFactoryBean.setJpaProperties(addProperties());

		return entityManagerFactoryBean;
	}

	private Properties addProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "none");
		properties.setProperty("hibernate.dialect", config.get("db.dialect"));
		properties.setProperty("hibernate.show_sql", "false");
		properties.setProperty("hibernate.format_sql", "false");
		return properties;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.unwrap(SessionFactory.class);
	}
}
