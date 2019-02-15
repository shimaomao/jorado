package com.jorado.search.hotword.service.exportimpl;

import com.jorado.dict.DictUtils;
import com.jorado.search.hotword.model.Word;
import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.search.hotword.util.WordUtils;

import com.jorado.logger.EventClient;
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
 * 公司热词数据导出器
 */
@Description("公司热词数据导出器")
@Service
public final class CompanyWordExporter extends BaseWordExporter {

    private static final Map<String, String> REPLACE_WORD_DICT;

    private static final Map<String, String> WRITELIST_DICT;

    private Map<String, Integer> checkRepeatDict = new HashMap<>();

    private boolean loadLocalData;

    private List<String> attachData;

    static {
        REPLACE_WORD_DICT = IOUtils.resourceToMap("dict/replaceword_company.txt");
        WRITELIST_DICT = IOUtils.resourceToMap("dict/writelist_company.txt");
    }

    public CompanyWordExporter() {
        this(new ArrayList<>(), true);
    }

    public CompanyWordExporter(List<String> attachData) {
        this(attachData, true);
    }

    public CompanyWordExporter(List<String> attachData, boolean loadLocalData) {
        if (null != attachData) {
            this.attachData = attachData;
        }
        this.loadLocalData = loadLocalData;
    }

    @Override
    public HotWordType getWordType() {
        return HotWordType.COMPANY;
    }

    @Override
    protected List<Word> initWord() {

        List<Word> words = new ArrayList<>();

        List<String> lines = new ArrayList<>();

        if (this.loadLocalData) {
            lines.addAll(IOUtils.readResourceLines("dict/company.txt"));
        }

        if (null != this.attachData) {
            //追加附加数据
            lines.addAll(this.attachData);
        }

        if (lines.isEmpty()) {
            return words;
        }

        for (String line : lines) {

            try {

                String[] fileds = StringUtils.splitString(line, ",", false);

                if (fileds.length == 0) {
                    continue;
                }

                Word word = null;
                Word word1 = null;
                Word word2 = null;

                //分公司处理
                String keyWord = fileds[0];
                keyWord = processWord(keyWord, true);
                if (!StringUtils.isNullOrWhiteSpace(keyWord)) {
                    word = assemblyWord(keyWord);
                }


                //公司简称处理
                if (fileds.length > 1) {
                    keyWord = fileds[1];
                    keyWord = processWord(keyWord, true);
                    if (!StringUtils.isNullOrWhiteSpace(keyWord)) {
                        word1 = assemblyWord(keyWord);
                    }
                }


                //总公司处理
                if (fileds.length > 2) {
                    keyWord = fileds[2];
                    keyWord = processWord(keyWord, true);
                    if (!StringUtils.isNullOrWhiteSpace(keyWord)) {
                        word2 = assemblyWord(keyWord);
                    }
                }

                //追加简称到分公司与总公司
                if (null != word1) {
                    words.add(word1);
                    if (null != word) {
                        word.getSimilar().add(word1.getKeyWord());
                        word.getSimilar().add(word1.getPinYin());
                        word.getSimilar().add(word1.getPy());
                    }
                    if (null != word2) {
                        word2.getSimilar().add(word1.getKeyWord());
                        word2.getSimilar().add(word1.getPinYin());
                        word2.getSimilar().add(word1.getPy());
                    }
                }


                if (null != word) {
                    words.add(word);
                }
                if (null != word2) {
                    words.add(word2);
                }
            } catch (Exception ex) {
                EventClient.getDefault().submitException(line, ex);
            }
        }
        for (String word : checkRepeatDict.keySet()) {
            if (word.contains("中国") || word.contains("香港")) {
                words.add(assemblyWord(word));
            }
        }

        for (String word : WRITELIST_DICT.keySet()) {
            if (!checkRepeatDict.containsKey(word)) {
                words.add(assemblyWord(word));
            }
        }

        return words;
    }

    /**
     * 数据预处理（去重、移除特殊符号、敏感词、黑名单、大小写转换、空格、全角转半角，去除城市名）
     * 数据格式 (关键字，相似词|相似词|相似词)
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

        int index = word.indexOf("公司");
        if (index > 0) {
            word = word.substring(0, index + 2);
        }

        if (removeCity) {
            word = removeCity(word);
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

        if (!StringUtils.isNullOrWhiteSpace(DictUtils.getLocationCode(word))) {
            return null;
        }

        if (word.equals("中国") || word.equals("公司") || word.equals("集团") || word.equals("祥云")) {
            return null;
        }

        if (checkRepeatDict.containsKey(word)) {
            return null;
        } else {
            checkRepeatDict.put(word, 1);
        }
        return word;
    }

    /**
     * 去除城市标记
     *
     * @param word
     * @return
     */
    private String removeCity(String word) {

        if (word.contains("航空") || word.contains("银行") || word.contains("证券")) {
            return word;
        }

        return DictUtils.removeCity(word, "中国,香港");
    }
}
