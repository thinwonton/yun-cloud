package com.github.thinwonton.yuncloud.safety.xss;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/test")
@Controller
class TestController {

    @ResponseBody
    @PostMapping("/xssByPostBody")
    public String testPostBody(
           @RequestBody BaseTest.Paper paper) {
        return paper.getContent();
    }

    @ResponseBody
    @GetMapping("/xss")
    public String testGet(BaseTest.Paper paper) {
        return paper.getContent();
    }

    @ResponseBody
    @PostMapping("/xss")
    public String testPost(BaseTest.Paper paper) {
        return paper.getContent();
    }

    @ResponseBody
    @PostMapping("/xssWithRequestParam")
    public String testPost(@RequestParam(name = "content") String content) {
        return content;
    }
}
