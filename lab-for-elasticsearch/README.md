# elasticsearch

### elasticsearch 安装 


-  wget https://www.elastic.co/downloads/past-releases/elasticsearch-7-7-1

各个版本地址 ：[下载地址](https://www.elastic.co/cn/downloads/past-releases#elasticsearch)

- tar -xzf  ***

- 修改 /usr/local/elasticsearch-7.7.1/config/elasticsearch.yml
```
node.name: node-1
# 如果用腾讯云或者其他的话，这个地址要用内网地址，而不是公网地址
network.host: ip地址
# 记得安全组放开这个端口
http.port: 9200 
# 这里对应 node.name 的值
cluster.initial_master_node: ["node-1"]

```
- elasticsearch不推荐用root启动，所以创建新用户
```
groupadd es
# -g 指定组 -p 指定密码
useradd es -g es -p password 
# -R : 处理指定目录下的所有文件
chown -R es:es  elasticsearch-7.7.1/ 
```
- 启动 /usr/local/elasticsearch-7.7.1/bin/elasticsearch
```
su es
# -d 后台方式启动
./bin/elasticsearch -d
```
- 启动的时候内存不足，可以修改 /usr/local/elasticsearch-7.7.1/config/jvm.options
- 检查是否启动成功 ps -aux | grep elasticsearch / 浏览器访问 http://127.0.0.1:9200

### 安装 elasticsearch-analysis-ik 分词器插件
> 分词器版本要跟es的版本一致

- wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.7.1/elasticsearch-analysis-ik-7.7.1.zip
- 解压到 plugins/ik/ 目录下 unzip elasticsearch-analysis-ik-7.7.1.zip -d plugins/ik/
- 关闭es ps -ef | grep elasticsearch  / kill -9 PID
- 启动es

### 安装 kibana
> 版本尽量跟es一致

- 还是下载，解压， 省略了。
- 配置文件 kibana.yml
```
server.port: 5601
# 跟es上面配置的network.host一样，腾讯云的话用内网地址
server.host: IP地址
elasticsearch.hosts: ["http://118.24.70.22:9200"]
```

