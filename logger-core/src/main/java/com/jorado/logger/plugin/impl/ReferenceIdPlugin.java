package com.jorado.logger.plugin.impl;

import com.jorado.logger.AutoLoad;
import com.jorado.logger.EventContext;
import com.jorado.logger.plugin.Plugin;
import com.jorado.logger.util.StringUtils;

@AutoLoad
public class ReferenceIdPlugin implements Plugin {

    /**
     * 最后加载
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -9999;
    }

    @Override
    public void run(EventContext context) {

        if (!StringUtils.isNullOrWhiteSpace(context.getEvent().getReferenceId()))
            return;

        String referenceId = context.getThreadContext().getOrSetData("ReferenceId", StringUtils.uuid());

        context.getEvent().setReferenceId(referenceId);
    }
}
