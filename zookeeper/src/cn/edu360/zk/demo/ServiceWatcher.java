package cn.edu360.zk.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;


public class ServiceWatcher implements Watcher {
    ZookeeperClient zkClint = null;

    public void setZk(ZookeeperClient zkcli) {
        zkClint = zkcli;
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected ) {
            switch (event.getType()){
                case NodeChildrenChanged:

                case NodeCreated:

                case NodeDataChanged:

                case NodeDeleted:

                case None:

            }
        }
    }
}
