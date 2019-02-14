package com.jorado.logger.data.collector;

import com.jorado.logger.data.JvmInfo;

public class JvmInfoCollector implements Collector {

    private static final long SIZE = 1024l * 1024l;

    @Override
    public JvmInfo getData() {

        JvmInfo jvmInfo = new JvmInfo();

        return jvmInfo;
    }

}
