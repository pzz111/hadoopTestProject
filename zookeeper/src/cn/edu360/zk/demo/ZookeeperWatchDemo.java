package cn.edu360.zk.demo;

import java.util.List;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class ZookeeperWatchDemo {

	ZooKeeper zk = null;

	@Before
	public void init() throws Exception {
		// 构造一个连接zookeeper的客户端对象
		zk = new ZooKeeper("172.19.1.162:2181", 2000, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.toString());

				if (event.getState() == KeeperState.SyncConnected && event.getType() == EventType.NodeDataChanged) {
					System.out.println(event.getPath()); // 收到的事件所发生的节点路径
					System.out.println(event.getType()); // 收到的事件的类型
					System.out.println("赶紧换照片，换浴室里面的洗浴套装....."); // 收到事件后，我们的处理逻辑

					try {
						zk.getData("/aa", true, null);

					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
				}else if(event.getState() == KeeperState.SyncConnected && event.getType() == EventType.NodeChildrenChanged){
					
					System.out.println("子节点变化了......");
				}

			}
		});
	}

	@Test
	public void testGetWatch() throws Exception {
		Stat exists = zk.exists("/aa/bb", false);
		if(exists == null) {
			String create = zk.create("/aa/bb", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.printf(create);
		}
//		String create = zk.create("/service/4/1", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//		System.out.printf(create);
//		byte[] data = zk.getData("/aa", true, null); // 监听节点数据变化
//
//		List<String> children = zk.getChildren("/aa", true); //监听节点的子节点变化事件
//
//		Stat exists = zk.exists("/aa/cc", true);
//
//		System.out.println(new String(data, "UTF-8"));
//
//		Thread.sleep(Long.MAX_VALUE);

	}

}
