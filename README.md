# Elasticsearch 6.4.3
```
下载：https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.4.3.zip
安装说明：https://www.elastic.co/guide/en/elasticsearch/reference/current/zip-windows.html

elasticsearch.yml配置需注意：
cluster.name: home 
node.name: node-home
指定该节点是否有资格被选举成为master节点，默认是true，es是默认集群中的第一台机器为master，如果这台机挂了就会重新选举
master node.master: true
允许该节点存储数据(默认开启)
node.data: true
```
# Metricbeat 6.4.3-windows-x86_64
* 下载：https://artifacts.elastic.co/downloads/beats/metricbeat/metricbeat-6.4.3-windows-x86_64.zip
* 安装说明：https://www.elastic.co/guide/en/beats/metricbeat/6.4/metricbeat-installation.html


# Filebeat 6.4.3-windows-x86_64
* 下载：https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.4.3-windows-x86_64.zip
* 安装说明：https://www.elastic.co/guide/en/beats/filebeat/6.4/filebeat-installation.html


# Maven构建项目

ESConfig.properties：
```
{
  es.master=192.168.20.1 修改为你的es配置的ip地址
  es.cluster_name = home  集群名字（必须和es配置一样）
  es.port = 9300     es传输接口（默认）
  es.metric_version = metricbeat-6.4.3
  es.fileset_version = filebeat-6.4.3
}
```


DatabasesConfig.properties
```
#dev
dao.url=localhost
dao.user = root
dao.password = root
dao.daobases = monitor //数据库名称
dao.port = 3306

```



