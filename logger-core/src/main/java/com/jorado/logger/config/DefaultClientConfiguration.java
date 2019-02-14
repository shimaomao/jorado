package com.jorado.logger.config;

import com.jorado.logger.data.collection.SettingsMap;
import com.jorado.logger.data.serializer.DefaultSerializer;
import com.jorado.logger.data.serializer.Serializer;
import com.jorado.logger.logging.Logger;
import com.jorado.logger.logging.Slf4jLogger;

/**
 * 未来此配置类里面的部分内容会通过扫描客户端配置文件来进行初始化
 * （譬如自动扫描用户自行事件的数据收集插件，序列化器等）
 */
public class DefaultClientConfiguration implements ClientConfiguration {

    private boolean enabled;
    private Serializer serializer;
    private Logger logger;
    private boolean counterEnabled;

    public DefaultClientConfiguration() {
        counterEnabled = false;
        enabled = true;
        serializer = new DefaultSerializer();
        logger = new Slf4jLogger();
    }

    @Override
    public SettingsMap getSettings() {
        return new SettingsMap();
    }

    /**
     * 是否启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 是否启用计数器
     *
     * @return
     */
    @Override
    public boolean isCounterEnabled() {
        return counterEnabled;
    }

    /**
     * 数据序列化器
     *
     * @return
     */
    @Override
    public Serializer getSerializer() {
        return serializer;
    }

    /**
     * 框架内日志记录器
     *
     * @return
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

}
