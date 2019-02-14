package com.jorado.logger.plugin.impl;

import com.jorado.logger.CacheManager;
import com.jorado.logger.EventContext;
import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.data.collector.Collector;
import com.jorado.logger.data.collector.JvmInfoCollector;
import com.jorado.logger.plugin.Plugin;

public class JvmInfoPlugin implements Plugin {

    private static final String CACHE_KEY = DataTypes.JVM_INFO;

    private static final int EXPIRE_TIME = 10;

    @Override
    public void run(EventContext context) {
        if (context.getEvent().containsData(DataTypes.JVM_INFO))
            return;

        try {

            Object info = CacheManager.getDefault().get(CACHE_KEY);

            if (null == info) {

                Collector collector = new JvmInfoCollector();

                info = collector.getData();

                CacheManager.getDefault().set(CACHE_KEY, info, EXPIRE_TIME);
            }

            context.getEvent().addData(DataTypes.JVM_INFO, info);

        } catch (Exception ex) {
            context.getLogger().error("Run jvmInfo plugin error", ex);
        }
    }
}
