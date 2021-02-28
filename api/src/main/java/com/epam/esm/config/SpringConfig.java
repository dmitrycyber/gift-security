package com.epam.esm.config;

import com.epam.esm.jpa.criteria.GiftCriteriaBuilder;
import com.epam.esm.jpa.criteria.TagCriteriaBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@PropertySource({
        "classpath:${spring.profiles.active}.db.properties",
        "classpath:application.properties"})
@EnableTransactionManagement
public class SpringConfig implements WebMvcConfigurer {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    }

    @Bean
    public MessageSource messageSource(@Value("${message-source.basename}") String messageSourceBaseName) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(messageSourceBaseName);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSourceResourceBundleLocator resourceBundle(MessageSource messageSource) {
        return new MessageSourceResourceBundleLocator(messageSource);
    }

    @Bean
    public ResourceBundleMessageInterpolator interpolator(MessageSourceResourceBundleLocator resourceBundle) {
        return new ResourceBundleMessageInterpolator(resourceBundle);
    }

    @Bean
    public LocalValidatorFactoryBean validator(ResourceBundleMessageInterpolator interpolator) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setMessageInterpolator(interpolator);
        return bean;
    }

    @Bean
    public GiftCriteriaBuilder giftCriteriaBuilder() {
        return new GiftCriteriaBuilder();
    }

    @Bean
    public TagCriteriaBuilder tagCriteriaBuilder() {
        return new TagCriteriaBuilder();
    }

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }
}
