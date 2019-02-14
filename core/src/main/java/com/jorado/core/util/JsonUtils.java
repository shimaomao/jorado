package com.jorado.core.util;


import com.alibaba.fastjson.JSON;
import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2015/10/2.
 */
public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static <T> T fromJsonFile(String path, Class<T> cls) {

        try {
            String content = IOUtils.read(path);
            return JSON.parseObject(content, cls);
        } catch (Exception ex) {
            logger.error("json file deserialization error", ex);
            return null;
        }
    }

    public static <T> T fromJson(String content, Class<T> cls) {

        try {
            return JSON.parseObject(content, cls);
        } catch (Exception ex) {
            logger.error("json deserialization error", ex);
            return null;
        }
    }

    public static String toJson(Object obj) {

        try {
            return JSON.toJSONString(obj);
        } catch (Exception ex) {
            logger.error("json serialization error", ex);
            return "[]";
        }
    }

    public static <T> T fromGson(String content, Class<T> cls) {

        try {
            Gson gson = new Gson();
            return gson.fromJson(content, cls);
        } catch (Exception ex) {
            logger.error("gson deserialization error", ex);
            return null;
        }
    }

    public static String toGson(Object obj) {

        try {
            Gson gson = new Gson();
            return gson.toJson(obj);
        } catch (Exception ex) {
            logger.error("gson serialization error", ex);
            return "[]";
        }
    }

    public static String toGson(Object obj, Class<?> cls) {

        try {
            Gson gson = new Gson();
            return gson.toJson(obj,cls);
        } catch (Exception ex) {
            logger.error("gson serialization error", ex);
            return "[]";
        }
    }
}
