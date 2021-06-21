package com.github.thinwonton.yuncloud.safety.xss;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

public class AntPathMatcherTest {
    @Test
    public void testAntPathMatcher() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        String testPattern1 = "/test1";
        Assert.assertTrue( antPathMatcher.match(testPattern1, "/test1"));
        Assert.assertFalse( antPathMatcher.match(testPattern1, "/test111"));
        Assert.assertFalse(antPathMatcher.match(testPattern1, "/test/111"));

        String testPattern2 = "/api/**";
        Assert.assertTrue( antPathMatcher.match(testPattern2, "/api"));
        Assert.assertTrue( antPathMatcher.match(testPattern2, "/api/a"));
        Assert.assertTrue( antPathMatcher.match(testPattern2, "/api/a/b"));
        Assert.assertFalse( antPathMatcher.match(testPattern2, "/api2"));
        Assert.assertFalse( antPathMatcher.match(testPattern2, "/api2/111"));

        String testPattern3 = "/**/*.html";
        Assert.assertTrue( antPathMatcher.match(testPattern3, "/a.html"));
        Assert.assertTrue( antPathMatcher.match(testPattern3, "/api/a.html"));
        Assert.assertTrue( antPathMatcher.match(testPattern3, "/api/a/a.html"));
    }
}
