package com.jorado.search.core.config;

import com.jorado.logger.util.JsonUtils;
import com.jorado.zookeeper.LoadConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 黑名单配置
 */
public class BlackConfig extends HashMap<String, List<String>> {

    private static LoadConfig remoteConfig = LoadConfig.newInstance("black", () -> {
        adjust();
        return null;
    });

    private static volatile BlackConfig config;

    public static BlackConfig getInstance() {
        if (null == config) {
            adjust();
        }
        return config;
    }

    /**
     * 获取白名单ip
     *
     * @return
     */
    public List<String> getWhiteIPs() {
        List<String> datas = get("whiteIPs");
        return null == datas ? new ArrayList<>() : datas;
    }

    /**
     * 获取白名单ip范围
     *
     * @return
     */
    public List<String> getWhiteIPScope() {
        List<String> datas = get("whiteIPScope");
        return null == datas ? new ArrayList<>() : datas;
    }

    /**
     * 获取黑名单ip
     *
     * @return
     */
    public List<String> getBlackIPs() {
        List<String> datas = get("blackIPs");
        return null == datas ? new ArrayList<>() : datas;
    }

    /**
     * 获取黑名单ip范围
     *
     * @return
     */
    public List<String> getBlackIPScope() {
        List<String> datas = get("blackIPScope");
        return null == datas ? new ArrayList<>() : datas;
    }

    /**
     * 获取百度爬虫ip范围
     *
     * @return
     */
    public List<String> getBaiduSpiderScope() {
        List<String> datas = get("baiduSpiderScope");
        return null == datas ? new ArrayList<>() : datas;
    }

    /**
     * 获取性别歧视关键字
     *
     * @return
     */
    public List<String> getGenderWords() {
        List<String> datas = get("genderWords");
        return null == datas ? new ArrayList<>() : datas;
    }

    private static void adjust() {
        config = JsonUtils.fromJson(remoteConfig.getBody(), BlackConfig.class);
    }
}
