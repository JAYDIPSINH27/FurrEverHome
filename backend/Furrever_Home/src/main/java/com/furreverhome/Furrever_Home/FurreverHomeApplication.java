package com.furreverhome.Furrever_Home;

import com.furreverhome.Furrever_Home.config.FrontendConfigurationProperties;
import io.getstream.chat.java.services.framework.DefaultClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({FrontendConfigurationProperties.class})
public class FurreverHomeApplication {

	@Value( "${io.getstream.chat.apiKey}" )
	private String apiKey;
	@Value( "${io.getstream.chat.apiSecret}" )
	private String secretKey;

	public static void main(String[] args) {
		SpringApplication.run(FurreverHomeApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			Properties properties = new Properties();
			properties.put(DefaultClient.API_KEY_PROP_NAME, apiKey);
			properties.put(DefaultClient.API_SECRET_PROP_NAME, secretKey);
			var client = new DefaultClient(properties);
			DefaultClient.setInstance(client);
		};
	}

}
