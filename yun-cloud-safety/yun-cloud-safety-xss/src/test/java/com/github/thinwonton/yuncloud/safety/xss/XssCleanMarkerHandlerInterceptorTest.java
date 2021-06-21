package com.github.thinwonton.yuncloud.safety.xss;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.List;

public class XssCleanMarkerHandlerInterceptorTest {

    /**
     * 测试 pathPatterns
     * @throws Exception
     */
    @Test
    public void testPathPatternSet() throws Exception {
        XssProperties xssProperties = new XssProperties();
        List<String> pathPatterns = xssProperties.getPathPatterns();
        pathPatterns.add("/test/**");

        XssCleanMarkerHandlerInterceptor interceptor = new XssCleanMarkerHandlerInterceptor(xssProperties);
        MockHttpServletResponse response = new MockHttpServletResponse();

        //命中path patterns
        XssCleanMarker.clean();
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.setPathInfo(test1_path);
        Method test1Method = InterceptorTestController.class.getMethod("test1");
        HandlerMethod test1HandlerMethod = new HandlerMethod(InterceptorTestController.class, test1Method);
        interceptor.preHandle(request1, response, test1HandlerMethod);
        Assert.assertTrue(XssCleanMarker.shouldClean());

        //不命中path patterns
        XssCleanMarker.clean();
        MockHttpServletRequest request3 = new MockHttpServletRequest();
        request3.setPathInfo(test3_path);
        Method test3Method = InterceptorTestController.class.getMethod("test3");
        HandlerMethod test3HandlerMethod = new HandlerMethod(InterceptorTestController.class, test3Method);
        interceptor.preHandle(request3, response, test3HandlerMethod);
        Assert.assertFalse(XssCleanMarker.shouldClean());

    }

    /**
     * 测试 pathPattern 和 excludePatterns 都设置
     */
    @Test
    public void testPathPatternAndExcludePatternsSet() throws Exception {
        XssProperties xssProperties = new XssProperties();
        List<String> excludePatterns = xssProperties.getExcludePatterns();
        excludePatterns.add("/test/**");

        XssCleanMarkerHandlerInterceptor interceptor = new XssCleanMarkerHandlerInterceptor(xssProperties);
        MockHttpServletResponse response = new MockHttpServletResponse();

        XssCleanMarker.clean();
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.setPathInfo(test1_path);
        Method test1Method = InterceptorTestController.class.getMethod("test1");
        HandlerMethod test1HandlerMethod = new HandlerMethod(InterceptorTestController.class, test1Method);
        interceptor.preHandle(request1, response, test1HandlerMethod);
        Assert.assertFalse(XssCleanMarker.shouldClean());

        XssCleanMarker.clean();
        MockHttpServletRequest request3 = new MockHttpServletRequest();
        request3.setPathInfo(test3_path);
        Method test3Method = InterceptorTestController.class.getMethod("test3");
        HandlerMethod test3HandlerMethod = new HandlerMethod(InterceptorTestController.class, test3Method);
        interceptor.preHandle(request3, response, test3HandlerMethod);
        Assert.assertTrue(XssCleanMarker.shouldClean());

    }

    /**
     * 测试 excludePatterns
     */
    @Test
    public void testExcludePatternsSet() throws Exception {
        XssProperties xssProperties = new XssProperties();
        List<String> excludePatterns = xssProperties.getExcludePatterns();
        excludePatterns.add("/test/**");

        XssCleanMarkerHandlerInterceptor interceptor = new XssCleanMarkerHandlerInterceptor(xssProperties);
        MockHttpServletResponse response = new MockHttpServletResponse();

        XssCleanMarker.clean();
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.setPathInfo(test1_path);
        Method test1Method = InterceptorTestController.class.getMethod("test1");
        HandlerMethod test1HandlerMethod = new HandlerMethod(InterceptorTestController.class, test1Method);
        interceptor.preHandle(request1, response, test1HandlerMethod);
        Assert.assertFalse(XssCleanMarker.shouldClean());

        XssCleanMarker.clean();
        MockHttpServletRequest request3 = new MockHttpServletRequest();
        request3.setPathInfo(test3_path);
        Method test3Method = InterceptorTestController.class.getMethod("test3");
        HandlerMethod test3HandlerMethod = new HandlerMethod(InterceptorTestController.class, test3Method);
        interceptor.preHandle(request3, response, test3HandlerMethod);
        Assert.assertTrue(XssCleanMarker.shouldClean());

    }

    /**
     * 测试 XssCleanIgnore 注解
     */
    @Test
    public void testXssCleanIgnoreAnno() throws Exception {
        XssProperties xssProperties = new XssProperties();
        List<String> excludePatterns = xssProperties.getExcludePatterns();
        excludePatterns.add("/test/**");

        XssCleanMarkerHandlerInterceptor interceptor = new XssCleanMarkerHandlerInterceptor(xssProperties);
        MockHttpServletResponse response = new MockHttpServletResponse();

        XssCleanMarker.clean();
        MockHttpServletRequest request5 = new MockHttpServletRequest();
        request5.setPathInfo(test5_path);
        Method test5Method = InterceptorTestController.class.getMethod("test5");
        HandlerMethod test5HandlerMethod = new HandlerMethod(InterceptorTestController.class, test5Method);
        interceptor.preHandle(request5, response, test5HandlerMethod);
        Assert.assertFalse(XssCleanMarker.shouldClean());

    }

    static final String test1_path = "/test/hello";
    static final String test2_path = "/test/bye";
    static final String test3_path = "/test3/boy";
    static final String test4_path = "/test4/girl";
    static final String test5_path = "/test5/girl";

    @Controller
    static class InterceptorTestController {
        @RequestMapping(test1_path)
        public void test1() {
        }

        @RequestMapping(test2_path)
        public void test2() {
        }

        @RequestMapping(test3_path)
        public void test3() {
        }

        @RequestMapping(test4_path)
        public void test4() {
        }

        @XssCleanIgnore
        @RequestMapping(test5_path)
        public void test5() {
        }
    }
}
