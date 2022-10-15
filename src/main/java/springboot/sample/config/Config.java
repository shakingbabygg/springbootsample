package springboot.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
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

	public String get(String key) {
		try {
			return env.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
