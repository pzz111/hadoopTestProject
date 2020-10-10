package cn.edu360.zk.demo;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.application.Application;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ZookeeperClient {

	ZooKeeper zk = null;
	String path;
	String node;
	String ipPort;

	ZookeeperClient(String serviceName,String ipPortStr){
		path="/service";
		node=serviceName;
		ipPort=ipPortStr;
	}


	public void connectZK() {
		// 构造一个连接zookeeper的客户端对象
		try {
			ServiceWatcher watcherTest = new ServiceWatcher();
			watcherTest.setZk(this);
			zk = new ZooKeeper("172.19.1.162:2181", 2000, watcherTest);
		}catch (Exception e){
			//TODO log
		}
	}

	public void registerServer(){
		try {

			//先判断注册节点的父节点是否存在，如果不存在，则创建
			Stat stat = zk.exists(path, false);
			if (stat == null) {
				zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			// 注册服务器数据到zk的约定注册节点下
			String create = zk.create(path + "/" + node, (ipPort).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			//TODO log
		}catch (Exception e){
			//todo log
		}
	}

	public void registerServerStandalone(){
		try {

			//先判断注册节点的父节点是否存在，如果不存在，则创建
			Stat stat = zk.exists(path, false);
			if (stat == null) {
				registerServer();
			}else {
				Stat nodeStat = zk.exists(path + "/" + node, false);
				if(nodeStat == null){
					registerServer();
				}else {
					//TODO stop
				}
			}
		}catch (Exception e){
			//todo log
		}
	}


}
