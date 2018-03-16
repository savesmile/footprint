# footprint
a demo for graduation project

### consul 启动
```bash
./consul agent -server -bootstrap-expect=1  -data-dir=/tmp/consul -node=server-110 -bind=192.168.1.110  -client 0.0.0.0 -ui
```
http://47.95.121.41:8500/ui/#/dc1/services

### 端口占用
```bash
netstat -tlnp
```
java -jar xxxx.jar
nohup java -jar xxxx.jar

### scp
```bash
scp gateway-1.0.0-SNAPSHOT.jar root@47.95.121.41:/usr/local/projectSpace/
```


