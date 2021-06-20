package com.github.thinwonton.yuncloud.safety.xss;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.github.thinwonton.yuncloud.safety.xss.XssProperties.XSS_PROPERTIES_ENABLED;

@Configuration
@ConditionalOnProperty(value = XSS_PROPERTIES_ENABLED, havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@EnableConfigurationProperties({XssProperties.class})
public class WebXssConfiguration implements WebMvcConfigurer {

//    private XssProperties properties;
//
//    public WebXssConfiguration(XssProperties properties) {
//        this.properties = properties;
//    }

    @Bean
    @ConditionalOnMissingBean
    public XssCleaner xssCleaner() {
        return new DefaultXssCleaner();
    }

    @Bean
    @ConditionalOnClass(value = {ObjectMapper.class, Jackson2ObjectMapperBuilder.class})
    @ConditionalOnProperty(value = "yun.xss.jackson.enabled", havingValue = "true", matchIfMissing = true)
    public Jackson2ObjectMapperBuilderCustomizer jacksonXssCleanJsonDeserializerCustomer(XssCleaner xssCleaner) {
        return builder -> builder.deserializers(new JacksonXssCleanJsonDeserializer(xssCleaner));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        XssCleaner xssCleaner = xssCleaner();
        registry.addConverter(new XssCleanConverter(xssCleaner));
    }
}
