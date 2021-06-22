package com.github.thinwonton.yuncloud.safety.xss;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
class SpringbootTestBootstrap {

    @RequestMapping("/test")
    @Controller
    static class TestController {

        /**
         * 通过url的query params获取请求数据. 方法使用简单类型进行接收
         */
        @ResponseBody
        @GetMapping("/xssByGetUsingSimple")
        public String testUsingSimple(@RequestParam(name = "content") String content) {
            return content;
        }

        /**
         * 通过url的query params获取请求数据. 方法使用model进行接收
         */
        @ResponseBody
        @GetMapping("/xssByGetUsingModel")
        public String testGetUsingModel(BaseTest.Paper paper) {
            return paper.getContent();
        }

        /**
         * 通过 form 表单的方式获取数据. 方法使用简单类型进行接收
         */
        @ResponseBody
        @PostMapping("/xssByFormPostUsingSimple")
        public String testFormPostUsingSimple(@RequestParam(name = "content") String content) {
            return content;
        }

        /**
         * 通过 form 表单的方式获取数据. 方法使用model进行参数接收
         */
        @ResponseBody
        @PostMapping("/xssByFormPostUsingModel")
        public String testFormPostUsingModel(BaseTest.Paper paper) {
            return paper.getContent();
        }

        /**
         * 通过 request body 发送 json数据
         */
        @ResponseBody
        @PostMapping("/xssByPostJsonBody")
        public String testPostJsonBody(@RequestBody BaseTest.Paper paper) {
            return paper.getContent();
        }
    }
}
