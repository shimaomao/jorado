package com.jorado.logger.config;

import com.jorado.logger.data.collection.SettingsMap;
import com.jorado.logger.data.serializer.Serializer;
import com.jorado.logger.logging.Logger;

/**
 * 项目可以在客户端自行设置一些本地化的数据
 */
public interface ClientConfiguration {

    SettingsMap getSettings();

    boolean isEnabled();

    boolean isCounterEnabled();

    Serializer getSerializer();

    Logger getLogger();
}
