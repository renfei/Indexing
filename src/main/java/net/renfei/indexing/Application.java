package net.renfei.indexing;

import net.renfei.indexing.ui.MainWindow;
import net.renfei.indexing.ui.MenuBar;

import javax.swing.*;


/**
 * 程序入口
 *
 * @author renfei
 */
public class Application {
    public static final MainWindow MAIN_WINDOW = new MainWindow();
    public static final String VERSION = "1.0.2";
    public static void main(String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame("Indexing - 搜索引擎推送工具 - SEO 工具箱");
        frame.setContentPane(MAIN_WINDOW.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 618);
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(new MenuBar());
        MAIN_WINDOW.init();
        frame.setVisible(true);
    }
}
