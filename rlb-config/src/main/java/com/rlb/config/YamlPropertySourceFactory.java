package com.rlb.config;

import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap = yaml.load(resource.getInputStream());
        Properties properties = new Properties();
        yamlMap.forEach((k, v) -> properties.put(k, v.toString()));
        return new PropertiesPropertySource(name != null ? name : resource.getResource().getFilename(), properties);
    }
}
