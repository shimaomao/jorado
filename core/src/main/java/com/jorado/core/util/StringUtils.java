package com.jorado.core.util;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

public final class StringUtils {

    private StringUtils() {
    }

    /**
     * The empty String {@code ""}.
     */
    public static final String EMPTY = "";

    /**
     * The context path separator String {@code "/"}.
     */
    public static final String CONTEXT_SEP = "/";

    /**
     * The string {@code "*"}.
     */
    public static final String ALL = "*";

    /**
     * The string {@code "default"}.
     */
    public static final String DEFAULT = "default";

    /**
     * The string {@code "true"}.
     */
    public static final String TRUE = "true";

    /**
     * The string {@code "false"}.
     */
    public static final String FALSE = "false";

    /**
     * The string {@code "null"}.
     */
    public static final String NULL = "null";

    /**
     * 空数组
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static final int INDEX_NOT_FOUND = -1;

    private static final int PAD_LIMIT = 8192;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[+\\-]?[0-9]+(\\.[.0-9]+)?[BSILFDbsilfd]?$");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("[^(_a-zA-Z0-9)]");
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("[_a-zA-Z][\\._a-zA-Z0-9]+");
    private static final Pattern NAMED_PATTERN = Pattern.compile("^[_A-Za-z][_0-9A-Za-z]*$");
    private static final Pattern TYPED_PATTERN = Pattern.compile("^[_A-Za-z][_.0-9A-Za-z]*$");
    private static final Pattern FUNCTION_PATTERN = Pattern.compile("^\\.[_A-Za-z][_0-9A-Za-z]*$");
    private static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*\\,\\s*");
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[^\\u4e00-\\u9fa5]");
    private static final Pattern SCRIPT_PATTERN = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern STYLE_PATTERN = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>", Pattern.CASE_INSENSITIVE);
    private static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
    private static final Pattern SPECIALCHAR_PATTERN = Pattern.compile("[_\\-\"+－`~!@$%^&*()=|{}':;',\\[\\]<>/?~！@￥%……&*（）——|{}【】‘；：”“’。，、？（）★『』！○、]");
    private static final Pattern KVP_PATTERN = Pattern.compile("([_.a-zA-Z0-9][-_.a-zA-Z0-9]*)[=](.*)"); //key value pair pattern.

    public static String getVaildName(String name) {
        return SYMBOL_PATTERN.matcher(name).replaceAll("_");
    }

    public static boolean isClassName(String value) {
        return !isEmpty(value) && CLASS_NAME_PATTERN.matcher(value).matches();
    }

    public static boolean isNumber(String value) {
        return !isEmpty(value) && NUMBER_PATTERN.matcher(value).matches();
    }

    public static boolean isNumber(char[] value) {
        if (value == null || value.length == 0) {
            return false;
        }
        for (char ch : value) {
            if (ch != '.' && (ch <= '0' || ch >= '9')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumber(byte[] value) {
        if (value == null || value.length == 0) {
            return false;
        }
        for (byte ch : value) {
            if (ch != '.' && (ch <= '0' || ch >= '9')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEquals(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    public static boolean isNamed(String value) {
        return NAMED_PATTERN.matcher(value).matches();
    }

    public static boolean isTyped(String value) {
        return TYPED_PATTERN.matcher(value).matches();
    }

    public static boolean isFunction(String value) {
        return FUNCTION_PATTERN.matcher(value).matches();
    }

    public static boolean isNoneEmpty(final String... ss) {
        if (ArrayUtils.isEmpty(ss)) {
            return false;
        }
        for (final String s : ss) {
            if (isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnyEmpty(final String... ss) {
        return !isNoneEmpty(ss);
    }

    public static boolean isEmpty(byte[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isNotEmpty(byte[] value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(char[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isNotEmpty(char[] value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isBlank(String value) {
        if (StringUtils.isNotEmpty(value)) {
            int len = value.length();
            for (int i = 0; i < len; i++) {
                char ch = value.charAt(i);
                switch (ch) {
                    case ' ':
                    case '\t':
                    case '\n':
                    case '\r':
                    case '\b':
                    case '\f':
                        break;
                    default:
                        return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static String valueOf(Object value) {
        if (value == null) {
            return "";
        }
        return toString(value);
    }

    public static String toString(Object value) {
        if (value == null)
            return null;
        if (value.getClass().isArray()) {
            if (value instanceof boolean[]) {
                return Arrays.toString((boolean[]) value);
            } else if (value instanceof byte[]) {
                return Arrays.toString((byte[]) value);
            } else if (value instanceof short[]) {
                return Arrays.toString((short[]) value);
            } else if (value instanceof int[]) {
                return Arrays.toString((int[]) value);
            } else if (value instanceof long[]) {
                return Arrays.toString((long[]) value);
            } else if (value instanceof float[]) {
                return Arrays.toString((float[]) value);
            } else if (value instanceof double[]) {
                return Arrays.toString((double[]) value);
            } else if (value instanceof char[]) {
                return String.valueOf((char[]) value);
            } else if (value instanceof Object[]) {
                return Arrays.toString((Object[]) value);
            }
        }
        return String.valueOf(value);
    }

    public static String toByteString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 5);
        for (byte b : bytes) {
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append((int) b);
        }
        return buf.toString();
    }

    public static String toCharString(char[] chars) {
        StringBuilder buf = new StringBuilder(chars.length * 4);
        for (char c : chars) {
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append('\'');
            switch (c) {
                case '\\':
                    buf.append("\\\\");
                    break;
                case '\'':
                    buf.append("\\'");
                    break;
                case '\t':
                    buf.append("\\t");
                    break;
                case '\n':
                    buf.append("\\n");
                    break;
                case '\r':
                    buf.append("\\r");
                    break;
                case '\f':
                    buf.append("\\f");
                    break;
                case '\b':
                    buf.append("\b");
                    break;
                default:
                    buf.append(c);
                    break;
            }
            buf.append('\'');
        }
        return buf.toString();
    }

    public static String escapeString(String src) {
        if (StringUtils.isEmpty(src)) {
            return src;
        }
        int len = src.length();
        StringBuilder buf = null;
        for (int i = 0; i < len; i++) {
            char ch = src.charAt(i);
            String rep;
            switch (ch) {
                case '\\':
                    rep = "\\\\";
                    break;
                case '\"':
                    rep = "\\\"";
                    break;
                case '\'':
                    rep = "\\\'";
                    break;
                case '\t':
                    rep = "\\t";
                    break;
                case '\n':
                    rep = "\\n";
                    break;
                case '\r':
                    rep = "\\r";
                    break;
                case '\b':
                    rep = "\\b";
                    break;
                case '\f':
                    rep = "\\f";
                    break;
                default:
                    rep = null;
                    break;
            }
            if (rep != null) {
                if (buf == null) {
                    buf = new StringBuilder(len * 2);
                    if (i > 0) {
                        buf.append(src, 0, i);
                    }
                }
                buf.append(rep);
            } else {
                if (buf != null) {
                    buf.append(ch);
                }
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return src;
    }

    public static char[] escapeString(char[] src) {
        if (src == null || src.length == 0) {
            return src;
        }
        int len = src.length;
        int off = 0;
        char[] buf = null;
        for (int i = 0; i < len; i++) {
            char ch = src[i];
            char rep;
            switch (ch) {
                case '\\':
                    rep = '\\';
                    break;
                case '\"':
                    rep = '\"';
                    break;
                case '\'':
                    rep = '\'';
                    break;
                case '\t':
                    rep = 't';
                    break;
                case '\n':
                    rep = 'n';
                    break;
                case '\r':
                    rep = 'r';
                    break;
                case '\b':
                    rep = 'b';
                    break;
                case '\f':
                    rep = 'f';
                    break;
                default:
                    rep = 0;
                    break;
            }
            if (rep != 0) {
                if (buf == null) {
                    buf = expand(src, off = i, 2);
                }
                buf[off++] = '\\';
                buf[off++] = rep;
            } else {
                if (buf != null) {
                    buf[off++] = ch;
                }
            }
        }
        if (buf != null) {
            if (buf.length > off) {
                char[] newBuf = new char[off];
                System.arraycopy(buf, 0, newBuf, 0, off);
                return newBuf;
            }
            return buf;
        }
        return src;
    }

    public static byte[] escapeString(byte[] src) {
        if (src == null || src.length == 0) {
            return src;
        }
        int len = src.length;
        int off = 0;
        byte[] buf = null;
        byte pre = 0;
        for (int i = 0; i < len; i++) {
            byte ch = src[i];
            byte rep;
            switch (ch) {
                case 92:
                    rep = '\\';
                    break;
                case 34:
                    rep = '\"';
                    break;
                case 39:
                    rep = '\'';
                    break;
                case 9:
                    rep = 't';
                    break;
                case 10:
                    rep = 'n';
                    break;
                case 13:
                    rep = 'r';
                    break;
                case 8:
                    rep = 'b';
                    break;
                case 12:
                    rep = 'f';
                    break;
                default:
                    rep = 0;
                    break;
            }
            if (rep != 0 && pre >= 0) {
                if (buf == null) {
                    buf = expand(src, off = i, 2);
                }
                buf[off++] = '\\';
                buf[off++] = rep;
            } else {
                if (buf != null) {
                    buf[off++] = ch;
                }
            }
            pre = ch;
        }
        if (buf != null) {
            if (buf.length > off) {
                byte[] newBuf = new byte[off];
                System.arraycopy(buf, 0, newBuf, 0, off);
                return newBuf;
            }
            return buf;
        }
        return src;
    }

    public static String unescapeString(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        StringBuilder buf = null;
        int len = value.length();
        int len1 = len - 1;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (ch == '\\' && i < len1) {
                int j = i;
                i++;
                ch = value.charAt(i);
                switch (ch) {
                    case '\\':
                        ch = '\\';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    default:
                        j--;
                }
                if (buf == null) {
                    buf = new StringBuilder(len);
                    if (j > 0) {
                        buf.append(value, 0, j);
                    }
                }
                buf.append(ch);
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    public static String escapeXml(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        int len = value.length();
        StringBuilder buf = null;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '<':
                    if (buf == null) {
                        buf = new StringBuilder(len * 2);
                        if (i > 0) {
                            buf.append(value, 0, i);
                        }
                    }
                    buf.append("&lt;");
                    break;
                case '>':
                    if (buf == null) {
                        buf = new StringBuilder(len * 2);
                        if (i > 0) {
                            buf.append(value, 0, i);
                        }
                    }
                    buf.append("&gt;");
                    break;
                case '\"':
                    if (buf == null) {
                        buf = new StringBuilder(len * 2);
                        if (i > 0) {
                            buf.append(value, 0, i);
                        }
                    }
                    buf.append("&quot;");
                    break;
                case '\'':
                    if (buf == null) {
                        buf = new StringBuilder(len * 2);
                        if (i > 0) {
                            buf.append(value, 0, i);
                        }
                    }
                    buf.append("&apos;");
                    break;
                case '&':
                    if (buf == null) {
                        buf = new StringBuilder(len * 2);
                        if (i > 0) {
                            buf.append(value, 0, i);
                        }
                    }
                    buf.append("&amp;");
                    break;
                default:
                    if (buf != null) {
                        buf.append(ch);
                    }
                    break;
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    public static char[] escapeXml(char[] src) {
        if (src == null || src.length == 0) {
            return src;
        }
        int len = src.length;
        int off = 0;
        char[] buf = null;
        for (int i = 0; i < len; i++) {
            char ch = src[i];
            switch (ch) {
                case '<':
                    if (buf == null) {
                        buf = expand(src, off = i, 4);
                    } else if (buf.length < off + 4) {
                        buf = expand(buf, off, 4);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'l';
                    buf[off++] = 't';
                    buf[off++] = ';';
                    break;
                case '>':
                    if (buf == null) {
                        buf = expand(src, off = i, 4);
                    } else if (buf.length < off + 4) {
                        buf = expand(buf, off, 4);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'g';
                    buf[off++] = 't';
                    buf[off++] = ';';
                    break;
                case '\"':
                    if (buf == null) {
                        buf = expand(src, off = i, 6);
                    } else if (buf.length < off + 6) {
                        buf = expand(buf, off, 6);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'q';
                    buf[off++] = 'u';
                    buf[off++] = 'o';
                    buf[off++] = 't';
                    buf[off++] = ';';
                    break;
                case '\'':
                    if (buf == null) {
                        buf = expand(src, off = i, 6);
                    } else if (buf.length < off + 6) {
                        buf = expand(buf, off, 6);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'a';
                    buf[off++] = 'p';
                    buf[off++] = 'o';
                    buf[off++] = 's';
                    buf[off++] = ';';
                    break;
                case '&':
                    if (buf == null) {
                        buf = expand(src, off = i, 5);
                    } else if (buf.length < off + 5) {
                        buf = expand(buf, off, 5);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'a';
                    buf[off++] = 'm';
                    buf[off++] = 'p';
                    buf[off++] = ';';
                    break;
                default:
                    if (buf != null) {
                        if (buf.length < off + 1) {
                            buf = expand(buf, off, 1);
                        }
                        buf[off++] = ch;
                    }
                    break;
            }
        }
        if (buf != null) {
            if (buf.length > off) {
                char[] newBuf = new char[off];
                System.arraycopy(buf, 0, newBuf, 0, off);
                return newBuf;
            }
            return buf;
        }
        return src;
    }

    public static byte[] escapeXml(byte[] src) {
        if (src == null || src.length == 0) {
            return src;
        }
        int len = src.length;
        int off = 0;
        byte[] buf = null;
        for (int i = 0; i < len; i++) {
            byte ch = src[i];
            switch (ch) {
                case 60:
                    if (buf == null) {
                        buf = expand(src, off = i, 4);
                    } else if (buf.length < off + 4) {
                        buf = expand(buf, off, 4);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'l';
                    buf[off++] = 't';
                    buf[off++] = ';';
                    break;
                case 62:
                    if (buf == null) {
                        buf = expand(src, off = i, 4);
                    } else if (buf.length < off + 4) {
                        buf = expand(buf, off, 4);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'g';
                    buf[off++] = 't';
                    buf[off++] = ';';
                    break;
                case 34:
                    if (buf == null) {
                        buf = expand(src, off = i, 6);
                    } else if (buf.length < off + 6) {
                        buf = expand(buf, off, 6);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'q';
                    buf[off++] = 'u';
                    buf[off++] = 'o';
                    buf[off++] = 't';
                    buf[off++] = ';';
                    break;
                case 39:
                    if (buf == null) {
                        buf = expand(src, off = i, 6);
                    } else if (buf.length < off + 6) {
                        buf = expand(buf, off, 6);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'a';
                    buf[off++] = 'p';
                    buf[off++] = 'o';
                    buf[off++] = 's';
                    buf[off++] = ';';
                    break;
                case 38:
                    if (buf == null) {
                        buf = expand(src, off = i, 5);
                    } else if (buf.length < off + 5) {
                        buf = expand(buf, off, 5);
                    }
                    buf[off++] = '&';
                    buf[off++] = 'a';
                    buf[off++] = 'm';
                    buf[off++] = 'p';
                    buf[off++] = ';';
                    break;
                default:
                    if (buf != null) {
                        if (buf.length < off + 1) {
                            buf = expand(buf, off, 1);
                        }
                        buf[off++] = ch;
                    }
                    break;
            }
        }
        if (buf != null) {
            if (buf.length > off) {
                byte[] newBuf = new byte[off];
                System.arraycopy(buf, 0, newBuf, 0, off);
                return newBuf;
            }
            return buf;
        }
        return src;
    }

    private static char[] expand(char[] src, int off, int inc) {
        int len = Math.max(src.length * 2, off + inc);
        char[] dest = new char[len];
        if (off > 0) {
            System.arraycopy(src, 0, dest, 0, off);
        }
        return dest;
    }

    private static byte[] expand(byte[] src, int off, int inc) {
        int len = Math.max(src.length * 2, off + inc);
        byte[] dest = new byte[len];
        if (off > 0) {
            System.arraycopy(src, 0, dest, 0, off);
        }
        return dest;
    }

    public static String unescapeXml(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        StringBuilder buf = null;
        int len = value.length();
        int len3 = len - 3;
        int len4 = len - 4;
        int len5 = len - 5;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (ch == '&' && i < len3) {
                int j = i;
                char ch1 = value.charAt(i + 1);
                switch (ch1) {
                    case 'l':
                        if (value.charAt(i + 2) == 't'
                                && value.charAt(i + 3) == ';') {
                            i += 3;
                            if (buf == null) {
                                buf = new StringBuilder(len3);
                                if (j > 0) {
                                    buf.append(value, 0, j);
                                }
                            }
                            buf.append('<');
                        } else if (buf != null) {
                            buf.append('&');
                        }
                        break;
                    case 'g':
                        if (value.charAt(i + 2) == 't'
                                && value.charAt(i + 3) == ';') {
                            i += 3;
                            if (buf == null) {
                                buf = new StringBuilder(len3);
                                if (j > 0) {
                                    buf.append(value, 0, j);
                                }
                            }
                            buf.append('>');
                        } else if (buf != null) {
                            buf.append('&');
                        }
                        break;
                    case 'a':
                        if (i < len4 && value.charAt(i + 2) == 'm'
                                && value.charAt(i + 3) == 'p'
                                && value.charAt(i + 4) == ';') {
                            i += 4;
                            if (buf == null) {
                                buf = new StringBuilder(len4);
                                if (j > 0) {
                                    buf.append(value, 0, j);
                                }
                            }
                            buf.append('&');
                        } else if (i < len5 && value.charAt(i + 2) == 'p'
                                && value.charAt(i + 3) == 'o'
                                && value.charAt(i + 4) == 's'
                                && value.charAt(i + 5) == ';') {
                            i += 5;
                            if (buf == null) {
                                buf = new StringBuilder(len5);
                                if (j > 0) {
                                    buf.append(value, 0, j);
                                }
                            }
                            buf.append('\'');
                        } else if (buf != null) {
                            buf.append('&');
                        }
                        break;
                    case 'q':
                        if (i < len5 && value.charAt(i + 2) == 'u'
                                && value.charAt(i + 3) == 'o'
                                && value.charAt(i + 4) == 't'
                                && value.charAt(i + 5) == ';') {
                            i += 5;
                            if (buf == null) {
                                buf = new StringBuilder(len5);
                                if (j > 0) {
                                    buf.append(value, 0, j);
                                }
                            }
                            buf.append('\"');
                        } else if (buf != null) {
                            buf.append('&');
                        }
                        break;
                    default:
                        if (buf != null) {
                            buf.append('&');
                        }
                        break;
                }
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    public static String clearBlank(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        int len = value.length();
        StringBuilder buf = null;
        boolean blank = false;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                case '\b':
                case '\f':
                    if (!blank) {
                        blank = true;
                        if (buf == null) {
                            buf = new StringBuilder(len);
                            if (i > 0) {
                                buf.append(value, 0, i);
                            }
                        }
                    }
                    break;
                default:
                    if (blank) {
                        blank = false;
                    }
                    if (buf != null) {
                        buf.append(ch);
                    }
                    break;
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    public static String compressBlank(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        int len = value.length();
        StringBuilder buf = null;
        boolean blank = false;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                case '\b':
                case '\f':
                    if (!blank) {
                        blank = true;
                        if (buf == null) {
                            buf = new StringBuilder(len);
                            if (i > 0) {
                                buf.append(value, 0, i);
                            }
                        }
                        buf.append(' ');
                    }
                    break;
                default:
                    if (blank) {
                        blank = false;
                    }
                    if (buf != null) {
                        buf.append(ch);
                    }
                    break;
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    public static String trimBlankLine(String value, boolean left, boolean right) {
        if (value == null || value.isEmpty() || !(left || right)) {
            return value;
        }
        int len = value.length();
        int start = 0;
        if (left) {
            loop_left:
            for (int i = 0; i < len; i++) {
                char ch = value.charAt(i);
                switch (ch) {
                    case ' ':
                    case '\t':
                    case '\b':
                    case '\f':
                        break;
                    case '\n':
                        start = i + 1;
                        break loop_left;
                    case '\r':
                        int next = i + 1;
                        if (next < len && value.charAt(next) == '\n') {
                            start = i + 2;
                        } else {
                            start = i + 1;
                        }
                        break loop_left;
                    default:
                        break loop_left;
                }
            }
        }
        int end = len;
        if (right) {
            int len1 = len - 1;
            int start1 = start > 0 ? start - 1 : 0;
            loop_right:
            for (int i = len1; i >= start1; i--) {
                char ch = value.charAt(i);
                switch (ch) {
                    case ' ':
                    case '\t':
                    case '\b':
                    case '\f':
                        break;
                    case '\n':
                        end = i + 1;
                        break loop_right;
                    case '\r':
                        end = i + 1;
                        break loop_right;
                    default:
                        break loop_right;
                }
            }
        }
        if (start == end) {
            return "";
        }
        if (start > 0 || end < len) {
            return value.substring(start, end);
        }
        return value;
    }

    public static String trimBlankLine(String value) {
        return trimBlankLine(value, true, true);
    }

    public static String trimLeftBlankLine(String value) {
        return trimBlankLine(value, true, false);
    }

    public static String trimRightBlankLine(String value) {
        return trimBlankLine(value, false, true);
    }

    public static String clearBlankLine(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        int len = value.length();
        int len1 = len - 1;
        StringBuilder buf = null;
        int pre = 0;
        boolean blank = true;
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (buf != null) {
                buf.append(ch);
            }
            switch (ch) {
                case ' ':
                case '\t':
                case '\r':
                case '\b':
                case '\f':
                    if (i < len1) {
                        break;
                    }
                case '\n':
                    if (blank) {
                        if (buf == null) {
                            buf = new StringBuilder(len);
                            if (pre > 0) {
                                buf.append(value, 0, pre + 1);
                            }
                        } else {
                            buf.setLength(buf.length() - i + pre);
                        }
                    } else {
                        blank = true;
                    }
                    pre = i;
                    break;
                default:
                    if (blank) {
                        blank = false;
                    }
                    break;
            }
        }
        if (buf != null) {
            return buf.toString();
        }
        return value;
    }

    public static String getConditionCode(Class<?> type, String code, String[] sizers) {
        if (type != boolean.class) {
            if (type == byte.class
                    || type == short.class
                    || type == int.class
                    || type == long.class
                    || type == float.class
                    || type == double.class) {
                code = "(" + code + ") != 0";
            } else if (type == char.class) {
                code = "(" + code + ") != \'\\0\'";
            } else if (type == String.class) {
                code = "(" + code + ")  != null && (" + code + ").length() > 0";
            } else if (type == Boolean.class) {
                code = "(" + code + ")  != null && (" + code + ").booleanValue()";
            } else if (type.isArray()) {
                code = "(" + code + ") != null && (" + code + ").length > 0";
            } else if (Collection.class.isAssignableFrom(type)) {
                code = "(" + code + ") != null && (" + code + ").size() > 0";
            } else if (Map.class.isAssignableFrom(type)) {
                code = "(" + code + ") != null && (" + code + ").size() > 0";
            } else {
                String method = ClassUtils.getSizeMethod(type, sizers);
                if (StringUtils.isNotEmpty(method)) {
                    code = "(" + code + ") != null && (" + code + ")." + method + " > 0";
                } else {
                    code = ClassUtils.class.getCanonicalName() + ".isTrue(" + code + ")";
                }
            }
        }
        return code;
    }

    public static String getLocationMessage(String name, Reader reader, int offset) {
        String location = "";
        if (offset <= 0) {
            return location;
        }
        try {
            int line = 1;
            int column = 0;
            int count = 0;
            int len = 0;
            char[] buf = new char[128];
            StringBuilder cur = new StringBuilder();
            while ((len = reader.read(buf)) > 0) {
                for (int i = 0; i < len; i++) {
                    char ch = buf[i];
                    if (ch == '\n') {
                        line++;
                        column = 0;
                        cur.setLength(0);
                    } else {
                        column++;
                        cur.append(ch);
                    }
                    if (count >= offset) {
                        int padding = 20;
                        String before;
                        if (cur.length() <= padding) {
                            before = cur.toString();
                        } else {
                            before = cur.substring(cur.length() - padding);
                        }
                        int c = i + 1;
                        int remain = len - c;
                        StringBuilder after = new StringBuilder();
                        boolean breaked = false;
                        if (remain > 0) {
                            for (int j = c; j < padding + c && j < buf.length; j++) {
                                if (buf[j] == '\r' || buf[j] == '\n') {
                                    breaked = true;
                                    break;
                                }
                                after.append(buf[j]);
                            }
                        }
                        if (!breaked && remain < padding) {
                            char[] b = new char[padding - remain];
                            int l = reader.read(b);
                            if (l > 0) {
                                for (int j = 0; j < l; j++) {
                                    if (b[j] == '\r' || b[j] == '\n') {
                                        break;
                                    }
                                    after.append(b[j]);
                                }
                            }
                        }
                        StringBuilder msg = new StringBuilder();
                        msg.append("line: " + line + ", column: " + column + ", char: " + ch + ", in: \n" + name + "\n");
                        for (int j = 0; j < padding * 2; j++) {
                            msg.append("=");
                        }
                        msg.append("\n");
                        msg.append("...");
                        msg.append(before);
                        msg.append(after);
                        msg.append("...");
                        msg.append("\n");
                        for (int j = 0; j < before.length() + 2; j++) {
                            msg.append(" ");
                        }
                        msg.append("^-here\n");
                        for (int j = 0; j < padding * 2; j++) {
                            msg.append("=");
                        }
                        msg.append("\n");
                        return msg.toString();
                    }
                    count++;
                }
            }
        } catch (Throwable t) {
        }
        return location;
    }

    public static String removeCommaValue(String values, String value) {
        return StringUtils.joinByComma(CollectionUtils.remove(StringUtils.splitByComma(values), value));
    }

    public static String joinByComma(String[] values) {
        return joinBy(values, ",");
    }

    public static String joinBy(String[] values, String sep) {
        StringBuilder buf = new StringBuilder();
        for (String value : values) {
            if (buf.length() > 0) {
                buf.append(sep);
            }
            buf.append(value);
        }
        return buf.toString();
    }

    public static String[] splitByComma(String name) {
        return name == null ? new String[0] : COMMA_SPLIT_PATTERN.split(name);
    }

    public static String splitCamelName(String name, String split) {
        return splitCamelName(name, split, false);
    }

    public static String splitCamelName(String name, String split, boolean upper) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        StringBuilder buf = new StringBuilder(name.length() * 2);
        buf.append(upper ? Character.toUpperCase(name.charAt(0)) : Character.toLowerCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                buf.append(split);
                buf.append(upper ? c : Character.toLowerCase(c));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    public static byte[] toBytes(String src, String encoding) {
        try {
            return src.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return src.getBytes();
        }
    }

    public static boolean endsWith(String value, String[] suffixes) {
        if (value != null && suffixes != null) {
            for (String suffix : suffixes) {
                if (value.endsWith(suffix)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean inArray(String value, String[] values) {
        if (value != null && values != null) {
            value = value.trim();
            for (String v : values) {
                if (value.equals(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String concat(Object v1, Object v2) {
        if (v1 == null && v2 == null) {
            return null;
        }
        if (v1 == null) {
            return String.valueOf(v2);
        }
        if (v2 == null) {
            return String.valueOf(v1);
        }
        return String.valueOf(v1) + String.valueOf(v2);
    }

    public static String escapeSolrString(String value) {
        if (value != null) {
            StringBuilder buf = null;
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                String str = null;
                switch (ch) {
                    case '(':
                        str = "\\(";
                        break;
                    case ')':
                        str = "\\)";
                        break;
                    case '/':
                        str = "\\/";
                        break;
                    case '<':
                        str = "\\<";
                        break;
                    case '>':
                        str = "\\>";
                        break;
                    case '{':
                        str = "\\{";
                        break;
                    case '}':
                        str = "\\}";
                        break;
                    case '+':
                        str = "\\+";
                        break;
                    case '-':
                        str = "\\-";
                        break;
                    case '^':
                        str = "\\^";
                        break;
                    case '\\':
                        str = "\\\\";
                        break;
//                    case '*':
//                        //如果以*结尾，表示前缀查询，否则表示特殊字符
//                        if (i != value.length() - 1) {
//                            str = "\\*";
//                        }
//                        break;
                    case '~':
                        str = "\\~";
                        break;
                    case '&':
                        str = "\\&";
                        break;
                    case '!':
                        str = "\\!";
                        break;
                    case ':':
                        str = "\\:";
                        break;
                    case '|':
                        str = "\\|";
                        break;
                    case '?':
                        str = "\\?";
                        break;
                    case '[':
                        str = "\\[";
                        break;
                    case ']':
                        str = "\\]";
                        break;
                    case '\"':
                        str = " ";
                        break;
                    case '\'':
                        str = " ";
                        break;
                    default:
                        break;
                }
                if (str != null) {
                    if (buf == null) {
                        buf = new StringBuilder();
                        if (i > 0) {
                            buf.append(value, 0, i);
                        }
                    }
                    buf.append(str);
                } else {
                    if (buf != null) {
                        buf.append(ch);
                    }
                }
            }
            if (buf != null) {
                return buf.toString();
            }
        }
        return value;
    }

    public static String removeSpecialChar(String value) {
        return SPECIALCHAR_PATTERN.matcher(value).replaceAll("");
    }

    public static String escapeHtml(String value) {
        if (value != null) {
            StringBuilder buf = null;
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                String str;
                switch (ch) {
                    case '&':
                        str = "&amp;";
                        break;
                    case '<':
                        str = "&lt;";
                        break;
                    case '>':
                        str = "&gt;";
                        break;
                    case '\"':
                        str = "&quot;";
                        break;
                    case '\'':
                        str = "&apos;";
                        break;
                    default:
                        str = null;
                        break;
                }
                if (str != null) {
                    if (buf == null) {
                        buf = new StringBuilder();
                        if (i > 0) {
                            buf.append(value, 0, i);
                        }
                    }
                    buf.append(str);
                } else {
                    if (buf != null) {
                        buf.append(ch);
                    }
                }
            }
            if (buf != null) {
                return buf.toString();
            }
        }
        return value;
    }

    public static String unescapeHtml(String text) { // TODO 性能
        return text.replace("&lt;", "<").replace("&gt;", ">")
                .replace("&quot;", "\"").replace("&apos;", "\'")
                .replace("&amp;", "&");
    }

    public static String getLocationMessage(Reader reader, int offset) {
        String location = "";
        if (offset <= 0) {
            return location;
        }
        try {
            int line = 1;
            int column = 0;
            int count = 0;
            int len = 0;
            char[] buf = new char[128];
            StringBuilder cur = new StringBuilder();
            while ((len = reader.read(buf)) > 0) {
                for (int i = 0; i < len; i++) {
                    char ch = buf[i];
                    if (ch == '\n') {
                        line++;
                        column = 0;
                        cur.setLength(0);
                    } else {
                        column++;
                        cur.append(ch);
                    }
                    if (count >= offset) {
                        int padding = 20;
                        String before;
                        if (cur.length() <= padding) {
                            before = cur.toString();
                        } else {
                            before = cur.substring(cur.length() - padding);
                        }
                        int c = i + 1;
                        int remain = len - c;
                        StringBuilder after = new StringBuilder();
                        boolean breaked = false;
                        if (remain > 0) {
                            for (int j = c; j < padding + c && j < buf.length; j++) {
                                if (buf[j] == '\r' || buf[j] == '\n') {
                                    breaked = true;
                                    break;
                                }
                                after.append(buf[j]);
                            }
                        }
                        if (!breaked && remain < padding) {
                            char[] b = new char[padding - remain];
                            int l = reader.read(b);
                            if (l > 0) {
                                for (int j = 0; j < l; j++) {
                                    if (b[j] == '\r' || b[j] == '\n') {
                                        break;
                                    }
                                    after.append(b[j]);
                                }
                            }
                        }
                        StringBuilder msg = new StringBuilder();
                        msg.append("line: " + line + ", column: " + column
                                + ", char: " + ch + ", in: \n");
                        for (int j = 0; j < padding * 2; j++) {
                            msg.append("=");
                        }
                        msg.append("\n");
                        msg.append("...");
                        msg.append(before);
                        msg.append(after);
                        msg.append("...");
                        msg.append("\n");
                        for (int j = 0; j < before.length() + 2; j++) {
                            msg.append(" ");
                        }
                        msg.append("^-here\n");
                        for (int j = 0; j < padding * 2; j++) {
                            msg.append("=");
                        }
                        msg.append("\n");
                        return msg.toString();
                    }
                    count++;
                }
            }
        } catch (Throwable t) {
        }
        return location;
    }

    public static String[] splitString(String str, String sdelimiter) {

        return splitString(str, sdelimiter, true);
    }

    public static String[] splitString(String str, String sdelimiter, boolean isRemoveEmptyEntries) {

        List<String> list = split(str, sdelimiter, isRemoveEmptyEntries);

        return list.toArray(new String[list.size()]);
    }

    public static List<String> split(String str, String sdelimiter, boolean isRemoveEmptyEntries) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str, sdelimiter);
        while (st.hasMoreElements()) {
            String entry = st.nextToken();
            if (isRemoveEmptyEntries && isNullOrWhiteSpace(entry)) continue;
            list.add(entry);
        }
        return list;
    }

    public static List<String> split(String str, String sdelimiter) {
        return split(str, sdelimiter, true);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrWhiteSpace(String str) {
        return isNullOrEmpty(str)
                || (str.length() > 0 && str.trim().length() == 0);
    }

    public static boolean isNotNullOrWhiteSpace(String str) {
        return !isNullOrWhiteSpace(str);
    }

    public static Date toDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return toDate(str, format);
    }

    public static Date toDate(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return toDate(str, format);
    }

    public static Date toCstDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
        Date dt = toDate(str, format);
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.HOUR_OF_DAY, -8);
        return c.getTime();
    }

    public static Date toDate(String str, DateFormat format) {
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
        }
        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static String joinString(String[] strs, int pos, int limit, String prefix, String suffix, String separate) {
        if (pos > strs.length - 1) {
            return joinString(strs, prefix, suffix, separate);
        }
        int size = pos + limit <= strs.length ? limit : strs.length - pos;
        String[] desStrs = new String[size];
        System.arraycopy(strs, pos, desStrs, 0, size);
        return joinString(desStrs, prefix, suffix, separate);
    }

    public static String joinString(String[] strs, String prefix, String suffix, String separate) {
        if (strs == null) {
            return null;
        }
        int length = strs.length;
        if (length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (strs.length >= 1) {
            if (!isNullOrEmpty(prefix))
                sb.append(prefix);
            sb.append(strs[0]);
            if (!isNullOrEmpty(suffix))
                sb.append(suffix);
        }
        for (int i = 1; i < length; i++) {
            if (isNullOrEmpty(strs[i])) {
                continue;
            }
            if (!isNullOrEmpty(separate))
                sb.append(separate);
            if (!isNullOrEmpty(prefix))
                sb.append(prefix);
            sb.append(strs[i]);
            if (!isNullOrEmpty(suffix))
                sb.append(suffix);
        }

        return sb.toString();
    }

    public static String joinString(List<String> strs, String prefix, String suffix, String separate) {
        if (strs == null) {
            return null;
        }
        int length = strs.size();
        if (length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (!strs.isEmpty()) {
            if (!isNullOrEmpty(prefix))
                sb.append(prefix);
            sb.append(strs.get(0));
            if (!isNullOrEmpty(suffix))
                sb.append(suffix);
        }
        for (int i = 1; i < length; i++) {
            if (isNullOrEmpty(strs.get(i))) {
                continue;
            }
            if (!isNullOrEmpty(separate))
                sb.append(separate);
            if (!isNullOrEmpty(prefix))
                sb.append(prefix);
            sb.append(strs.get(i));
            if (!isNullOrEmpty(suffix))
                sb.append(suffix);
        }

        return sb.toString();
    }

    public static String joinString(List<String> strs, String separate) {
        return joinString(strs, "", "", separate);
    }

    public static String joinString(List<String> strs) {
        return joinString(strs, "", "", ",");
    }

    public static String subString(String oriStr, int beginIndex, int len) {
        String str = "";
        int strlen = oriStr.length();
        beginIndex = beginIndex - 1;
        if (strlen <= beginIndex) {
            System.out.println("out of " + oriStr
                    + "'s length, please recheck!");
        } else if (strlen <= beginIndex + len) {
            str = oriStr.substring(beginIndex);
        } else {
            str = oriStr.substring(beginIndex, beginIndex + len);
        }
        return str;
    }

    public static String padLeft(String oriStr, int len, char alexin) {
        StringBuilder sb = new StringBuilder();
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                sb.append(alexin);
            }
        }
        sb.append(oriStr);
        return sb.toString();
    }

    public static String padRight(String oriStr, int len, char alexin) {
        StringBuilder sb = new StringBuilder();
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                sb.append(alexin);
            }
        }
        sb.append(oriStr);
        return sb.toString();
    }

    public static String joinString(String[] arr, String sdelimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1)
                sb.append(sdelimiter);
        }
        return sb.toString();
    }

    public static String joinString(String[] arr) {
        return joinString(arr, ",");
    }

    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String uuid() {

        return UUID.randomUUID().toString();
    }

    public static String removeHTMLTag(String htmlStr) {

        htmlStr = SCRIPT_PATTERN.matcher(htmlStr).replaceAll(""); //过滤script标签

        htmlStr = STYLE_PATTERN.matcher(htmlStr).replaceAll(""); //过滤style标签

        htmlStr = HTML_PATTERN.matcher(htmlStr).replaceAll(""); //过滤html标签

        return htmlStr; //返回文本字符串

    }

    public static String getChinese(String str) {
        return CHINESE_PATTERN.matcher(str).replaceAll("");
    }

    public static String removeNumber(String str) {
        return str.replaceAll("\\d+", "");
    }

    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static boolean isChinese(char c) {
        // 根据字节码判断
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    public static boolean isContainsChinese(String input) {

        char[] chars = input.toCharArray();
        for (char a : chars) {
            if (isChinese(a)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        // punctuation, spacing, and formatting characters
        return ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                // symbols and punctuation in the unified Chinese, Japanese and Korean script
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                // fullwidth character or a halfwidth character
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                // vertical glyph variants for east Asian compatibility
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                // vertical punctuation for compatibility characters with the Chinese Standard GB 18030
                || ub == Character.UnicodeBlock.VERTICAL_FORMS
                // ascii
                || ub == Character.UnicodeBlock.BASIC_LATIN;
    }

    private static boolean isUserDefined(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.NUMBER_FORMS
                || ub == Character.UnicodeBlock.ENCLOSED_ALPHANUMERICS
                || ub == Character.UnicodeBlock.LETTERLIKE_SYMBOLS
                || c == '\ufeff'
                || c == '\u00a0';

    }

    public static boolean isMessy(String str) {
        float chlength = 0;
        float count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(isPunctuation(c) || isUserDefined(c))) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
                chlength++;
            }
        }
        if (chlength == 0) {
            return false;
        }
        float result = count / chlength;
        return result > 0.3;
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String repeat(final String str, final int repeat) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return EMPTY;
        }
        final int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= PAD_LIMIT) {
            return repeat(str.charAt(0), repeat);
        }

        final int outputLength = inputLength * repeat;
        switch (inputLength) {
            case 1:
                return repeat(str.charAt(0), repeat);
            case 2:
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                final char[] output2 = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default:
                final StringBuilder buf = new StringBuilder(outputLength);
                for (int i = 0; i < repeat; i++) {
                    buf.append(str);
                }
                return buf.toString();
        }
    }

    public static String repeat(final String str, final String separator, final int repeat) {
        if (str == null || separator == null) {
            return repeat(str, repeat);
        }
        // given that repeat(String, int) is quite optimized, better to rely on it than try and splice this into it
        final String result = repeat(str + separator, repeat);
        return removeEnd(result, separator);
    }

    public static String removeEnd(final String str, final String remove) {
        if (isAnyEmpty(str, remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    public static String repeat(final char ch, final int repeat) {
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }

    public static String stripEnd(final String str, final String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != INDEX_NOT_FOUND) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    private static Map<String, String> parseKeyValuePair(String str, String itemSeparator) {
        String[] tmp = str.split(itemSeparator);
        Map<String, String> map = new HashMap<>(tmp.length);
        for (int i = 0; i < tmp.length; i++) {
            Matcher matcher = KVP_PATTERN.matcher(tmp[i]);
            if (!matcher.matches()) {
                continue;
            }
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }

    public static String getQueryStringValue(String qs, String key) {
        Map<String, String> map = StringUtils.parseQueryString(qs);
        return map.get(key);
    }

    public static Map<String, String> parseQueryString(String qs) {
        if (isEmpty(qs)) {
            return new HashMap<String, String>();
        }
        return parseKeyValuePair(qs, "\\&");
    }

    public static String toQueryString(Map<String, String> ps) {
        StringBuilder buf = new StringBuilder();
        if (ps != null && ps.size() > 0) {
            for (Map.Entry<String, String> entry : new TreeMap<String, String>(ps).entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (isNoneEmpty(key, value)) {
                    if (buf.length() > 0) {
                        buf.append("&");
                    }
                    buf.append(key);
                    buf.append("=");
                    buf.append(value);
                }
            }
        }
        return buf.toString();
    }

    public static String camelToSplitName(String camelName, String split) {
        if (isEmpty(camelName)) {
            return camelName;
        }
        StringBuilder buf = null;
        for (int i = 0; i < camelName.length(); i++) {
            char ch = camelName.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                if (buf == null) {
                    buf = new StringBuilder();
                    if (i > 0) {
                        buf.append(camelName.substring(0, i));
                    }
                }
                if (i > 0) {
                    buf.append(split);
                }
                buf.append(Character.toLowerCase(ch));
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        return buf == null ? camelName : buf.toString();
    }

    public static String replace(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(final String text, final String searchString, final String replacement, int max) {
        if (isAnyEmpty(text, searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == INDEX_NOT_FOUND) {
            return text;
        }
        final int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= max < 0 ? 16 : max > 64 ? 64 : max;
        final StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }
}
