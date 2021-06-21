package com.github.thinwonton.yuncloud.safety.xss;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.springframework.util.StringUtils;

public class DefaultXssCleaner implements XssCleaner {

    public static final HtmlWhitelist WHITE_LIST = new HtmlWhitelist();

    @Override
    public String clean(String html) {
        if (StringUtils.hasText(html)) {
            return Jsoup.clean(html, WHITE_LIST);
        }
        return html;
    }

    private static class HtmlWhitelist extends Whitelist {

        public HtmlWhitelist() {
            //定义标签和属性的白名单

            addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "span", "embed", "object", "dl", "dt",
                    "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small",
                    "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul");

            addAttributes("a", "href", "title", "target");
            addAttributes("blockquote", "cite");
            addAttributes("col", "span");
            addAttributes("colgroup", "span");
            addAttributes("img", "align", "alt", "src", "title");
            addAttributes("ol", "start");
            addAttributes("q", "cite");
            addAttributes("table", "summary");
            addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width");
            addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width");
            addAttributes("video", "src", "autoplay", "controls", "loop", "muted", "poster", "preload");
            addAttributes("object", "width", "height", "classid", "codebase");
            addAttributes("param", "name", "value");
            addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess", "flashvars", "name", "type", "pluginspage");

            addAttributes(":all", "class", "style", "height", "width", "type", "id", "name");

            addProtocols("blockquote", "cite", "http", "https");
            addProtocols("cite", "cite", "http", "https");
            addProtocols("q", "cite", "http", "https");

        }

        @Override
        protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
            //不允许 javascript 开头的 src 和 href
            if ("src".equalsIgnoreCase(attr.getKey()) || "href".equalsIgnoreCase(attr.getKey())) {
                String value = attr.getValue();
                if (StringUtils.hasText(value) && value.toLowerCase().startsWith("javascript")) {
                    return false;
                }
            }

            //允许 base64 的图片内容
            if ("img".equals(tagName) && "src".equals(attr.getKey()) && attr.getValue().startsWith("data:;base64")) {
                return true;
            }

            return super.isSafeAttribute(tagName, el, attr);
        }
    }
}
