package net.renfei.indexing.service;

import net.renfei.indexing.ui.MainWindow;
import net.renfei.sdk.utils.BeanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashSet;
import java.util.Set;

public class ExtractSitemapService implements Runnable {
    private MainWindow mainWindow;

    public ExtractSitemapService(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void run() {
        String site = mainWindow.siteUrl.getText();
        if (BeanUtils.isEmpty(site)) {
            mainWindow.setLog("【站点URL】不能为空。");
            return;
        }
        if (site.endsWith("/")) {
            site += "sitemap.xml";
        } else {
            site += "/sitemap.xml";
        }
        mainWindow.setLog("访问 " + site);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(site);
            NodeList sitemapList = document.getElementsByTagName("sitemap");
            if (sitemapList.getLength() > 0) {
                mainWindow.setLog("检测到站点地图集合");
                for (int i = 0; i < sitemapList.getLength(); i++) {
                    Node item = sitemapList.item(i);
                    NodeList childNodes = item.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        String nodeName = childNodes.item(j).getTextContent().trim();
                        if (!nodeName.isEmpty() && "loc".equalsIgnoreCase(childNodes.item(j).getNodeName())) {
                            getUrl(nodeName);
                        }
                    }
                }
            } else {
                getUrl(site);
            }
            mainWindow.setLog("执行结束");
        } catch (Exception e) {
            mainWindow.setLog("\n[!] 发生错误：\r\n" + e.getMessage() + "\r\n如果您认为不是您的错误，请联系开发者：i@renfei.net。\r\n");
        }
    }

    private void getUrl(String url) {
        mainWindow.setLog("发现网站地图：" + url);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(url);
            NodeList sitemapList = document.getElementsByTagName("url");
            for (int i = 0; i < sitemapList.getLength(); i++) {
                Node item = sitemapList.item(i);
                NodeList childNodes = item.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    String nodeName = childNodes.item(j).getTextContent().trim();
                    if ("loc".equalsIgnoreCase(childNodes.item(j).getNodeName())) {
                        mainWindow.urls.append("\r\n" + nodeName);
                    }
                }
            }
        } catch (Exception e) {
            mainWindow.setLog("\n[!] 发生错误：\r\n" + e.getMessage() + "\r\n如果您认为不是您的错误，请联系开发者：i@renfei.net。\r\n");
        }
    }
}
