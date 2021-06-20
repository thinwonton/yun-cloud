package com.github.thinwonton.yuncloud.safety.xss;

public class BaseTest {
    protected  String dirtyHtml = "<p><a href='http://www.baidu.com' οnclick='stealCookies()'> 百度一下，你就知道 </a></p>";
    protected String cleanedHtml = "<p><a href=\"http://www.baidu.com\"> 百度一下，你就知道 </a></p>";


    protected static class Page {
        private String content;

        public Page() {
        }

        public Page(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Page{");
            sb.append("content='").append(content).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
