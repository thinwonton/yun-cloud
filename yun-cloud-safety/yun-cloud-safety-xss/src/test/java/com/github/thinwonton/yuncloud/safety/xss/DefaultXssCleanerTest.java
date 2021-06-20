package com.github.thinwonton.yuncloud.safety.xss;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 测试 {@link DefaultXssCleaner}
 */
public class DefaultXssCleanerTest extends BaseTest{

    @Test
    public void testClean() {
        DefaultXssCleaner xssCleaner = new DefaultXssCleaner();

        //a标签的清除
        String html = dirtyHtml;
        String htmlToCleanATag = xssCleaner.clean(html);
        Assert.assertEquals(cleanedHtml, htmlToCleanATag);
    }
}