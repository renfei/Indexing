package net.renfei.indexing.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 360 搜索
 *
 * @author renfei
 */
public class QiHu360Service {
    private final String token;

    public QiHu360Service(String token) {
        this.token = token;
    }

    public String push(String url) throws IOException {
        String pushUrl = "http://s.360.cn/so/zz.gif?url=" + url + "&sid=" + token + "&token=" + getParameter(url, token);
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse;
        HttpGet httpPost = new HttpGet(pushUrl);
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

    private static String getParameter(String url, String token) {
        String[] n = reverse(url.split("")), r = token.split("");
        StringBuilder i = new StringBuilder();
        for (int s = 0, o = 16; s < o; s++) {
            i.append(r[s]).append(n[s] == null ? "" : n[s]);
        }
        return i.toString();
    }

    private static String[] reverse(String[] arr) {
        String[] reverseArray = new String[arr.length];
        ArrayList<String> arraylist = new ArrayList<>();
        Collections.addAll(arraylist, arr);
        Collections.reverse(arraylist);
        for (int i = 0; i < arr.length; i++) {
            reverseArray[i] = (String) arraylist.get(i);
        }
        return reverseArray;
    }
}
