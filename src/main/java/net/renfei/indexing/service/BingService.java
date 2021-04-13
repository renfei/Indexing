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

public class BingService {
    private final String siteUrl;
    private final String bingToken;

    public BingService(String siteUrl, String bingToken) {
        this.siteUrl = siteUrl;
        this.bingToken = bingToken;
    }

    public String push(String url) throws IOException {
        String pushUrl = "https://ssl.bing.com/webmaster/api.svc/json/SubmitUrlbatch?apikey=" + bingToken;
        String json = "{\"siteUrl\":\"" + siteUrl + "\",\"urlList\":[\"" + url + "\"]}";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse;
        HttpPost httpPost = new HttpPost(pushUrl);
        httpPost.addHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(json, ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(entity);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        closeableHttpResponse = closeableHttpClient.execute(httpPost);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new RuntimeException("\r\n#### 请求错误 ####\r\n响应状态码："
                    + statusCode
                    + "\r\n请求地址："
                    + pushUrl);
        }
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }
}
