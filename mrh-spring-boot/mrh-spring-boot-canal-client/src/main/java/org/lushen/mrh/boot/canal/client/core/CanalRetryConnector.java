package org.lushen.mrh.boot.canal.client.core;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;

/**
 * canal 重试客户端
 *
 * @author hlm
 */
public class CanalRetryConnector extends CanalProperties implements InitializingBean, DisposableBean {

    private final Log log = LogFactory.getLog(getClass().getSimpleName());

    // 线程池
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // 连接重试次数
    private final AtomicLong retries = new AtomicLong(0);

    // 是否运行中
    private boolean isRunning = true;

    // canal 连接对象
    private CanalConnector connector;

    // canal 消费对象
    private List<CanalSubscriber> subscribers;

    public CanalRetryConnector(CanalSubscriber subscriber) {
        this(Arrays.asList(subscriber));
    }

    public CanalRetryConnector(List<CanalSubscriber> subscribers) {
        super();
        this.subscribers = Optional.ofNullable(subscribers).orElse(Collections.emptyList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // 创建连接对象
        if(zkServers != null) {
            this.connector = CanalConnectors.newClusterConnector(zkServers, destination, username, password);
        }
        if(addresses != null) {
            List<InetSocketAddress> socketAddresses = addresses.stream().map(e -> new InetSocketAddress(e.getHost(), e.getPort())).collect(Collectors.toList());
            this.connector = CanalConnectors.newClusterConnector(socketAddresses, destination, username, password);
        }
        if(this.connector == null) {
            this.connector = CanalConnectors.newSingleConnector(new InetSocketAddress(host, port), destination, username, password);
        }

        // 开始执行订阅
        this.executor.execute(() -> doRun());

    }

    private void doRun() {

        try {

            // 重试先断开连接
            if(retries.getAndIncrement() > 0) {
                connector.disconnect();
                Thread.sleep(retries.get()>20? 5000L:100L);
            }

            // 初始化连接
            connector.connect();
            if(subscribe != null) {
                connector.subscribe(subscribe);
            } else {
                connector.subscribe();
            }
            connector.rollback();

            while(isRunning) {

                // 拉取数据
                Message message = connector.getWithoutAck(batchSize);

                // 无数据休眠
                if(message.getId() == -1) {
                    Thread.sleep(50L);
                    continue;
                }

                // 消费数据
                try {
                    for(CanalSubscriber subscriber : subscribers) {
                        subscriber.subscribe(message);
                    }
                    connector.ack(message.getId());
                } catch (Exception e) {
                    connector.rollback(message.getId());
                }

            }

        } catch (Exception ex) {

            // 非退出异常，重新执行订阅
            if(isRunning) {
                log.warn("Retry to execute method doRun(), cause by: " + ex.getMessage(), ex);
                executor.execute(() -> doRun());
            } else {
                log.warn("Canal has been stopped and will not retry to execute method doRun()");
            }

        }

    }

    @Override
    public void destroy() throws Exception {

        try {

            // 关闭线程池
            if( ! executor.isTerminated() ) {
                isRunning = false;
                executor.shutdownNow();
            }

        } finally {

            // 关闭连接
            connector.disconnect();

        }

    }

}