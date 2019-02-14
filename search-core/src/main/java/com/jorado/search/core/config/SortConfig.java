package com.jorado.search.core.config;

import com.jorado.logger.util.JsonUtils;
import com.jorado.logger.util.StringUtils;
import com.jorado.zookeeper.LoadConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 排序配置
 */
public class SortConfig extends HashMap<String, HashMap<String, String>> {

    private static LoadConfig remoteConfig = LoadConfig.newInstance("sort", () -> {
        adjust();
        return null;
    });

    private static volatile SortConfig config;

    public static SortConfig getInstance() {
        if (null == config) {
            adjust();
        }
        return config;
    }

    private static void adjust() {
        config = JsonUtils.fromJson(remoteConfig.getBody(), SortConfig.class);
    }

    public Map<String, String> getSortParam(String sortType, String client, String defaultSort) {

        Map<String, String> result = new HashMap<>();

        //如果用户没有传递排序类型，返回默认排序
        if (StringUtils.isNullOrWhiteSpace(sortType)) {
            result.put("sort", defaultSort);
            return result;
        }

        //如果客户端标记不为空时，获取针对客户端的排序
        if (!StringUtils.isNullOrWhiteSpace(client)) {
            String st = client + "-" + sortType;
            HashMap<String, String> resultObj = SortConfig.getInstance().get(st.toLowerCase());
            if (null != resultObj) {
                result.putAll(resultObj);
                return result;
            }
        }

        //如果没有找到针对特定客户端的排序，直接根据sortType设置排序
        HashMap<String, String> resultObj = SortConfig.getInstance().get(sortType.toLowerCase());

        if (null != resultObj) {
            result.putAll(resultObj);
            return result;
        }

        result.put("sort", sortType);

        return result;
    }
}
