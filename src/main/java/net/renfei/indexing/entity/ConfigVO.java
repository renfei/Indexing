package net.renfei.indexing.entity;

/**
 * @author renfei
 */
public class ConfigVO {
    private String siteUrl;
    private String baiduToken;
    private String bingToken;
    private String googleJsonPath;
    private String soToken;

    public String getSiteUrl() {
        return siteUrl == null ? "" : siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getBaiduToken() {
        return baiduToken == null ? "" : baiduToken;
    }

    public void setBaiduToken(String baiduToken) {
        this.baiduToken = baiduToken;
    }

    public String getBingToken() {
        return bingToken == null ? "" : bingToken;
    }

    public void setBingToken(String bingToken) {
        this.bingToken = bingToken;
    }

    public String getGoogleJsonPath() {
        return googleJsonPath == null ? "" : googleJsonPath;
    }

    public void setGoogleJsonPath(String googleJsonPath) {
        this.googleJsonPath = googleJsonPath;
    }

    public String getSoToken() {
        return soToken == null ? "" : soToken;
    }

    public void setSoToken(String soToken) {
        this.soToken = soToken;
    }
}
