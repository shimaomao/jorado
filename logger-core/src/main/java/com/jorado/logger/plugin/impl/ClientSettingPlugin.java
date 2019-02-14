package com.jorado.logger.plugin.impl;

import com.jorado.logger.AutoLoad;
import com.jorado.logger.EventContext;
import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.plugin.Plugin;

@AutoLoad
public class ClientSettingPlugin implements Plugin {

    @Override
    public void run(EventContext context) {

        if (null == context.getClientSettings() || context.getClientSettings().isEmpty())
            return;

        context.getEvent().addData(DataTypes.CLIENT_SETTING, context.getClientSettings());
    }
}
