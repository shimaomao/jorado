package com.jorado.logger;

public interface Sortable {
    default int getOrder() {
        return 0;
    }
}
