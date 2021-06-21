package com.github.thinwonton.yuncloud.safety.xss;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 用于标记是否需要进行 XSS 清理
 */
public class XssCleanMarkerHandlerInterceptor implements HandlerInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * 拦截的路径规则。不设置时为全部拦截
     */
    private final XssProperties xssProperties;

    public XssCleanMarkerHandlerInterceptor(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response,
                             Object handler) throws Exception {
        // 非控制器请求直接跳出
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 处理注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        XssCleanIgnore xssCleanIgnore = handlerMethod.getMethodAnnotation(XssCleanIgnore.class);
        if (Objects.nonNull(xssCleanIgnore)) {
            // 注解的优先级最后，这里需要马上返回
            XssCleanMarker.disabled();
            return true;
        }

        String pathInfo = request.getPathInfo();
        final List<String> pathPatterns = xssProperties.getPathPatterns();
        final List<String> excludePatterns = xssProperties.getExcludePatterns();

        // 处理配置中的拦截路径
        boolean shouldXssFilter = true;
        if (CollectionUtils.isEmpty(excludePatterns)) {
            //如果排除名单没有，需要查看白名单有无。如果白名单有，不在白名单的不进行XSS过滤
            if (!CollectionUtils.isEmpty(pathPatterns)) {
                boolean match = pathPatterns.stream()
                        .anyMatch(pattern -> antPathMatcher.match(pattern, pathInfo));
                if (!match) {
                    shouldXssFilter = false;
                }
            }
        } else {
            boolean match = excludePatterns.stream()
                    .anyMatch(pattern -> antPathMatcher.match(pattern, pathInfo));
            if (match) {
                shouldXssFilter = false;
            }
        }

        if (shouldXssFilter) {
            XssCleanMarker.enabled();
        } else {
            XssCleanMarker.disabled();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        XssCleanMarker.clean();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        XssCleanMarker.clean();
    }
}
