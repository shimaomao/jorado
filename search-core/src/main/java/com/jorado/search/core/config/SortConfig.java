package com.jorado.search.core.config;

import com.jorado.logger.util.StringUtils;
import com.jorado.zkconfig.ZKPSettings;
import com.jorado.zkconfig.ConfigFactory;
import com.jorado.zkconfig.ZKPConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 排序配置
 */
public class SortConfig extends ZKPConfig<HashMap<String, String>> {

    final static String ZKP_PATH = ZKPSettings.ZOOKEEPER_PATH + "/" + SortConfig.class.getName();

    static SortConfig settings;

    public synchronized static SortConfig getInstance() {
        if (settings == null) {
            settings = ConfigFactory.get(ZKP_PATH);
        }
        return settings;
    }

    @Override
    public void adjust() {
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
