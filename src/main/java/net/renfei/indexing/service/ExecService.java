package net.renfei.indexing.service;

import com.google.api.client.http.HttpResponse;
import net.renfei.sdk.utils.BeanUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

/**
 * @author renfei
 */
public class ExecService {
    private BaiduService baiduService;
    private BingService bingService;
    private GoogleService googleService;

    public String execBaidu(String siteUrl, String baiduToken, String url) throws IOException {
        if (baiduService == null) {
            baiduService = new BaiduService(siteUrl, baiduToken);
        }
        return baiduService.push(url);
    }

    public String execBaiduKuaiSu(String siteUrl, String baiduToken, String url) throws IOException {
        if (baiduService == null) {
            baiduService = new BaiduService(siteUrl, baiduToken);
        }
        return baiduService.push(url, true);
    }

    public String execBing(String siteUrl, String bingToken, String url) throws IOException {
        if (bingService == null) {
            bingService = new BingService(siteUrl, bingToken);
        }
        return bingService.push(url);
    }

    public String execGoogle(String googleJsonPath, String url) throws IOException, GeneralSecurityException {
        if (BeanUtils.isEmpty(googleJsonPath)) {
            throw new RuntimeException("请先选择谷歌私钥JSON文件");
        }
        if (googleJsonPath.endsWith(".json")) {
            File file = new File(googleJsonPath);
            if (file.exists()) {
                if (googleService == null) {
                    googleService = new GoogleService(googleJsonPath);
                }
                HttpResponse httpResponse = googleService.update(url);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getContent()));
                StringBuilder sb = new StringBuilder();
                String sTempOneLine;
                while ((sTempOneLine = bufferedReader.readLine()) != null) {
                    sb.append(sTempOneLine);
                }
                return sb.toString();
            } else {
                throw new RuntimeException(".json文件不存在");
            }
        } else {
            throw new RuntimeException("请选择.json的文件");
        }
    }
}
