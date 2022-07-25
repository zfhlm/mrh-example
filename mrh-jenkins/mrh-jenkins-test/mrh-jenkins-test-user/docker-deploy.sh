#!/bin/sh

# 常量定义
JAR_NAME=mrh-jenkins-test-user
JAR_VERSION=v1.0
REGISTRY_ADDR=192.168.140.136:5000

# 镜像容器名称
TAG_IMAGE_NAME=$REGISTRY_ADDR/$JAR_NAME:$JAR_VERSION
CONTIANER_SHORT_NAME=$JAR_NAME
CONTIANER_FULL_NAME=$JAR_NAME"_"$JAR_VERSION

# 停止运行相同名称容器
CONTIANER_ID=$(docker ps --filter name=$CONTIANER_SHORT_NAME* -q)
if [ $CONTIANER_ID != "" ]; then
	echo 'stop docker container id : $CONTIANER_ID'
    docker stop $CONTIANER_ID
fi

# 删除名称和版本相同的容器
CONTIANER_ID=$(docker ps -a --filter name=$CONTIANER_FULL_NAME -q)
if [ $CONTIANER_ID != "" ]; then
	echo 'remove docker container id : $CONTIANER_ID'
    docker rm $CONTIANER_ID
fi

# 拉取当前版本容器并启动
docker pull $TAG_IMAGE_NAME

# 启动容器
docker run -it -d --name $CONTIANER_FULL_NAME -p 8888:8888 $TAG_IMAGE_NAME

# 输出启动信息
CONTIANER_ID=$(docker ps -a --filter name=$CONTIANER_FULL_NAME -q)
echo 'start docker container id : $CONTIANER_ID'
