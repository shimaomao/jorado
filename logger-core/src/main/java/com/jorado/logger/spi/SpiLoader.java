package com.jorado.logger.spi;

import com.jorado.logger.util.StringUtils;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * SPI 扩展加载器
 */
public class SpiLoader {

    private SpiLoader() {
    }

    /**
     * 通过 SPI 去加载相应的扩展子类
     *
     * @param service
     * @param spiName
     * @param <T>
     * @return
     */
    public static <T> T load(Class<T> service, String spiName) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(service);
        Iterator<T> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (StringUtils.isNullOrWhiteSpace(spiName)) {
                return t;
            }
            Spi spi = t.getClass().getAnnotation(Spi.class);
            if (spi == null) {
                continue;
            }
            if (spiName.equals(spi.value())) {
                return t;
            }
        }
        return null;
    }

    public static <T> T load(Class<T> service) {
        ServiceLoader<T> servers = ServiceLoader.load(service);
        Iterator<T> serversIt = servers.iterator();
        if (serversIt.hasNext()) {
            return serversIt.next();
        } else {
            throw new IllegalStateException("No implementation found for SPI: " + service.getName());
        }
    }
}
