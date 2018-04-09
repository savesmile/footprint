#!/usr/bin/env bash
cd $1;
mvn clean package;
cd target;
scp $1-1.0.0-SNAPSHOT-exec.jar root@47.95.121.41:/usr/local/projectSpace;
#ssh -nf root@47.95.121.41 "./publish.sh $1 $2";