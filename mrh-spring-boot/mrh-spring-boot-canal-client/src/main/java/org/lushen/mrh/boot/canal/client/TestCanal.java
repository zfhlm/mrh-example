package org.lushen.mrh.boot.canal.client;

import org.lushen.mrh.boot.canal.client.core.CanalRetryConnector;
import org.lushen.mrh.boot.canal.client.core.CanalSubscriber;

/**
 * 执行测试
 * 
 * @author hlm
 */
public class TestCanal {
	 
    public static void main(String[] args) throws Exception {
 
        CanalSubscriber subscriber = new CanalPrintSubscriber();
 
        //单点连接
        //CanalRetryConnector connector = new CanalRetryConnector(subscriber);
        //connector.setHost("192.168.140.210");
        //connector.setPort(11111);
        //connector.setDestination("example");
        //connector.setSubscribe(".*\\..*");
        //connector.setBatchSize(1);
        //connector.afterPropertiesSet();
 
        //集群连接
        //List<Address> address = new ArrayList<Address>();
        //address.add(new Address("192.168.140.210", 11111));
        //address.add(new Address("192.168.140.211", 11111));
        //address.add(new Address("192.168.140.212", 11111));
        //
        //CanalRetryConnector connector = new CanalRetryConnector(subscriber);
        //connector.setAddresses(address);
        //connector.setDestination("example");
        //connector.setSubscribe(".*\\..*");
        //connector.setBatchSize(1);
        //connector.afterPropertiesSet();
 
        //zookeeper连接
        CanalRetryConnector connector = new CanalRetryConnector(subscriber);
        connector.setZkServers("192.168.140.210:2181,192.168.140.211:2181,192.168.140.212:2181");
        connector.setDestination("example");
        connector.setSubscribe(".*\\..*");
        connector.setBatchSize(1);
        connector.afterPropertiesSet();
 
    }
 
}