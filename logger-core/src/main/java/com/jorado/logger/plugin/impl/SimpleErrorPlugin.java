package com.jorado.logger.plugin.impl;

import com.jorado.logger.AutoLoad;
import com.jorado.logger.EventContext;
import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.data.ext.ExceptionExtensions;
import com.jorado.logger.plugin.Plugin;
import com.jorado.logger.util.StringUtils;


@AutoLoad
public class SimpleErrorPlugin implements Plugin {

    @Override
    public void run(EventContext context) {

        Exception exception = context.getContextData().getException();

        if (exception == null)
            return;

        if (StringUtils.isNullOrWhiteSpace(context.getEvent().getType()))
            context.getEvent().setType(DataTypes.SIMPLE_ERROR);

        context.getEvent().addData(DataTypes.SIMPLE_ERROR, ExceptionExtensions.toSimpleError(exception));
    }
}
