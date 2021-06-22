package com.github.thinwonton.yuncloud.safety.xss;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
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
    public void testGetUsingSimple() throws Exception {
        String url = "/test/xssByGetUsingSimple";
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .queryParam("content", dirtyHtml)
        )
                .andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        String returnContent = mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Assert.assertEquals(cleanedHtml, returnContent);
    }

    @Test
    public void testGetUsingModel() throws Exception {
        String url = "/test/xssByGetUsingModel";
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .queryParam("content", dirtyHtml)
        )
                .andReturn();
        String returnContent = mvcResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Assert.assertEquals(cleanedHtml, returnContent);
    }

    @Test
    public void testFormPostUsingSimple() throws Exception {
        String url = "/test/xssByFormPostUsingSimple";
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
    public void testFormPostUsingModel() throws Exception {
        String url = "/test/xssByFormPostUsingModel";
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
    public void testPostJsonBody() throws Exception {

        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        BaseTest.Paper toSerialize = new BaseTest.Paper(dirtyHtml);
        String requestBody = objectMapper.writeValueAsString(toSerialize);

        String url = "/test/xssByPostJsonBody";
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

}
