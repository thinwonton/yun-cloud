package com.github.thinwonton.yuncloud.safety.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * jackson的反序列化时的html xss过滤器
 */
public class JacksonXssCleanJsonDeserializer extends JsonDeserializer<String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(JacksonXssCleanJsonDeserializer.class);

    private final XssCleaner xssCleaner;

    public JacksonXssCleanJsonDeserializer(XssCleaner xssCleaner) {
        this.xssCleaner = xssCleaner;
    }

    @Override
    public Class<?> handledType() {
        return String.class;
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        // XSS clean
        String text = p.getValueAsString();
        if (StringUtils.hasText(text) && XssCleanMarker.shouldClean()) {
            String cleanText = xssCleaner.clean(text);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Json property value: [{}] cleaned up by JacksonXssCleanJsonDeserializer, current value is:{}.", text, cleanText);
            }
            return cleanText;
        }
        return text;
    }
}
