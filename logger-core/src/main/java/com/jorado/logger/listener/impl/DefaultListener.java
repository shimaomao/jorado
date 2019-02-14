package com.jorado.logger.listener.impl;

import com.jorado.logger.AutoLoad;
import com.jorado.logger.Event;
import com.jorado.logger.EventContext;
import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.data.serializer.Serializer;
import com.jorado.logger.listener.Listener;
import com.jorado.logger.logging.LogLevel;

@AutoLoad
public class DefaultListener implements Listener {

    @Override
    public void run(EventContext context) {

        Event ev = context.getEvent();

        Serializer serializer = context.getSerializer();
        String log = serializer.serialize(ev, 0);
        if (ev.isError()) {
            context.getLogger().error(log);
            return;
        }

        if (ev.containsData(DataTypes.LEVEL)) {
            String level = ev.getValue(DataTypes.LEVEL, String.class);
            LogLevel logLevel = LogLevel.fromString(level);
            if (logLevel.equals(LogLevel.Off)) {
                return;
            }
            if (logLevel.equals(LogLevel.Debug)) {
                context.getLogger().debug(log);
                return;
            }
            if (logLevel.equals(LogLevel.Warn)) {
                context.getLogger().warn(log);
                return;
            }
            if (logLevel.equals(LogLevel.Trace)) {
                context.getLogger().trace(log);
                return;
            }
            if (logLevel.equals(LogLevel.Fatal) || logLevel.equals(LogLevel.Error)) {
                context.getLogger().error(log);
                return;
            }
        }
        context.getLogger().info(log);
    }
}
