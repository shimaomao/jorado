package com.jorado.logger.listener.impl;

import com.jorado.logger.Event;
import com.jorado.logger.EventContext;
import com.jorado.logger.data.serializer.Serializer;
import com.jorado.logger.listener.Listener;

/**
 * 控制台监听器
 */
public class ConsoleListener implements Listener {

    @Override
    public void run(EventContext context) {
        Event ev = context.getEvent();

        Serializer serializer = context.getSerializer();

        String body = serializer.serialize(ev, 0);

        System.out.println(body);
    }
}
