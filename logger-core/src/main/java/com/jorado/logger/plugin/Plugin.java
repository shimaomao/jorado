package com.jorado.logger.plugin;

import com.jorado.logger.EventContext;
import com.jorado.logger.Sortable;

public interface Plugin extends Sortable {

    default boolean enabled() {
        return true;
    }

    void run(EventContext context);
}
