
#### 注意

    当前项目 example 基于 spring cloud

    spring cloud 使用 zookeeper 作为注册中心，没有使用配置中心

    seata client 和 seata server 使用 zookeeper 作为注册中心和配置中心

#### 配置 seata-server

    第一步，下载安装包到服务器：

        cd /usr/local/software

        wget https://github.com/seata/seata/releases/download/v1.4.2/seata-server-1.4.2.tar.gz

        tar -zxvf seata-server-1.4.2.tar.gz

        cd seata

        mv seata-server-1.4.2/ .. && cd ..

        rm -rf seata && mv seata-server-1.4.2 ..

        cd ..&& ln -s ./seata-server-1.4.2/ seata

        ll /usr/local/seata

    第二步，导入 seata 配置 到 zookeeper：

        cd /usr/local/seata

        wget https://github.com/seata/seata/blob/develop/script/config-center/config.txt

        vi config.txt

        => 更改以下配置：

            service.vgroupMapping.seata-tx-service-group=seata-server
            service.seata-server.grouplist=127.0.0.1:8091
            store.mode=db
            store.lock.mode=db
            store.session.mode=db
            store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true
            store.db.user=root
            store.db.password=123456

        cd ./conf

        wget https://github.com/seata/seata/blob/develop/script/config-center/zk/zk-config.sh

        chmod 777 zk-config.sh

        ./zk-config.sh -h 127.0.0.1 -p 2181 -z /usr/local/zookeeper

    第三步，启动 seata server：

        cd /usr/local/seata

        ./bin/seata-server.sh

    (未完待添加)
