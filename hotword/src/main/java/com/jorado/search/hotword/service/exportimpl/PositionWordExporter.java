package com.jorado.search.hotword.service.exportimpl;

import com.jorado.search.hotword.model.Word;
import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.search.hotword.util.WordUtils;
import com.jorado.basedata.BaseDataUtils;
import com.jorado.logger.util.ConvertUtils;
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
 * 职位热词数据导出器
 */
@Description("职位热词数据导出器")
@Service
public final class PositionWordExporter extends BaseWordExporter {

    private static final Map<String, String> REPLACE_WORD_DICT;

    private Map<String, Integer> checkRepeatDict = new HashMap<>();

    private boolean loadLocalData;

    private List<String> attachData;

    static {
        REPLACE_WORD_DICT = IOUtils.resourceToMap("dict/replaceword.txt");
    }

    public PositionWordExporter() {
        this(new ArrayList<>(), true);
    }

    public PositionWordExporter(List<String> attachData) {
        this(attachData, true);
    }

    public PositionWordExporter(List<String> attachData, boolean loadLocalData) {
        if (null != attachData) {
            this.attachData = attachData;
        }
        this.loadLocalData = loadLocalData;
    }

    @Override
    public HotWordType getWordType() {
        return HotWordType.POSITION;
    }

    @Override
    protected List<Word> initWord() {

        List<Word> words = new ArrayList<>();

        List<String> lines = new ArrayList<>();

        if (this.loadLocalData) {
            lines.addAll(IOUtils.readResourceLines("dict/position.txt"));
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

            keyWord = processWord(keyWord, false);

            if (StringUtils.isNullOrWhiteSpace(keyWord)) {
                continue;
            }

            Word word = assemblyWord(keyWord);

            //说明有点击次数
            if (fileds.length > 1) {
                String field = fileds[1];
                word.setClick(ConvertUtils.toInt(field));
            }

            //说明有匹配行数
            if (fileds.length > 2) {
                String field = fileds[2];
                word.setRows(ConvertUtils.toInt(field));
            }

            //说明有相似词
            if (fileds.length > 3) {
                String field = fileds[3];
                String[] similarFileds = StringUtils.splitString(field, "\\|", true);
                for (String s : similarFileds) {
                    String sw = processWord(s, false);
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
    private String processWord(String word, boolean removeCity) {

        word = WordUtils.removeSpecialChar(word);

        if (StringUtils.isNullOrWhiteSpace(word)) {
            return null;
        }

        for (Map.Entry<String, String> w : BLACK_WORD_DICT.entrySet()) {
            if (word.contains(w.getKey())) {
                return null;
            }
        }

        if (removeCity) {
            word = BaseDataUtils.removeCity(word);
        }

        for (Map.Entry<String, String> w : REPLACE_WORD_DICT.entrySet()) {
            if (!word.contains(w.getKey())) {
                continue;
            }
            word = word.replace(w.getKey(), REPLACE_WORD_DICT.get(w.getKey()));
        }

        if (word.length() < 2 || StringUtils.isMessy(word)) {
            return null;
        }

        if (word.length() <= 3 && !StringUtils.isContainsChinese(word)) {
            return null;
        }

        if (word.equals("公司") || word.equals("集团")) {
            return null;
        }

        if (checkRepeatDict.containsKey(word)) {
            return null;
        } else {
            checkRepeatDict.put(word, 1);
        }
        return word;
    }
}
