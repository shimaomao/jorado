package com.jorado.search.hotword.service.exportimpl;

import com.jorado.search.core.service.Exporter;
import com.jorado.search.hotword.model.Word;
import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.search.hotword.util.WordUtils;
import com.jorado.logger.util.EncryptUtils;
import com.jorado.logger.util.IOUtils;

import java.util.List;
import java.util.Map;

/**
 * 热词数据导出抽象类
 */
public abstract class BaseWordExporter implements Exporter<Word> {

    /**
     * 黑词字典
     */
    protected static final Map<String, String> BLACK_WORD_DICT;

    /**
     * 数据
     */
    private List<Word> words;

    /**
     * 静态初始化
     */
    static {
        BLACK_WORD_DICT = IOUtils.resourceToMap("dict/blackword.txt");
    }

    @Override
    public long count() {
        if (null == words) {
            words = initWord();
        }
        return words.size();
    }

    @Override
    public List<Word> listDatas(int start, int rows) {
        if (null == words) {
            words = initWord();
        }
        return words;
    }

    /**
     * 获取热词类型
     *
     * @return
     */
    public abstract HotWordType getWordType();

    /**
     * 初始化热词数据
     *
     * @return
     */
    protected abstract List<Word> initWord();

    /**
     * 拼装word
     *
     * @param input
     * @return
     */
    protected Word assemblyWord(String input) {

        Word word = new Word(input);
        word.setId(EncryptUtils.md5Encode(word.getKeyWord()));
        word.setFlag(getWordType().getValue());
        word.setPinYin(WordUtils.getPinYin(input));
        word.setPy(WordUtils.getPy(input));

        word.getSimilar().add(word.getKeyWord());
        word.getSimilar().add(word.getPinYin());
        word.getSimilar().add(word.getPy());

        return word;
    }
}
