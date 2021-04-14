package net.renfei.indexing.service;

import com.alibaba.fastjson.JSON;
import net.renfei.indexing.Application;
import net.renfei.sdk.http.HttpRequest;
import net.renfei.sdk.http.HttpResult;
import net.renfei.sdk.utils.HttpUtils;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static net.renfei.indexing.ui.MenuBar.openURL;

/**
 * 检查新版本服务
 *
 * @author renfei
 */
public class CheckVersionService {
    public static void checkVersion() {
        HttpRequest request = HttpRequest.create().url("https://gitee.com/api/v5/repos/rnf/Indexing/releases/latest").useSSL();
        try {
            HttpResult result = HttpUtils.get(request);
            String responseText = result.getResponseText();
            String latestTagName = JSON.parseObject(responseText).getString("tag_name");
            if (Application.VERSION.equals(latestTagName)) {
                JOptionPane.showMessageDialog(Application.MAIN_WINDOW.mainPanel,
                        "您当前使用的就是最新版。", "检查更新", JOptionPane.PLAIN_MESSAGE);
            } else {
                int resultInt = JOptionPane.showConfirmDialog(Application.MAIN_WINDOW.mainPanel,
                        "发现新版本：" + latestTagName + "，是否前往下载？", "检查更新", JOptionPane.YES_NO_OPTION);
                if (resultInt == 0) {
                    openURL(new URI("https://gitee.com/rnf/Indexing/releases"));
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Application.MAIN_WINDOW.mainPanel,
                    "检查失败，请手动前往「https://www.renfei.net/kitbox/indexing」检查。", "检查更新", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
