package com.jorado.core.util;

/*
 * Created by len.zhang on 2018/4/2.
 * 基础数据转换
 */
public final class ConvertUtils {

    private ConvertUtils() {

    }

    public static int toInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long toLong(String input, long defaultValue) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static short toShort(String input, short defaultValue) {
        try {
            return Short.parseShort(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static float toFloat(String input, float defaultValue) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double toDouble(String input, double defaultValue) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean toBoolean(String input, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(input) || input.equals("1");
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static byte toByte(String input, byte defaultValue) {
        try {
            return Byte.parseByte(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long toLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static short toShort(String input) {
        try {
            return Short.parseShort(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static float toFloat(String input) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return 0F;
        }
    }

    public static double toDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0D;
        }
    }

    public static boolean toBoolean(String input) {
        try {
            return Boolean.parseBoolean(input) || input.equals("1");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static byte toByte(String input) {
        try {
            return Byte.parseByte(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Integer tryToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long tryToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Short tryToShort(String input) {
        try {
            return Short.parseShort(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Float tryToFloat(String input) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double tryToDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Boolean tryToBoolean(String input) {
        try {
            return Boolean.parseBoolean(input) || input.equals("1");
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Byte tryToByte(String input) {
        try {
            return Byte.parseByte(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
