package net.renfei.indexing.service;

import com.alibaba.fastjson.JSON;
import net.renfei.indexing.entity.ConfigVO;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 配置文件服务
 *
 * @author renfei
 */
public class ConfigFileService {
    public static ConfigVO getConfig() {
        File file = getConfigFile();
        if (file != null) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                int length = inputStream.available();
                byte[] bytes = new byte[length];
                inputStream.read(bytes);
                String str = new String(bytes, StandardCharsets.UTF_8);
                return JSON.parseObject(str, ConfigVO.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static void saveConfig(ConfigVO configVO) {
        File file = getConfigFile();
        if (file != null && configVO != null) {
            PrintStream stream = null;
            try {
                stream = new PrintStream(file);
                stream.print(JSON.toJSONString(configVO));
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteConfig() {
        File file = getConfigFile();
        if (file != null) {
            file.delete();
        }
    }

    private static File getConfigFile() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File file = new File(fsv.getHomeDirectory().getPath() + "/Indexing.conf");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }
}
