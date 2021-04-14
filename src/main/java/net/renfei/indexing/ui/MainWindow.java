package net.renfei.indexing.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import net.renfei.indexing.service.ExecService;
import net.renfei.sdk.utils.BeanUtils;
import net.renfei.sdk.utils.DateUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主窗体
 *
 * @author renfei
 */
public class MainWindow {
    private final ExecService execService = new ExecService();
    public JPanel mainPanel;
    public JTextArea urls;
    public JTextArea logText;
    public JCheckBox chkBaiduPuTong;
    public JCheckBox chkGoogle;
    public JCheckBox chkBaiDuKuiSu;
    public JButton execButton;
    public JButton googleJson;
    public JCheckBox chkBing;
    public JTextField siteUrl;
    public JTextField baiduToken;
    public JTextField bingToken;
    public JTextArea explain;
    public JSplitPane rightSplitPane;
    public JScrollPane urlsScroPane;
    public JScrollPane logsScroPane;

    public void init() {
        urlsScroPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        logsScroPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        execButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLog("开始执行");
                exec();
                setLog("执行结束");
            }
        });
        googleJson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser("/");
                int val = fc.showOpenDialog(null);
                if (val == JFileChooser.APPROVE_OPTION) {
                    googleJson.setText(fc.getSelectedFile().getPath());
                } else {
                    googleJson.setText("点击选择JSON文件");
                }
            }
        });
    }

    private void setLog(String log) {
        logText.append("\r\n" + DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + " : " + log);
    }

    private void exec() {
        String site = siteUrl.getText();
        if (BeanUtils.isEmpty(site)) {
            setLog("【站点URL】不能为空。");
            return;
        }
        setLog("获取到【站点URL】：" + site);
        try {
            if (chkBaiduPuTong.isSelected()) {
                execBaidu(site);
            }
            if (chkBaiDuKuiSu.isSelected()) {
                execBaiduKuaiSu(site);
            }
            if (chkBing.isSelected()) {
                execBing(site);
            }
            if (chkGoogle.isSelected()) {
                execGoogle(site);
            }
        } catch (Exception e) {
            setLog("\n[!] 发生错误：\r\n" + e.getMessage() + "\r\n如果您认为不是您的错误，请联系开发者：i@renfei.net。\r\n");
        }
    }

    private void execBaidu(String siteUrl) throws BadLocationException, IOException {
        setLog("开始执行百度普通收录推送");
        String token = baiduToken.getText();
        if (BeanUtils.isEmpty(token)) {
            setLog("【百度Token】为空，跳过执行。");
        } else {
            List<String> urlList = getUrlTexts();
            for (String url : urlList
            ) {
                setLog("推送：" + url);
                setLog("结果：" + execService.execBaidu(siteUrl, token, url));
            }
        }
    }

    private void execBaiduKuaiSu(String siteUrl) throws BadLocationException, IOException {
        setLog("开始执行百度快速收录推送");
        String token = baiduToken.getText();
        if (BeanUtils.isEmpty(token)) {
            setLog("【百度Token】为空，跳过执行。");
        } else {
            List<String> urlList = getUrlTexts();
            for (String url : urlList
            ) {
                setLog("推送：" + url);
                setLog("结果：" + execService.execBaiduKuaiSu(siteUrl, token, url));
            }
        }
    }

    private void execBing(String siteUrl) throws BadLocationException, IOException {
        setLog("开始执行必应推送");
        String token = bingToken.getText();
        if (BeanUtils.isEmpty(token)) {
            setLog("【必应Token】为空，跳过执行。");
        } else {
            List<String> urlList = getUrlTexts();
            for (String url : urlList
            ) {
                setLog("推送：" + url);
                setLog("结果：" + execService.execBing(siteUrl, token, url));
            }
        }
    }

    private void execGoogle(String siteUrl) throws BadLocationException, IOException, GeneralSecurityException {
        setLog("开始执行谷歌推送");
        String token = googleJson.getText();
        if (BeanUtils.isEmpty(token) || "点击选择JSON文件".equals(token)) {
            setLog("【谷歌私钥】为空，跳过执行。");
        } else {
            List<String> urlList = getUrlTexts();
            for (String url : urlList
            ) {
                setLog("推送：" + url);
                setLog("结果：" + execService.execGoogle(googleJson.getText(), url));
            }
        }
    }

    private List<String> getUrlTexts() throws BadLocationException {
        List<String> stringList = new ArrayList<>(urls.getRows());
        for (int i = 0; i < urls.getLineCount(); i++) {
            int start = urls.getLineStartOffset(i);
            int end = urls.getLineEndOffset(i);
            String str = urls.getText(start, end - start).replace("\r\n", "").replace("\n", "");
            if (!BeanUtils.isEmpty(str)) {
                if (str.startsWith(siteUrl.getText())) {
                    stringList.add(str);
                } else {
                    setLog("检测到【" + str + "】与站点URL不符，跳过处理。");
                }
            }
        }
        return stringList;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setContinuousLayout(true);
        splitPane1.setDividerLocation(400);
        splitPane1.setDividerSize(5);
        mainPanel.add(splitPane1, BorderLayout.CENTER);
        rightSplitPane = new JSplitPane();
        rightSplitPane.setDividerLocation(300);
        rightSplitPane.setDividerSize(5);
        rightSplitPane.setOrientation(0);
        splitPane1.setRightComponent(rightSplitPane);
        urlsScroPane = new JScrollPane();
        rightSplitPane.setLeftComponent(urlsScroPane);
        urls = new JTextArea();
        urls.setEditable(true);
        urls.setText("https://www.renfei.net/demo/1");
        urlsScroPane.setViewportView(urls);
        logsScroPane = new JScrollPane();
        rightSplitPane.setRightComponent(logsScroPane);
        logText = new JTextArea();
        logText.setBackground(new Color(-1118482));
        logText.setEditable(false);
        logText.setText("--== 运行日志 ==--");
        logsScroPane.setViewportView(logText);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(21, 2, new Insets(0, 0, 0, 0), 2, 4));
        splitPane1.setLeftComponent(panel1);
        final JLabel label1 = new JLabel();
        label1.setText("百度Token：");
        panel1.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(63, 16), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("必应Token：");
        panel1.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(63, 16), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(63, 11), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("上报项：");
        panel1.add(label3, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(63, 16), null, 0, false));
        chkBaiduPuTong = new JCheckBox();
        chkBaiduPuTong.setSelected(true);
        chkBaiduPuTong.setText("百度普通收录");
        panel1.add(chkBaiduPuTong, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chkBaiDuKuiSu = new JCheckBox();
        chkBaiDuKuiSu.setText("百度快速收录");
        panel1.add(chkBaiDuKuiSu, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chkGoogle = new JCheckBox();
        chkGoogle.setText("谷歌搜索");
        panel1.add(chkGoogle, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(63, 11), null, 0, false));
        execButton = new JButton();
        execButton.setText("执行");
        panel1.add(execButton, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("谷歌私钥：");
        panel1.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(63, 16), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(19, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("使用说明：");
        panel1.add(label5, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(63, 16), null, 0, false));
        chkBing = new JCheckBox();
        chkBing.setText("必应搜索");
        panel1.add(chkBing, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        siteUrl = new JTextField();
        siteUrl.setText("https://www.renfei.net");
        panel1.add(siteUrl, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("站点URL：");
        panel1.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(63, 16), null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel1.add(spacer6, new GridConstraints(20, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(11, 4052), null, 0, false));
        baiduToken = new JTextField();
        panel1.add(baiduToken, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        bingToken = new JTextField();
        panel1.add(bingToken, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel1.add(spacer7, new GridConstraints(18, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel1.add(spacer8, new GridConstraints(17, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panel1.add(spacer9, new GridConstraints(16, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        panel1.add(spacer10, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        explain = new JTextArea();
        explain.setBackground(new Color(-1118482));
        explain.setEditable(false);
        explain.setLineWrap(true);
        explain.setText("1.百度Token获取地址： https://ziyuan.baidu.com/linksubmit/index\n2.必应Token获取地址：https://docs.microsoft.com/en-us/bingwebmaster/getting-access\n3.谷歌JSON私钥获取：https://www.renfei.net/posts/1003342\n4.谷歌上报需要本地是翻墙状态，否则网络不通\n5.各个平台的接口提交配额与本工具无关，是各个平台分配给你的；例如百度快速收录是百度站长工具给予的权限，与是否使用本工具无关\n6.本工具不会收集上报用户的Token，本工具代码已开源，欢迎监督，如遇仿制程序上报Token请联系 i@renfei.net");
        explain.setWrapStyleWord(true);
        panel1.add(explain, new GridConstraints(15, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        googleJson = new JButton();
        googleJson.setText("点击选择JSON文件");
        panel1.add(googleJson, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
