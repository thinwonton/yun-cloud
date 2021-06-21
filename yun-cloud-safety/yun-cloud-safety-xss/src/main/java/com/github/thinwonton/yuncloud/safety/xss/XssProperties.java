package com.github.thinwonton.yuncloud.safety.xss;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

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
     * 拦截的路径规则。不设置时为全部拦截
     */
    private List<String> pathPatterns = new ArrayList<>();

    /**
     * 放弃拦截的路径规则
     */
    private List<String> excludePatterns = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getPathPatterns() {
        return pathPatterns;
    }

    public void setPathPatterns(List<String> pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }
}
