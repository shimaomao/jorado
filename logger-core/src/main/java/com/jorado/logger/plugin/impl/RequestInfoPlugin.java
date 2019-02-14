package com.jorado.logger.plugin.impl;

import com.jorado.logger.EventContext;
import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.data.collector.Collector;
import com.jorado.logger.data.collector.RequestInfoCollector;
import com.jorado.logger.plugin.Plugin;

public class RequestInfoPlugin implements Plugin {

    @Override
    public void run(EventContext context) {
        if (context.getEvent().containsData(DataTypes.REQUEST_INFO))
            return;

        try {
            Collector collector = new RequestInfoCollector();

            Object info = collector.getData();

            context.getEvent().addData(DataTypes.REQUEST_INFO, info);

        } catch (Exception ex) {
            context.getLogger().error("Run requestInfo plugin error", ex);
        }
    }
}
