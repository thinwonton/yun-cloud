package com.github.thinwonton.yuncloud.safety.xss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 对请求数据过滤xss
 */
public class XssCleanConverter implements Converter<String, String> {

    private final Logger LOGGER = LoggerFactory.getLogger(XssCleanConverter.class);

    private XssCleaner xssCleaner;

    public XssCleanConverter(XssCleaner xssCleaner) {
        this.xssCleaner = xssCleaner;
    }

    @Override
    public String convert(String source) {
        if (StringUtils.hasText(source)) {
            String value = xssCleaner.clean(source);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("request param [{}] cleaned up by XssCleanConverter, current value is:{}.", source, value);
            }
            return value;
        }
        return source;
    }
}
