package com.myntra.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties;

    static {
        try {
            String configPath = "src/test/resources/config/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fis);
            fis.close();
            logger.info("Configuration loaded successfully from: {}", configPath);
        } catch (IOException e) {
            logger.error("Failed to load configuration file", e);
            throw new RuntimeException("Configuration file not found: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property key '{}' not found in config file", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
