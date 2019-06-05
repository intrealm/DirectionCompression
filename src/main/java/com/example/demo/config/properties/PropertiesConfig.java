package com.example.demo.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

	
	@Value( "${demo.comression.basedir" )
	public String baseDirectory;
}
