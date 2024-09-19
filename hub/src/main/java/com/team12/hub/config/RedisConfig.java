//package com.team12.hub.config;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//import java.io.IOException;
//import java.util.List;
//
//@Configuration
//public class RedisConfig {
//    @Bean(name = "redisPageModule")
//    public SimpleModule pageModule() {
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(PageImpl.class, new PageImplDeserializer());
//        return module;
//    }
//}
