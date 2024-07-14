package com.furreverhome.Furrever_Home.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "frontend")
@Data
public class FrontendConfigurationProperties {
  String host;
  String loginUrl;
  String updatePasswordUrl;
}
