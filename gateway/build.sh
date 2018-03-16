#!/bin/sh
mvn clean package -Dmaven.test.skip=true -pl gateway -f ../pom.xml -am
if [ $? -ne 0 ]; then
    echo "\n [BUILD ERROR]mvn install failed"
    exit 1
fi

all=$*

if ${#all[*]} -eq 0; then
    all[0]='latest'
fi

for version in ${all}
do
    echo ${version}
    sudo docker build -t gateway:${version} .
    sudo docker tag gateway:${version} hub.c.163.com/mofang23/gateway:${version}
    sudo docker push hub.c.163.com/mofang23/gateway:${version}
done