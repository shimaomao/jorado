package com.jorado.admin.util;

import com.jorado.ik.util.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by len.zhang on  2018/9/17.
 */
public class CommonUtils {

    /**
     * 向Map中插入数据，如果Map存在该数据，value值+1，否则，设置为1
     *
     * @param map  Map集合
     * @param word 用于判断Map的key中是否存在
     * @return Map集合
     */
    public static Map<String, Integer> insertMap(Map<String, Integer> map, String word) {
        if (StringUtils.isNullOrWhiteSpace(word)) {
            return map;
        }
        int cnt = 1;
        if (map.containsKey(word)) {
            cnt += map.get(word);
        }
        map.put(word, cnt);
        return map;
    }

    /**
     * 获取map的int型value值，默认为0
     *
     * @param map  map集合
     * @param word key值
     * @return value值
     */
    public static int getMapValue(Map<String, Integer> map, String word) {
        int value = 0;
        if (null == map) {
            return value;
        }

        if (map.containsKey(word)) {
            value = map.get(word);
        }
        return value;
    }

    /**
     * 判断是否为拼音
     *
     * @param word
     * @return
     */
    public static boolean isPinyin(String word) {
        boolean pinyin;
        Pattern pinyinPatt = Pattern.compile("[^\\u4E00-\\u9FA5)]+");
        pinyin = pinyinPatt.matcher(word).matches();
        return pinyin;
    }

}
