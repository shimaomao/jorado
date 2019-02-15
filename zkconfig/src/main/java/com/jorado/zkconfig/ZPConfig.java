package com.jorado.zkconfig;

import com.jorado.core.util.JsonUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public abstract class ZPConfig {

    protected float version = 1.001f;
    protected String path = "";

    public void update() {
        ZKPManager watcher = ConfigFactory.getWatcher();
        float v = this.getVersion() + 0.001f;
        String result = String.format("%.3f", v);
        v = Float.parseFloat(result);
        this.setVersion(v);
        String data = JsonUtils.toJson(this);
        watcher.writeData(this.path, data);
    }

    public abstract void adjust();

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void updateConfig(ZPConfig current) {
        ZPConfig config = ConfigFactory.get(this.path);
        if (config != null && config.getVersion() > current.getVersion()) {
            Method method1[] = config.getClass().getMethods();
            Method method2[] = current.getClass().getMethods();
            for (Method set : method2) {
                String setMethodName = set.getName();
                if (setMethodName.startsWith("set")) {
                    String getMethodName = "get" + setMethodName.substring(3);
                    Object value;
                    for (Method get : method1) {
                        if (get.getName().equals(getMethodName)) {
                            try {
                                value = get.invoke(config);
                                set.invoke(current, value);
                                System.out.println(value);
                            } catch (IllegalAccessException |
                                    IllegalArgumentException |
                                    InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
            adjust();
        }

    }

    public static void main(String[] args) {
        ZPConfig config = ConfigFactory.get(AppSettings.ZKP_PATH);
        Class<?> type = config.getClass();
        Method method[] = type.getMethods();
        for (int i = 0; i < method.length; ++i) {
            if (method[i].getName().startsWith("get") || method[i].getName().startsWith("set")) {
                System.out.println(method[i].getName());
            }
        }
    }

}
