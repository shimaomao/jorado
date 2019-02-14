package com.jorado.search.hotword.util;

import com.jorado.logger.util.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import static com.jorado.logger.util.StringUtils.isChinese;

/**
 * 字词处理工具类
 */
public final class WordUtils {

    private WordUtils() {

    }

    /**
     * 拼音校准
     *
     * @param input
     * @return
     */
    private static String pinYinAdjust(String input) {
        String ss = input;
        if (ss.contains("琻")) {
            ss = ss.replace("琻", "jin");
        }
        if (ss.contains("凪")) {
            ss = ss.replace("凪", "zhi");
        }
        if (ss.contains("汼")) {
            ss = ss.replace("汼", "niu");
        }
        if (ss.contains("灜")) {
            ss = ss.replace("灜", "ying");
        }
        if (ss.contains("柜")) {
            ss = ss.replace("柜", "gui");
        }
        if (ss.contains("畜")) {
            ss = ss.replace("畜", "xu");
        }
        if (ss.contains("铣")) {
            ss = ss.replace("铣", "xi");
        }
        if (ss.contains("轧")) {
            ss = ss.replace("轧", "zha");
        }
        if (ss.contains("飞行员")) {
            ss = ss.replace("行", "xing");
        }
        if (ss.contains("模具")) {
            ss = ss.replace("模", "mu");
        }
        if (ss.contains("冷藏")) {
            ss = ss.replace("藏", "cang");
        }
        if (ss.contains("美的")) {
            ss = ss.replace("的", "di");
        }
        if (ss.contains("重庆")) {
            ss = ss.replace("重庆", "chongqing");
        }
        if (ss.contains("长沙")) {
            ss = ss.replace("长沙", "changsha");
        }
        if (ss.contains("校对") || ss.contains("校直") || ss.contains("校准")) {
            ss = ss.replace("校", "jiao");
        }
        if (ss.contains("乐师") || ss.contains("音乐") || ss.contains("配乐")) {
            ss = ss.replace("乐", "yue");
        }
        if (ss.contains("会计") || ss.contains("财会")) {
            ss = ss.replace("会", "kuai");
        }
        if (ss.contains("分行") || ss.contains("投行") || ss.contains("行长") || ss.contains("行员")
                || ss.contains("银行") || ss.contains("行业") || ss.contains("支行")) {
            ss = ss.replace("行", "hang");
        }
        if (ss.contains("空调") || ss.contains("调剂") || ss.contains("调酒") || ss.contains("调色")
                || ss.contains("调试") || ss.contains("调音") || ss.contains("协调") || ss.contains("装调")
                || ss.contains("调温") || ss.contains("调解") || ss.contains("调墨")
                || ss.contains("烹调") || ss.contains("调制")) {
            ss = ss.replace("调", "tiao");
        }

        return ss;
    }

    /**
     * 生成全拼
     *
     * @param input
     * @return
     */
    public static String getPinYin(String input) {
        input = pinYinAdjust(input);
        char[] t1;
        String[] t2;
        t1 = input.toCharArray();
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (isChinese(t1[i])) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4.append(t2[0]);
                } else {
                    t4.append(Character.toString(t1[i]));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4.toString();
    }

    /**
     * 生成首字母简拼
     *
     * @param input
     * @return
     */
    public static String getPy(String input) {
        input = pinYinAdjust(input);
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < input.length(); j++) {
            char ch = input.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(ch);
            }
        }
        return convert.toString();
    }

    public static String removeSpecialChar(String s) {
        s = StringUtils.removeSpecialChar(s);
        s = StringUtils.removeNumber(s);
        s = StringUtils.toDBC(s.toLowerCase()).trim();
        return StringUtils.clearBlank(s);
    }

    public static void main(String[] args) {
        String word = "123456aaa";
        String py = removeSpecialChar(word);
        System.out.println(py);
    }
}
