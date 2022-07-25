#!/bin/sh
CheckProcess()
{
    if [ "$1" = "" ];
    then
        return 1
    fi

    PROCESS_NUM=$(ps -ef|grep "$1"|grep -v "grep"|wc -l)
    if [ "$PROCESS_NUM" = "1" ];
    then
        return 0
    else
        return 1
    fi
}

APP_HOME=$(cd $(dirname $0); pwd)

CheckProcess "$APP_HOME/application.jar"
CheckQQ_RET=$?
if [ "$CheckQQ_RET" = "0" ];
then
    echo "restart test ..."
    kill -9 $(ps -ef|grep $APP_HOME/application.jar |gawk '$0 !~/grep/ {print $2}' |tr -s '\n' ' ')
    sleep 1
    exec nohup /usr/local/jdk/bin/java -jar $APP_HOME/application.jar >/dev/null 2>&1 &
    echo "restart test success..."
else
    echo "restart test..."
    exec nohup /usr/local/jdk/bin/java -jar $APP_HOME/application.jar >/dev/null 2>&1 &
    echo "restart test success..."
fi
