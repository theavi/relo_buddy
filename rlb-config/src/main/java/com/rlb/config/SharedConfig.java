package com.rlb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*Why a custom factory? Because Spring doesn't support YAML in @PropertySource natively.*/
@Configuration
@PropertySource(value = "classpath:application-common.yml", factory = YamlPropertySourceFactory.class)
public class SharedConfig {
}
