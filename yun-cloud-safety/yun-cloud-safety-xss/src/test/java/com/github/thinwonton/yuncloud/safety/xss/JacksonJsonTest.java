package com.github.thinwonton.yuncloud.safety.xss;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Assert;
import org.junit.Test;

/**
 * json的xss测试
 */
public class JacksonJsonTest extends BaseTest {

    /**
     * 测试json的xss清除
     */
    @Test
    public void testJsonClean() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        XssCleaner xssCleaner = new DefaultXssCleaner();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new JacksonXssCleanJsonDeserializer(xssCleaner));
        objectMapper.registerModule(module);

        Paper toSerialize = new Paper(dirtyHtml);
        String jsonStrToTest = objectMapper.writeValueAsString(toSerialize);
        Paper paper = objectMapper.readValue(jsonStrToTest, Paper.class);
        Assert.assertEquals(cleanedHtml, paper.getContent());
    }

}
