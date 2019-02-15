package com.jorado.zkconfig;

public class AppSettings extends ZKPConfig<String> {

    final static String ZKP_PATH = ZKPSettings.ZOOKEEPER_PATH + "/" + AppSettings.class.getName();

    static AppSettings settings;

    public synchronized static AppSettings getInstance() {
        if (settings == null) {
            settings = ConfigFactory.get(ZKP_PATH);
        }
        return settings;
    }

    public String get(String key, String defaultValue) {
        Object value = get(key);
        return null == value ? defaultValue : value.toString();
    }

    public boolean log() {
        String o = get("log", "0");
        return "1".equals(o);
    }

    public boolean debug() {
        String o = get("debug", "1");
        return o.equals("1");
    }

    @Override
    public void adjust() {

    }
}
