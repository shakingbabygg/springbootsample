package springboot.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource(value = "classpath:application.properties")
public class Config {

	@Autowired
	private Environment env;

	@Value("db.jdbc")
	private String jdbc;

	@Value("db.driverClass")
	private String driverClass;

	@Value("db.dialect")
	private String dialect;

	@Value("db.user")
	private String user;

	@Value("db.pswd")
	private String pswd;

	public String get(String key) {
		try {
			return env.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
