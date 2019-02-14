package com.jorado.search.hotwordapi.util;

import com.jorado.logger.util.ConvertUtils;
import com.jorado.logger.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HttpUtils {
    private HttpUtils() {
    }

    static String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
            "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod", "nokia",
            "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma", "docomo",
            "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos", "techfaith",
            "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem", "wellcom", "bunjalloo",
            "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos", "pantech", "gionee", "portalmmm",
            "jig browser", "hiptop", "benq", "haier", "^lct", "320x320", "240x320", "176x220", "w3c ", "acs-", "alav",
            "alca", "amoi", "audi", "avan", "benq", "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
            "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g",
            "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-", "newt", "noki",
            "oper", "palm", "pana", "pant", "phil", "play", "port", "prox", "qwap", "sage", "sams", "sany", "sch-",
            "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar", "sony", "sph-", "symb", "t-mo",
            "teli", "tim-", "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc",
            "winw", "winw", "xda", "xda-", "googlebot-mobile"};

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }

    public static boolean isMultipartRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().indexOf("multipart") != -1;
    }

    /**
     * 是否是手机浏览器
     *
     * @return
     */
    public static boolean isMoblieBrowser(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua == null) {
            return false;
        }
        ua = ua.toLowerCase();
        for (String mobileAgent : mobileAgents) {
            if (ua.indexOf(mobileAgent) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是微信浏览器
     *
     * @return
     */
    public static boolean isWechatBrowser(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua == null) {
            return false;
        }
        ua = ua.toLowerCase();
        return ua.indexOf("micromessenger") > -1;
    }


    /**
     * 是否是PC版的微信浏览器
     *
     * @param request
     * @return
     */
    public static boolean isWechatPcBrowser(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua == null) {
            return false;
        }
        ua = ua.toLowerCase();
        return ua.indexOf("windowswechat") > -1;
    }

    /**
     * 是否是IE浏览器
     *
     * @return
     */
    public static boolean isIEBrowser(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua == null) {
            return false;
        }

        ua = ua.toLowerCase();
        if (ua.indexOf("msie") > -1) {
            return true;
        }

        return ua.indexOf("gecko") > -1 && ua.indexOf("rv:11") > -1;
    }

    public static String getClientIp(HttpServletRequest request) {

        String ip = request.getHeader("X-requested-For");
        if (StringUtils.isNullOrWhiteSpace(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isNullOrWhiteSpace(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isNullOrWhiteSpace(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isNullOrWhiteSpace(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isNullOrWhiteSpace(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isNullOrWhiteSpace(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.contains(",")) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        return ip;
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    public static Map<String, Object> getParamMap(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            result.put(entry.getKey(), StringUtils.joinString(entry.getValue()));
        }
        return result;
    }

    public static String getParamString(HttpServletRequest request) {
        List<String> result = new ArrayList<>();
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            result.add(entry.getKey() + "=" + StringUtils.joinString(entry.getValue()));
        }
        return StringUtils.joinString(result, "&");
    }

    public static <T> T getParamValue(HttpServletRequest request, String key, T defaultValue) {

        try {
            if (StringUtils.isNullOrWhiteSpace(request.getParameter(key))) return defaultValue;

            Class<?> type = defaultValue.getClass();
            if (type.equals(String.class))
                return (T) request.getParameter(key);
            if (type.equals(Integer.class))
                return (T) type.cast(ConvertUtils.toInt(request.getParameter(key)));
            if (type.equals(Long.class))
                return (T) type.cast(ConvertUtils.toLong(request.getParameter(key)));
            if (type.equals(Float.class))
                return (T) type.cast(ConvertUtils.toFloat(request.getParameter(key)));
            if (type.equals(Double.class))
                return (T) type.cast(ConvertUtils.toDouble(request.getParameter(key)));
            if (type.equals(Byte.class))
                return (T) type.cast(ConvertUtils.toByte(request.getParameter(key)));
            if (type.equals(boolean.class))
                return (T) type.cast(ConvertUtils.toBoolean(request.getParameter(key)));

            return defaultValue;

        } catch (Exception ex) {

            return defaultValue;
        }

    }

    /**
     * 获取字符串value
     *
     * @param request
     * @param key
     * @return
     */
    public static String getParamValue(HttpServletRequest request, String key) {
        return getParamValue(request, key, "");
    }

    /**
     * 获取多值字段
     *
     * @param request
     * @param key
     * @return
     */
    public static String[] getParamValues(HttpServletRequest request, String key) {
        String[] values = request.getParameterValues(key);
        if (values != null)
            return values;
        return new String[]{};
    }
}
