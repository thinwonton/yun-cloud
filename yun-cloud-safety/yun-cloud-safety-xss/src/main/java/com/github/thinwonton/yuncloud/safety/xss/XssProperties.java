package com.github.thinwonton.yuncloud.safety.xss;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.thinwonton.yuncloud.safety.xss.XssProperties.XSS_PROPERTIES_PREFIX;

@ConfigurationProperties(prefix = XSS_PROPERTIES_PREFIX)
public class XssProperties {

    public final static String XSS_PROPERTIES_PREFIX = "yun.xss";

    public final static String XSS_PROPERTIES_ENABLED = XSS_PROPERTIES_PREFIX + ".enabled";

    /**
     * 全局启用xss过滤，默认启用
     */
    private boolean enabled = true;

    /**
     * jackson的相关配置
     */
    private JacksonXssProperties jackson;

    public JacksonXssProperties getJackson() {
        return jackson;
    }

    public void setJackson(JacksonXssProperties jackson) {
        this.jackson = jackson;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private static class JacksonXssProperties {
        /**
         * 启用jackson的xss过滤，默认启用
         */
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

}
