package com.example.netris.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import java.time.Duration;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
/**
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/EnableAsync.html
 * The @EnableAsync annotation switches on Spring’s ability to run @Async methods in a background thread pool.
 * By default, Spring will be searching for an associated thread pool definition:
 * either a unique TaskExecutor bean in the context, or an Executor bean named "taskExecutor" otherwise.
 * Add the @Bean annotation to the getAsyncExecutor() method if you want a fully managed bean.
 * In such circumstances it is no longer necessary to manually call the executor.initialize() method
 * as this will be invoked automatically when the bean is initialized.
 */
public class CameraConfiguration implements AsyncConfigurer {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HttpEntity<?> getHttpEntity() {
        return new HttpEntity<>(null);
    }

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("Vashetkov-");
        return executor;
    }
}
