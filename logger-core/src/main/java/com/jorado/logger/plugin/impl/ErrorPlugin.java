package com.jorado.logger.plugin.impl;

import com.jorado.logger.EventContext;
import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.data.ext.ExceptionExtensions;
import com.jorado.logger.plugin.Plugin;
import com.jorado.logger.util.StringUtils;

public class ErrorPlugin implements Plugin {

    @Override
    public boolean enabled() {
        return false;
    }

    @Override
    public void run(EventContext context) {

        Exception exception = context.getContextData().getException();
        if (exception == null)
            return;


        if (StringUtils.isNullOrWhiteSpace(context.getEvent().getType()))
            context.getEvent().setType(DataTypes.ERROR);

        context.getEvent().addData(DataTypes.ERROR, ExceptionExtensions.toError(exception));
    }
}
