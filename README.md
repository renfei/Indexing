![Indexing - 搜索引擎推送工具 - SEO 工具箱](https://cdn.renfei.net/upload/image/2021/indexing_tools.gif)
# Indexing - 百度-必应-谷歌 搜索引擎推送工具
此工具利用「百度-必应-谷歌」站长工具或开放平台接口即时推送网站更新给搜索引擎，加快蜘蛛程序爬取与更新。  
此工具代码作者已经使用多年，同时作者正在探索 Swing/AWT 编程，此工具作为作者 Swing/AWT 编程处女作，顺便将技术能力通过可视化界面分享出来，让不懂编程的站长也可以使用 API 接口的便利。

## 安全声明
作为软件开发工程师的我，深知安全的重要性，因为此工具运行时需要站长提供 API 的 Token 令牌，这就相当于密码授权。  
为了证明此工具不会抓取上传站长的 Token 令牌，所以开源公布出来，欢迎监督。（PS:本来想闭源使用混淆编译发布，防止被仿冒）  
因为开源以后任何人可以利用源码修改制作小版本，站长们请认准 renfei.net 官网。如果发现仿冒请向我举报。

## 使用帮助
本工具基于 Java8 制作，如果您拥有 Java8 或更高的 JDK/JRE 环境，可以直接下载 Jar 包文件，使用如下命令即可启动：
```bash
java -jar Indexing.jar
```
如果您不确定自己的环境是否拥有 JDK/JRE，我还提供了环境打包版本，由于操作系统不同请下载对应的版本，执行其中的 start 脚本。

## 发布与下载
请下载对应的环境，执行 start 脚本：
- Github发布与下载：[https://github.com/renfei/Indexing/releases](https://github.com/renfei/Indexing/releases)
- Gitee发布与下载：[https://gitee.com/rnf/Indexing/releases](https://gitee.com/rnf/Indexing/releases)

## 其他说明
- 百度Token获取地址： https://ziyuan.baidu.com/linksubmit/index
- 必应Token获取地址：https://docs.microsoft.com/en-us/bingwebmaster/getting-access#using-api-key
- 谷歌JSON私钥获取：https://www.renfei.net/posts/1003342
- 谷歌上报需要本地是"你懂得"状态，否则网络不通，总是触发关键词「根据相关法律政策,该内容无法显示」
- 各个平台的接口提交配额与本工具无关，是各个平台分配给你的；例如百度快速收录是百度站长工具给予的权限，与是否使用本工具无关
- 本工具不会收集上报用户的Token，本工具代码已开源，欢迎监督，如遇仿制程序上报Token请联系 i@renfei.net

### 代码仓库
- [Main] Github:[https://github.com/renfei/Indexing](https://github.com/renfei/Indexing)
- [Mirror] Gitee:[https://gitee.com/rnf/Indexing](https://gitee.com/rnf/Indexing)
- [Mirror] Gitlab:[https://gitlab.com/renfei/Indexing](https://gitlab.com/renfei/Indexing)

### 求鼓励

如果这个项目帮助到了你，是否能给我点个免费的星星 (Star) 给个鼓励呢。高星项目我将持续关注努力更新的。