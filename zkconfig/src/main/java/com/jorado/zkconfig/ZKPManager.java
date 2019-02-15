package com.jorado.zkconfig;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZKPManager implements Watcher, AutoCloseable {

    private static Logger logger = LoggerFactory.getLogger(ZKPManager.class);
    private static final int sessionTimeout = 10000;

    private ZooKeeper zk = null;
    private CountDownLatch downLoatch = new CountDownLatch(1);
    public ZKPWatcher updateHandler, expiredHandler, childrenChangedHandler, connectionHandler;

    public void open() {
        open(ZKPSettings.ZOOKEEPER_ADDRESS);
    }

    public void open(String path) {
        this.close();
        try {
            zk = new ZooKeeper(path, sessionTimeout, this);
            downLoatch.await();
        } catch (Exception e) {
            logger.error("Connection zookeeper exception : ", e);
        }
    }

    @Override
    public void close() {
        try {
            if (zk != null) {
                this.zk.close();
            }
        } catch (InterruptedException e) {
            logger.error("Close zookeeper exception : ", e);
        }
    }

    public String readData(String path, boolean needWatch) {
        try {
            return new String(this.zk.getData(path, needWatch, null));
        } catch (Exception e) {
            logger.error("Read Date exception : ", e);
            return "";
        }
    }

    public Stat writeData(String path, String data) {
        try {
            return this.zk.setData(path, data.getBytes(), -1);
        } catch (KeeperException e) {
            logger.error("Write Date exception", e);
        } catch (InterruptedException e) {
            logger.error("Write Date thread exception", e);
        }
        return null;
    }

    public boolean createPath(String path, String data) {
        try {
            this.zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        } catch (Exception e) {
            logger.error("Create path exception", e);
            return false;
        }
    }


    public List<String> getChildren(String path, boolean needWatch) {
        try {
            List<String> nodes = this.zk.getChildren(path, needWatch);
            return nodes;
        } catch (Exception e) {
            logger.error("Get children exception", e);
            return null;
        }
    }

    @Override
    public void process(WatchedEvent event) {
        KeeperState keeperState = event.getState();
        EventType eventType = event.getType();
        String path = event.getPath();
        if (KeeperState.SyncConnected == keeperState) {
            if (EventType.None == eventType) {
                System.out.println("成功连接上ZK服务器");
                downLoatch.countDown();
                if (connectionHandler != null) {
                    connectionHandler.watch(path, this);
                }

            } else if (EventType.NodeDataChanged == eventType) {
                if (updateHandler != null) {
                    updateHandler.watch(path, this);
                }
            }
            //-----------------------------------------
            else if (EventType.NodeChildrenChanged == eventType) {
                if (childrenChangedHandler != null) {
                    childrenChangedHandler.watch(path, this);
                }
            }
            //-----------------------------------------
        } else if (KeeperState.Disconnected == keeperState) {
            if (expiredHandler != null) {
                expiredHandler.watch(path, this);
            }
        } else if (KeeperState.Expired == keeperState) {
            if (expiredHandler != null) {
                expiredHandler.watch(path, this);
            }
        } else {
            logger.info("zookeeper keeperState has problem : " + keeperState);
        }
    }

    public void createZnode(String znode) {
        try {
            znode = this.zk.create(znode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception ex) {
            logger.error("Create node error : " + ex);
        }
    }
}
