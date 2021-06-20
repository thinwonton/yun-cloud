package com.github.thinwonton.yuncloud.safety.xss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

/**
 * springboot 环境测试
 */
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootTestBootstrap.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class SpringbootEnvTest extends BaseTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testJackson() throws JsonProcessingException {
        Assert.assertNotNull(applicationContext);
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        BaseTest.Paper toSerialize = new BaseTest.Paper(dirtyHtml);
        String jsonStrToTest = objectMapper.writeValueAsString(toSerialize);
        BaseTest.Paper paper = objectMapper.readValue(jsonStrToTest, BaseTest.Paper.class);
        Assert.assertEquals(cleanedHtml, paper.getContent());
    }

    /**
     * 测试JACKSON转换request body
     */
    @Test
    public void testRequestByPostBody() throws Exception {

        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        BaseTest.Paper toSerialize = new BaseTest.Paper(dirtyHtml);
        String requestBody = objectMapper.writeValueAsString(toSerialize);

        String url = "/test/xssByPostBody";
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
                .andReturn();
        String returnContent = mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Assert.assertEquals(cleanedHtml, returnContent);
    }

    @Test
    public void testRequestByGetMethod() throws Exception {
        String url = "/test/xss";
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .queryParam("content", dirtyHtml)
        )
                .andReturn();
        String returnContent = mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Assert.assertEquals(cleanedHtml, returnContent);
    }

    /**
     * 测试Post方法提交FORM表单
     */
    @Test
    public void testFormRequestByPostMethod() throws Exception {
        String url = "/test/xss";
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .param("content", dirtyHtml)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String returnContent = mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Assert.assertEquals(cleanedHtml, returnContent);
    }

    @Test
    public void testFormRequestByPostMethodWithRequestParam() throws Exception {
        String url = "/test/xssWithRequestParam";
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .param("content", dirtyHtml)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String returnContent = mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Assert.assertEquals(cleanedHtml, returnContent);
    }

}
