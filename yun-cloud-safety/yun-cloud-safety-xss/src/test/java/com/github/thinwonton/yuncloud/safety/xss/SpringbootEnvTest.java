package com.github.thinwonton.yuncloud.safety.xss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * springboot 环境测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootTestBootstrap.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class SpringbootEnvTest extends BaseTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testJackson() throws JsonProcessingException {
        Assert.assertNotNull(applicationContext);
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        BaseTest.Page pageToSerialize = new BaseTest.Page(dirtyHtml);
        String jsonStrToTest = objectMapper.writeValueAsString(pageToSerialize);
        BaseTest.Page page = objectMapper.readValue(jsonStrToTest, BaseTest.Page.class);
        Assert.assertEquals(cleanedHtml, page.getContent());
    }

}
