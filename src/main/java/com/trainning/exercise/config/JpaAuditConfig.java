package com.trainning.exercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class JpaAuditConfig {
    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable("system");
    }

    @Bean
    DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(Instant.now());
    }
}
