package com.jorado.ik;

import com.jorado.ik.util.StringUtils;
import org.apache.lucene.analysis.util.TokenizerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ReloadableTokenizerFactory extends TokenizerFactory implements Reloadable {

    protected String conf;
    protected String remoteConf;

    protected ReloadableTokenizerFactory(Map<String, String> args) {
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

    public static List<String> SplitFileNames(String fileNames) {
        List result = new ArrayList();
        if (StringUtils.isNotNullOrWhiteSpace(fileNames)) {
            for (String file : fileNames.split("[,\\s]+")) {
                result.add(file);
            }
        }
        return result;
    }
}
