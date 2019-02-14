package com.jorado.zkconfig;

import java.util.HashMap;
import java.util.Map;

public class AppSettings extends ZPConfig {

    final static String ZKP_PATH = AppProperties.ZOOKEEPER_PATH + "/" + AppSettings.class.getName();

    static AppSettings settings;

    public synchronized static AppSettings Instance() {
        if (settings == null) {
            settings = (AppSettings) ConfigFactory.get(ZKP_PATH);
        }
        return settings;
    }

    Map<String, String> map = new HashMap<String, String>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public AppSettings() {
        path = ZKP_PATH;
    }

    public String get(String key) {

        return map.get(key);
    }

    @Override
    public String toString() {
        return "appSettings v=" + this.getVersion() + "; path=" + this.getPath() + "; " + map.toString();
    }

    @Override
    public void adjust() {

    }

    public static void main(String[] args) {

//		Gson gson = new Gson();
//		AppSettings settings=new AppSettings();
//		settings.map.put("wzg", "123");
//		settings.map.put("zjl", "456");
//		//settings.path="/configs/applycv/AppSettings";
//		settings.version=1.001f;
//		String content = gson.toJson(settings,AppSettings.class);
//		System.out.println(content);

//		AppSettings config = AppSettings.Instance();
//		config.update();
//		System.out.println(config);
//		config.update();
//		System.out.println(config);
    }

}
