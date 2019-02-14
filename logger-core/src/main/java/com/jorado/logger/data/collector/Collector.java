package com.jorado.logger.data.collector;

import com.jorado.logger.data.BaseData;

public interface Collector {
    <T extends BaseData> T getData();
}
