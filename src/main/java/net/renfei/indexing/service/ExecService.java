package net.renfei.indexing.service;

import com.google.api.client.http.HttpResponse;
import net.renfei.indexing.entity.ConfigVO;
import net.renfei.indexing.ui.MainWindow;
import net.renfei.sdk.utils.BeanUtils;

import javax.swing.text.BadLocationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * @author renfei
 */
public class ExecService implements Runnable {
    private BaiduService baiduService;
    private BingService bingService;
    private GoogleService googleService;
    private MainWindow mainWindow;

    public ExecService(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

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

    private void execBaidu(String siteUrl) throws BadLocationException, IOException {
        mainWindow.setLog("开始执行百度普通收录推送");
        String token = mainWindow.baiduToken.getText();
        if (BeanUtils.isEmpty(token)) {
            mainWindow.setLog("【百度Token】为空，跳过执行。");
        } else {
            List<String> urlList = mainWindow.getUrlTexts();
            for (String url : urlList
            ) {
                mainWindow.setLog("推送：" + url);
                mainWindow.setLog("结果：" + execBaidu(siteUrl, token, url));
            }
        }
    }

    private void execBaiduKuaiSu(String siteUrl) throws BadLocationException, IOException {
        mainWindow.setLog("开始执行百度快速收录推送");
        String token = mainWindow.baiduToken.getText();
        if (BeanUtils.isEmpty(token)) {
            mainWindow.setLog("【百度Token】为空，跳过执行。");
        } else {
            List<String> urlList = mainWindow.getUrlTexts();
            for (String url : urlList
            ) {
                mainWindow.setLog("推送：" + url);
                mainWindow.setLog("结果：" + execBaiduKuaiSu(siteUrl, token, url));
            }
        }
    }

    private void execBing(String siteUrl) throws BadLocationException, IOException {
        mainWindow.setLog("开始执行必应推送");
        String token = mainWindow.bingToken.getText();
        if (BeanUtils.isEmpty(token)) {
            mainWindow.setLog("【必应Token】为空，跳过执行。");
        } else {
            List<String> urlList = mainWindow.getUrlTexts();
            for (String url : urlList
            ) {
                mainWindow.setLog("推送：" + url);
                mainWindow.setLog("结果：" + execBing(siteUrl, token, url));
            }
        }
    }

    private void execGoogle(String siteUrl) throws BadLocationException, IOException, GeneralSecurityException {
        mainWindow.setLog("开始执行谷歌推送");
        String token = mainWindow.googleJson.getText();
        if (BeanUtils.isEmpty(token) || "点击选择JSON文件".equals(token)) {
            mainWindow.setLog("【谷歌私钥】为空，跳过执行。");
        } else {
            List<String> urlList = mainWindow.getUrlTexts();
            for (String url : urlList
            ) {
                mainWindow.setLog("推送：" + url);
                mainWindow.setLog("结果：" + execGoogle(mainWindow.googleJson.getText(), url));
            }
        }
    }

    @Override
    public void run() {
        String site = mainWindow.siteUrl.getText();
        if (BeanUtils.isEmpty(site)) {
            mainWindow.setLog("【站点URL】不能为空。");
            return;
        }
        if (mainWindow.saveConfig.isSelected()) {
            ConfigVO configVO = new ConfigVO();
            configVO.setSiteUrl(site);
            configVO.setBaiduToken(mainWindow.baiduToken.getText());
            configVO.setBingToken(mainWindow.bingToken.getText());
            configVO.setGoogleJsonPath(mainWindow.googleJson.getText());
            ConfigFileService.saveConfig(configVO);
        } else {
            ConfigFileService.deleteConfig();
        }
        mainWindow.setLog("获取到【站点URL】：" + site);
        try {
            if (mainWindow.chkBaiduPuTong.isSelected()) {
                execBaidu(site);
            }
            if (mainWindow.chkBaiDuKuiSu.isSelected()) {
                execBaiduKuaiSu(site);
            }
            if (mainWindow.chkBing.isSelected()) {
                execBing(site);
            }
            if (mainWindow.chkGoogle.isSelected()) {
                execGoogle(site);
            }
        } catch (Exception e) {
            mainWindow.setLog("\n[!] 发生错误：\r\n" + e.getMessage() + "\r\n如果您认为不是您的错误，请联系开发者：i@renfei.net。\r\n");
        }
        mainWindow.setLog("执行结束");
    }
}
