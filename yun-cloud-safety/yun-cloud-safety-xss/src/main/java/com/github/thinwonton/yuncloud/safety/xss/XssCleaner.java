package com.github.thinwonton.yuncloud.safety.xss;

public interface XssCleaner {

    /**
     * 清理 html, 防止XSS
	 *
     * @param html html
     * @return 清理后的数据
     */
    String clean(String html);

}