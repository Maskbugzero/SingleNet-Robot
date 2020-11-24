# SingleNet-Robot 闪讯机器人

　　自2016年9月以来，闪讯已没有任何方法可以固定密码，据说杭州下沙地区仍可以向营业厅申请固定密码，但小和山地区则完全拒绝。这个项目就是通过手机上的App设置定时任务，自动发送短信获取密码，然后通过运行在路由器上的API接口更新OpenWrt配置，实现自动更新密码的效果，达到曲线救国的目的。**本项目禁止一切商业用途**  
　　博客文章地址：[https://blog.kuretru.com/posts/fcbfc0a9.html](https://blog.kuretru.com/posts/fcbfc0a9.html)  

## 项目简介

* 项目完成情况
  * [ ] Go服务端
  * [x] Python服务端(2.x, 3.x)
  * [x] LuCI RPC服务端(推荐)
  * [X] Android客户端
  * [ ] iOS客户端
* 项目地址
  * [闪讯机器人](https://github.com/kuretru/SingleNet-Robot)，支持Android 9.0+版本
  * ~~[闪讯密码获取器](https://github.com/kuretru/SingleNet-Password)~~，Android 4.4版本稳定运行，已合并旧项目至本仓库，参考1.0版本
* 系统需求
  * 一个已经刷好OpenWrt系统的路由器
  * 路由器已配置好[闪讯拨号插件](https://github.com/miao1007/Openwrt-NetKeeper)
  * 一部运行Android 4.4+系统的手机
  * ~~路由器已配置好Python2运行环境(服务端目前没有Go版本)~~
* 已知问题
  * 大部分运行Android 8.0以上的手机会自动将定时任务杀死，并非软件的bug，请添加白名单，如无该功能，则需要每隔28小时，手动点击一次`立即更新密码`

## 服务端配置

### LuCI服务端

推荐使用LuCI服务端，仅占用少量闪存空间。

1. 在路由器上运行

```bash
opkg update
opkg install luci-mod-rpc
```

### Python服务端

Python服务端依赖于Python适用于软路由或大闪存的路由器。

1. 在路由器上下载服务端文件，或通过电脑上传

```bash
# Python 3.x
wget https://raw.githubusercontent.com/kuretru/SingleNet-Robot/master/server/Python/singlenet_robot_server.py -O singlenet_robot_server.py

# Python2.x
wget https://raw.githubusercontent.com/kuretru/SingleNet-Robot/master/server/Python/singlenet_robot_server.py2 -O singlenet_robot_server.py
```

2. (可选)修改`PORT`字段为你喜欢的端口
3. (可选)修改通信密钥`SECRET`字段为你喜欢的任意字符串，防止他人恶意调用APi服务
4. 设置开机自动启动
   1. 打开OpenWrt LuCI -> `系统` -> `启动脚本` -> `本地启动脚本`
   2. 于`exit 0`前追加
   3. `python /your path/singlenet_robot_server.py`
5. 开放防火墙对应端口(默认8079)，以允许外网访问
   1. 打开OpenWrt LuCI -> `网络` -> `防火墙` -> `流量规则`
   2. 打开`路由器端口`，协议`TCP`，端口`8079`
6. 如果你有自己的域名，设置DDNS，使内网、外网都可以通过统一的URL更新
7. 手动运行脚本，使用客户端更新密码测试

## 客户端配置

1. 去项目仓库下载编译好的apk文件，并安装至手机
2. 点击`服务器配置`，输入服务端地址及服务端网络接口名称，一个正确的服务端地址是类似于这样的：`http://192.168.1.1:8079`
3. 根据服务端类型，配置用户名密码或通讯密钥
4. 点击`测试服务器`，若成功点击`保存并退出`，若失败请仔细检查服务端地址是否设置正确或服务端是否开启
5. 在`调试面板`输入当前的闪讯账号及密码，并点击`手动更新用户名及密码`查看是否自动更新成功
6. 点击`注册定时任务`以开启自动更新密码功能

## 疑难杂症解决

1. 问：我使用苹果手机，有没有iOS版本？
   答：目前没有iOS版本，建议咸鱼购买一台Android手机，推荐运行Android4.4系统的老手机，使用旧版本项目[闪讯密码获取器](https://github.com/kuretru/SingleNet-Password)，可以自动稳定更新一学期。
2. 问：为什么断网以后无法更新密码了？
   答：目前大部分手机在Wi-Fi无Internet连接时，会自动通过流量通信，关闭手机的“无线网络不佳时使用流量”功能。
3. 问：总是连接失败怎么办？如何调试？
   答：若使用Python服务端，直接使用浏览器访问`/api/ping`，例如`http://192.168.1.1:8079/api/ping`，若显示`请求头未携带Access-Token`，则说明服务端运行正常，应将`http://192.168.1.1:8079`作为服务器地址填入手机客户端，即可成功连接。
4. 问：定时任务不生效怎么办？怎么定时更新？
   答：国产ROM均有白名单机制，会杀死白名单以外App的后台进程，将闪讯机器人加入白名单。
   * 华为EMUI：
     1. 设置->应用->应用启动管理->闪讯机器人->手动管理->允许后台活动
     2. 设置->搜索"电池优化"->左上角"所有应用"->闪讯机器人->不允许
5. 问：我还有其他问题怎么办？
   答：既然你已经找到这了，相信聪明的你一定可以解决，如果发现了软件的bug，可以去代码仓库发起issue。

## 版本历史

* v3.0 2020-10-23
* v2.4 2020-01-15
* v2.3 2019-05-25
* v2.2 2019-02-25
* v2.1 2019-02-20
* v1.0 2018-02-05

## 协议

　　本项目使用[GPL-3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)协议，你可以自由的使用或修改，但不得用于商业用途。
