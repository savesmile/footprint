# footprint
a demo for graduation project

### consul 启动
```bash
./consul agent -dev -bootstrap-expect=1  -data-dir=/tmp/consul -node=server  -client 0.0.0.0 -ui
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

### vpn
```
首先，需要修改注册表regedit
[HKEY_LOCAL_MACHINE/SYSTEM/CurrentControlSet/Services/RasMan/Parameters]
ProhibitIPSec的值修改为1
```

### Nginx
```
cd /usr/local/nginx/sbin/
./nginx 
./nginx -s quit
重启加载配置文件 ./nginx -s reload
```
vi /etc/sysconfig/iptables

