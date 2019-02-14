package com.jorado.logger.data.collector;

import com.jorado.logger.data.RequestInfo;

public class RequestInfoCollector implements Collector {

    @Override
    public RequestInfo getData() {

        return new RequestInfo();
    }
}
