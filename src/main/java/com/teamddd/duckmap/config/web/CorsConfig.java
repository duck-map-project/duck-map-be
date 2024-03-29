package com.teamddd.duckmap.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Value("${props.allowed-origin-url}")
	private String allowedFrontUrl;
	@Value("${props.allowed-origin-https-url}")
	private String allowedFrontHttpsUrl;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(
				allowedFrontUrl,
				allowedFrontHttpsUrl,
				"http://localhost:3000")
			.allowCredentials(true)
			.allowedHeaders("*")
			.allowedMethods("*")
			.exposedHeaders("Authorization", "Set-Cookie");
	}

}

