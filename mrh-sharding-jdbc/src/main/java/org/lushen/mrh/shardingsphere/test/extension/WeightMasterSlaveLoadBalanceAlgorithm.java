package org.lushen.mrh.shardingsphere.test.extension;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.shardingsphere.spi.masterslave.MasterSlaveLoadBalanceAlgorithm;

//
// 配置文件示例，以下将会循环顺序选择 slave1、slave2、slave1 从数据源
//
// spring: 
//   shardingsphere: 
//     sharding: 
//       master-slave-rules: 
//         cluster-1: 
//           master-data-source-name: cluster-1-master
//           slave-data-source-names: cluster-1-slave-1, cluster-1-slave-2
//           load-balance-algorithm-type: WEIGHT_ROUND_ROBIN
//           props: 
//             cluster-1-slave-1.weight: 2
//             cluster-1-slave-2.weight: 1
//
/**
 * 加权轮询策略，从数据源配置权重
 * 
 * @author hlm
 */
public class WeightMasterSlaveLoadBalanceAlgorithm implements MasterSlaveLoadBalanceAlgorithm {

	private static final String WEIGHT_KEY = "%s.weight";

	private static final ConcurrentHashMap<String, NodeSelector> SELECTORS = new ConcurrentHashMap<String, NodeSelector>();

	private final Properties properties = new Properties();

	@Override
	public String getType() {
		return "WEIGHT_ROUND_ROBIN";
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public void setProperties(Properties properties) {
		System.err.println(properties);
		this.properties.putAll(properties);
	}

	@Override
	public String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames) {

		// 获取节点选择器
		NodeSelector selector = SELECTORS.computeIfAbsent(name, e -> {
			List<Node> nodes = slaveDataSourceNames.stream()
					.map(s -> new Node(s, getWeight(s, 1)))
					.filter(s -> s.getWeight() > 0)
					.collect(Collectors.toList());
			return new NodeSelector(nodes);
		});

		// 选择节点
		return selector.select().getDataSourceName();
	}

	/**
	 * 获取 slave 数据源权重
	 * 
	 * @param dataSourceName
	 * @param defaultValue
	 * @return
	 */
	private int getWeight(String dataSourceName, int defaultValue) {
		String weight = this.properties.getProperty(String.format(WEIGHT_KEY, dataSourceName));
		if (weight == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(weight);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 节点选择器
	 * 
	 * @author hlm
	 */
	private class NodeSelector {

		private final List<Node> nodes;

		private final AtomicInteger offset = new AtomicInteger(0);

		public NodeSelector(List<Node> nodes) {
			super();
			this.nodes = nodes;
		}

		public Node select() {

			if(this.nodes.isEmpty()) {
				return null;
			}

			// 使用相对公平的选择策略
			// 如果耗尽第一节点次数，再选择第二节点，权重数值较大时流量不平均

			synchronized (this) {
				// 轮询选择可用节点
				for(int i=0; i< nodes.size(); i++) {
					Node node = this.nodes.get(offset.getAndUpdate(prev -> prev==this.nodes.size()-1? 0 : prev+1));
					AtomicInteger remaining = node.getRemaining();
					if(remaining.getAndDecrement() > 0) {
						return node;
					}
				}
				// 可用次数已耗尽，重置所有状态
				this.offset.set(0);
				for(Node node : this.nodes) {
					node.getRemaining().set(node.getWeight());
				}
				// 重新选择可用节点
				return select();
			}

		}

	}

	/**
	 * 节点
	 * 
	 * @author hlm
	 */
	private class Node {

		private String dataSourceName;

		private int weight;

		private final AtomicInteger remaining;

		public Node(String dataSourceName, int weight) {
			super();
			this.dataSourceName = dataSourceName;
			this.weight = weight;
			this.remaining = new AtomicInteger(weight);
		}

		public String getDataSourceName() {
			return dataSourceName;
		}

		public int getWeight() {
			return weight;
		}

		public AtomicInteger getRemaining() {
			return remaining;
		}

	}

}
