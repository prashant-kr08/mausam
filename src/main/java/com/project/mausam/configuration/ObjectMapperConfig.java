package com.project.mausam.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ObjectMapperConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper mapper = builder.build();
		return mapper;
	}

	@Bean("redisObjectMapper")
	public ObjectMapper redisObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, 
//				ObjectMapper.DefaultTyping.NON_FINAL,
//				JsonTypeInfo.As.PROPERTY);
	    System.out.println("Custom Redis ObjectMapper hashCode: " + mapper.hashCode());

		return mapper;
	}
	

}
