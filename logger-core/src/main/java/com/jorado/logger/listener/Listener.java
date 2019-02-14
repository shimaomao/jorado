package com.jorado.logger.listener;

import com.jorado.logger.EventContext;
import com.jorado.logger.Sortable;

/**
 * 事件监听器
 */
public interface Listener extends Sortable {

    default boolean enabled() {
        return true;
    }

    void run(EventContext context);
}
