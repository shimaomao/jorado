package com.jorado.search.hotword.service.exportimpl;

import com.jorado.search.hotword.model.Word;
import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.search.hotword.util.WordUtils;
import com.jorado.logger.util.IOUtils;
import com.jorado.logger.util.StringUtils;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by len.zhang on 2018/3/30.
 * 学校热词数据导出器
 */
@Description("学校热词数据导出器")
@Service
public final class SchoolWordExporter extends BaseWordExporter {

    private static final Map<String, String> REPLACE_WORD_DICT;

    private Map<String, Integer> checkRepeatDict = new HashMap<>();

    private boolean loadLocalData;

    private List<String> attachData;

    static {
        REPLACE_WORD_DICT = IOUtils.resourceToMap("dict/replaceword.txt");
    }

    public SchoolWordExporter() {
        this(new ArrayList<>(), true);
    }

    public SchoolWordExporter(List<String> attachData) {
        this(attachData, true);
    }

    public SchoolWordExporter(List<String> attachData, boolean loadLocalData) {
        if (null != attachData) {
            this.attachData = attachData;
        }
        this.loadLocalData = loadLocalData;
    }

    @Override
    public HotWordType getWordType() {
        return HotWordType.SCHOOL;
    }

    @Override
    protected List<Word> initWord() {

        List<Word> words = new ArrayList<>();

        List<String> lines = new ArrayList<>();

        if (this.loadLocalData) {
            lines.addAll(IOUtils.readResourceLines("dict/school.txt"));
        }

        if (null != this.attachData) {
            //追加附加数据
            lines.addAll(this.attachData);
        }

        if (lines.isEmpty()) {
            return words;
        }

        for (String line : lines) {

            String[] fileds = StringUtils.splitString(line, ",", false);

            if (fileds.length == 0) {
                continue;
            }

            String keyWord = fileds[0];

            keyWord = processWord(keyWord);

            if (StringUtils.isNullOrWhiteSpace(keyWord)) {
                continue;
            }

            Word word = assemblyWord(keyWord);

            //说明有相似词
            if (fileds.length > 1) {
                String field = fileds[1];
                String[] similarFileds = StringUtils.splitString(field, "\\|", true);
                for (String s : similarFileds) {
                    String sw = processWord(s);
                    if (StringUtils.isNullOrWhiteSpace(sw)) {
                        continue;
                    }
                    Word pw = assemblyWord(sw);
                    word.getSimilar().add(pw.getKeyWord());
                    word.getSimilar().add(pw.getPinYin());
                    word.getSimilar().add(pw.getPy());
                }
            }
            words.add(word);
        }

        return words;
    }

    /**
     * 数据预处理（去重、移除特殊符号、敏感词、黑名单、大小写转换、空格、全角转半角）
     * 数据格式 (关键字，点击次数，匹配行数，相似词|相似词|相似词)
     *
     * @param word
     * @return
     */
    private String processWord(String word) {

        word = WordUtils.removeSpecialChar(word);

        if (StringUtils.isNullOrWhiteSpace(word)) {
            return null;
        }

        if (word.length() < 2 || StringUtils.isMessy(word)) {
            return null;
        }

        if (word.length() <= 3 && !StringUtils.isContainsChinese(word)) {
            return null;
        }

        for (Map.Entry<String, String> w : BLACK_WORD_DICT.entrySet()) {
            if (word.contains(w.getKey())) {
                return null;
            }
        }

        for (Map.Entry<String, String> w : REPLACE_WORD_DICT.entrySet()) {
            if (!word.contains(w.getKey())) {
                continue;
            }
            word = word.replace(w.getKey(), REPLACE_WORD_DICT.get(w.getKey()));
        }

        if (checkRepeatDict.containsKey(word)) {
            return null;
        } else {
            checkRepeatDict.put(word, 1);
        }

        return word;
    }
}
