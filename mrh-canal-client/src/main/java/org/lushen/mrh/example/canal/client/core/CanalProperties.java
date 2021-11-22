package org.lushen.mrh.example.canal.client.core;

import java.util.List;

/**
 * canal properties
 *
 * @author hlm
 */
public class CanalProperties {
 
    protected String zkServers;            // canal zookeeper连接地址
 
    protected List<Address> addresses;     // canal 集群连接地址
 
    protected String host = "localhost";   // canal host
 
    protected int port = 11111;            // canal port
 
    protected String destination;          // canal instance
 
    protected String username;             // canal username
 
    protected String password;             // canal password
 
    protected String subscribe;            // canal subscribe
 
    protected int batchSize = 1;           // canal batchSize
 
    public String getZkServers() {
        return zkServers;
    }
 
    public void setZkServers(String zkServers) {
        this.zkServers = zkServers;
    }
 
    public List<Address> getAddresses() {
        return addresses;
    }
 
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
 
    public String getHost() {
        return host;
    }
 
    public void setHost(String host) {
        this.host = host;
    }
 
    public int getPort() {
        return port;
    }
 
    public void setPort(int port) {
        this.port = port;
    }
 
    public String getDestination() {
        return destination;
    }
 
    public void setDestination(String destination) {
        this.destination = destination;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getSubscribe() {
        return subscribe;
    }
 
    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }
 
    public int getBatchSize() {
        return batchSize;
    }
 
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
 
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[zkServers=");
        builder.append(zkServers);
        builder.append(", addresses=");
        builder.append(addresses);
        builder.append(", host=");
        builder.append(host);
        builder.append(", port=");
        builder.append(port);
        builder.append(", destination=");
        builder.append(destination);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", subscribe=");
        builder.append(subscribe);
        builder.append(", batchSize=");
        builder.append(batchSize);
        builder.append("]");
        return builder.toString();
    }
 
    public static class Address {
 
        private String host;
 
        private int port;
 
        public Address() {
            super();
        }
 
        public Address(String host, int port) {
            super();
            this.host = host;
            this.port = port;
        }
 
        public String getHost() {
            return host;
        }
 
        public void setHost(String host) {
            this.host = host;
        }
 
        public int getPort() {
            return port;
        }
 
        public void setPort(int port) {
            this.port = port;
        }
 
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(host=");
            builder.append(host);
            builder.append(", port=");
            builder.append(port);
            builder.append(")");
            return builder.toString();
        }
 
    }
 
}