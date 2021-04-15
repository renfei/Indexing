package net.renfei.indexing.entity;

/**
 * @author renfei
 */
public class ConfigVO {
    private String siteUrl;
    private String baiduToken;
    private String bingToken;
    private String googleJsonPath;

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getBaiduToken() {
        return baiduToken;
    }

    public void setBaiduToken(String baiduToken) {
        this.baiduToken = baiduToken;
    }

    public String getBingToken() {
        return bingToken;
    }

    public void setBingToken(String bingToken) {
        this.bingToken = bingToken;
    }

    public String getGoogleJsonPath() {
        return googleJsonPath;
    }

    public void setGoogleJsonPath(String googleJsonPath) {
        this.googleJsonPath = googleJsonPath;
    }
}
