package com.jorado.ik;

import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

public abstract class ReloadableFilterFactory extends TokenFilterFactory implements Reloadable {

    protected String conf;
    protected String remoteConf;

    protected ReloadableFilterFactory(Map<String, String> args) {
        super(args);
        this.conf = get(args, "conf");
        this.remoteConf = get(args, "remoteConf");
    }

    @Override
    public String getResourceName() {
        return this.conf;
    }

    @Override
    public String getRemoteResourceName() {
        return this.remoteConf;
    }
}
