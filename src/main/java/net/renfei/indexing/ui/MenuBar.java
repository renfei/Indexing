package net.renfei.indexing.ui;

import net.renfei.indexing.Application;
import net.renfei.indexing.service.CheckVersionService;
import net.renfei.sdk.utils.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 菜单条
 *
 * @author renfei
 */
public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(createFileMenu());
        add(createEditMenu());
        setVisible(true);
    }

    private JMenu createFileMenu() {
        JMenu menu = new JMenu("系统(S)");
        JMenuItem item;
        item = new JMenuItem("工具箱(T)");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openURL(new URI("https://www.renfei.net/kitbox?spm=application.indexing.st"));
                } catch (URISyntaxException uriSyntaxException) {
                    JOptionPane.showMessageDialog(null, "打开失败，请手动打开: https://www.renfei.net/kitbox");
                }
            }
        });
        menu.add(item);
        menu.addSeparator();
        item = new JMenuItem("退出(E)");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(item);
        return menu;
    }

    private JMenu createEditMenu() {
        JMenu menu = new JMenu("帮助(H)");
        JMenuItem item;
        item = new JMenuItem("主页(P)");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openURL(new URI("https://www.renfei.net/kitbox/indexing?spm=application.indexing.hp"));
                } catch (URISyntaxException uriSyntaxException) {
                    JOptionPane.showMessageDialog(null, "打开失败，请手动打开: https://www.renfei.net/kitbox/indexing");
                }
            }
        });
        menu.add(item);
        JMenu menuOpen = new JMenu("开源(O)");
        item = new JMenuItem("Github");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openURL(new URI("https://github.com/renfei/Indexing"));
                } catch (URISyntaxException uriSyntaxException) {
                    JOptionPane.showMessageDialog(null, "打开失败，请手动打开: https://github.com/renfei/Indexing");
                }
            }
        });
        menuOpen.add(item);
        item = new JMenuItem("Gitee");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openURL(new URI("https://gitee.com/rnf/Indexing"));
                } catch (URISyntaxException uriSyntaxException) {
                    JOptionPane.showMessageDialog(null, "打开失败，请手动打开: https://gitee.com/rnf/Indexing");
                }
            }
        });
        menuOpen.add(item);
        menu.add(menuOpen);
        menu.addSeparator();
        item = new JMenuItem("更新(U)");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckVersionService.checkVersion();
            }
        });
        menu.add(item);
        item = new JMenuItem("关于(A)");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder("\t\t--== 关于==--\t\t").append("\r\n");
                sb.append("\r\n");
                sb.append("版本号：" + Application.VERSION).append("\r\n");
                sb.append("「Indexing」是一个集合了百度、必应、谷歌搜索引擎网址推送的工具。").append("\r\n");
                sb.append("主要用于即时向搜索引擎蜘蛛推送本站的更新动态，加快蜘蛛程序爬取和更新。").append("\r\n");
                sb.append("\r\n");
                sb.append("Copyright © ").append(DateUtils.getDate("yyyy")).append(" RENFEI.NET All rights reserved.").append("\r\n");
                JOptionPane.showMessageDialog(Application.MAIN_WINDOW.mainPanel,
                        sb.toString(), "关于", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menu.add(item);
        return menu;
    }

    public static void openURL(URI uri) {
        try {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "打开失败，请手动打开:" + uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "打开失败，请手动打开:" + uri);
        }
    }
}
