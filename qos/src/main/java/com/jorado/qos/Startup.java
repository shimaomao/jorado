package com.jorado.qos;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.qos.server.Server;

import java.util.concurrent.atomic.AtomicBoolean;

public class Startup {

    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    private static AtomicBoolean hasStarted = new AtomicBoolean(false);

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        try {

            if (!hasStarted.compareAndSet(false, true)) {
                return;
            }

            Server server = Server.getInstance();
            server.setPort(Constants.DEFAULT_PORT);
            server.setAcceptForeignIp(false);
            server.start();
            System.in.read();
        } catch (Throwable throwable) {
            logger.warn("Fail to start qos server: ", throwable);
        }
    }

    public static void stopServer() {
        if (hasStarted.compareAndSet(true, false)) {
            Server server = Server.getInstance();
            server.stop();
        }
    }
}
