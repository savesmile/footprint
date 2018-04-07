#!/usr/bin/env bash
cd $1;
mvn clean package;
cd target;
scp $1-1.0.0-SNAPSHOT-exec.jar root@192.168.0.105:/usr/local/project;
ssh -nf root@192.168.0.105 "./publish.sh $1 $2";