package com.jorado.search.core.config;

import com.jorado.zkconfig.ZKPSettings;
import com.jorado.zkconfig.ConfigFactory;
import com.jorado.zkconfig.ZKPConfig;

import java.util.List;

/**
 * 黑名单配置
 */
public class BlackConfig extends ZKPConfig {

    final static String ZKP_PATH = ZKPSettings.ZOOKEEPER_PATH + "/" + BlackConfig.class.getName();

    static BlackConfig settings;

    public synchronized static BlackConfig getInstance() {
        if (settings == null) {
            settings = ConfigFactory.get(ZKP_PATH);
        }
        return settings;
    }

    @Override
    public void adjust() {

    }

    private List<String> whiteIPs;
    private List<String> whiteIPScope;
    private List<String> blackIPs;
    private List<String> blackIPScope;
    private List<String> spiderIPs;
    private List<String> spiderScope;
    private List<String> genderWords;

    public List<String> getWhiteIPs() {
        return whiteIPs;
    }

    public void setWhiteIPs(List<String> whiteIPs) {
        this.whiteIPs = whiteIPs;
    }

    public List<String> getWhiteIPScope() {
        return whiteIPScope;
    }

    public void setWhiteIPScope(List<String> whiteIPScope) {
        this.whiteIPScope = whiteIPScope;
    }

    public List<String> getBlackIPs() {
        return blackIPs;
    }

    public void setBlackIPs(List<String> blackIPs) {
        this.blackIPs = blackIPs;
    }

    public List<String> getBlackIPScope() {
        return blackIPScope;
    }

    public void setBlackIPScope(List<String> blackIPScope) {
        this.blackIPScope = blackIPScope;
    }

    public List<String> getSpiderIPs() {
        return spiderIPs;
    }

    public void setSpiderIPs(List<String> spiderIPs) {
        this.spiderIPs = spiderIPs;
    }

    public List<String> getSpiderScope() {
        return spiderScope;
    }

    public void setSpiderScope(List<String> spiderScope) {
        this.spiderScope = spiderScope;
    }

    public List<String> getGenderWords() {
        return genderWords;
    }

    public void setGenderWords(List<String> genderWords) {
        this.genderWords = genderWords;
    }
}
