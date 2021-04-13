package net.renfei.indexing.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class BaiduService {
    private final String siteUrl;
    private final String baiduToken;

    public BaiduService(String siteUrl, String baiduToken) {
        this.siteUrl = siteUrl;
        this.baiduToken = baiduToken;
    }

    public String push(String url) throws IOException {
        return push(url, false);
    }

    public String push(String url, boolean isMobile) throws IOException {
        String pushUrl = "http://data.zz.baidu.com/urls?site=" + siteUrl + "&token=" + baiduToken;
        if (isMobile) {
            pushUrl = "http://data.zz.baidu.com/urls?site=" + siteUrl + "&token=" + baiduToken + "&type=daily";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse;
        HttpPost httpPost = new HttpPost(pushUrl);
        httpPost.addHeader("Content-Type", "text/plain");
        StringEntity entity = new StringEntity(url, ContentType.create("text/plain", "UTF-8"));
        httpPost.setEntity(entity);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        closeableHttpResponse = closeableHttpClient.execute(httpPost);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new RuntimeException("\r\n#### 请求错误 ####\r\n响应状态码：" + statusCode + "\r\n请求地址：" + pushUrl);
        }
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }
}
