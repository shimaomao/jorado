package com.jorado.core.util;

import java.security.CodeSource;

public final class Version {

    private static final String VERSION = getVersion(Version.class, "1.0.0");

    private Version() {
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getVersion(Class<?> cls) {
        return getVersion(cls, getVersion());
    }

    public static String getVersion(Class<?> cls, String defaultVersion) {
        try {
            // 首先查找MANIFEST.MF规范中的版本号
            String version = cls.getPackage().getImplementationVersion();
            if (StringUtils.isEmpty(version)) {
                version = cls.getPackage().getSpecificationVersion();
            }
            if (StringUtils.isEmpty(version)) {
                // 如果规范中没有版本号，基于jar包名获取版本号
                CodeSource codeSource = cls.getProtectionDomain().getCodeSource();
                if (codeSource != null) {
                    String file = codeSource.getLocation().getFile();
                    if (StringUtils.isNotEmpty(file) && file.endsWith(".jar")) {
                        file = file.substring(0, file.length() - 4);
                        int i = file.lastIndexOf('/');
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        i = file.indexOf("-");
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
                            i = file.indexOf("-");
                            if (i >= 0) {
                                file = file.substring(i + 1);
                            } else {
                                break;
                            }
                        }
                        version = file;
                    }
                }
            }
            // 返回版本号，如果为空返回缺省版本号
            return StringUtils.isEmpty(version) ? defaultVersion : version;
        } catch (Throwable e) { // 防御性容错
            return defaultVersion;
        }
    }

}