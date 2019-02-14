package com.jorado.lexicon.postag;

import com.jorado.lexicon.model.DictUnit;
import com.jorado.lexicon.model.NatureUnit;
import com.jorado.lexicon.postag.impl.AnsjRecognizer;
import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.ConvertUtils;
import com.jorado.logger.util.IOUtils;
import com.jorado.logger.util.StringUtils;

import java.util.*;

/**
 * 词性标注器
 */
public final class PosTagger {

    private static final PosTagger posTagger = new PosTagger(new AnsjRecognizer());

    private Recognizer recognizer;

    public PosTagger(Recognizer recognizer) {
        this.setRecognizer(recognizer);
    }

    public static PosTagger getDefault() {
        return posTagger;
    }

    public PosTagger setRecognizer(Recognizer recognizer) {
        this.recognizer = recognizer;
        this.addDefaultDict();
        return this;
    }

    /**
     * 获取词性识别结果
     *
     * @param content
     * @return
     */
    public List<NatureUnit> recognition(String content) {
        if (StringUtils.isNullOrWhiteSpace(content))
            return new ArrayList<>();
        //剔除特殊字符
        content = StringUtils.removeSpecialChar(content).replaceAll("\\s*", "");
        return recognizer.recognition(content);
    }

    /**
     * 获取所有词性标签
     *
     * @return
     */
    public Set<String> getTags() {
        return recognizer.getTags();
    }

    /**
     * 添加字典
     *
     * @param dictUnit
     */
    public PosTagger addDict(DictUnit... dictUnit) {

        for (DictUnit unit : dictUnit) {
            //剔除特殊字符
            String word = StringUtils.removeSpecialChar(unit.getWord()).replaceAll("\\s*", "");
            if (StringUtils.isNullOrWhiteSpace(word)) continue;
            unit.setWord(word);
            recognizer.addDict(unit);
        }
        return this;
    }

    /**
     * 添加字典文件
     * 文件格式>每行(词语separator词性separator词频)
     *
     * @param path
     * @param separator
     */
    public PosTagger addDict(String path, String separator) {
        List<String> datas = IOUtils.readResourceLines(path);
        for (String data : datas) {
            String[] arrs = data.split(separator);
            String term, nature = "-", freq = "0";
            term = arrs[0];
            if (StringUtils.isNullOrWhiteSpace(term)) continue;

            if (arrs.length > 1)
                nature = arrs[1];

            if (arrs.length > 2)
                freq = arrs[2];

            addDict(new DictUnit(term, nature, ConvertUtils.toInt(freq, 0)));
        }
        return this;
    }

    /**
     * 添加字典文件
     * 文件格式>每行(词语\t词性\t词频)
     *
     * @param paths
     */
    public PosTagger addDict(String... paths) {
        for (String p : paths) {
            this.addDict(p, "\t");
        }
        return this;
    }

    /**
     * 获取词性识别字典
     *
     * @param content
     * @return
     */
    public Map<String, Integer> getTagMap(String content) {
        Map<String, Integer> map = new HashMap<>();
        List<NatureUnit> ret = recognizer.recognition(content);
        for (NatureUnit nature : ret) {
            String t = nature.getNature();
            if (!map.containsKey(t))
                map.put(t, 1);
            else {
                map.put(t, map.get(t) + 1);
            }

        }
        return map;
    }

    /**
     * 获取词性识别最多的标签
     *
     * @param content
     * @return
     */
    public String getTopTag(String content) {

        Map<String, Integer> map = getTagMap(content);

        if (map.isEmpty()) return null;
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(list);
        return !list.isEmpty() ? list.get(0).getKey() : null;
    }

    /**
     * 判断是否为公司
     *
     * @param content
     * @return
     */
    public boolean isCompany(String content) {
        try {
            int companyFreq = 0;
            int positionFreq = 0;
            List<NatureUnit> ret = PosTagger.getDefault().recognition(content);
            for (NatureUnit nature : ret) {
                String t = nature.getNature();
                if (t.startsWith("com")) companyFreq++;
                else if (t.startsWith("jt")) positionFreq++;
            }
            return companyFreq > positionFreq;
        } catch (Exception ex) {
            EventClient.getDefault().createException("company recognition error", ex).addData("word", content).asyncSubmit();
            return false;
        }
    }

    private void addDefaultDict() {

        EventBuilder eb = EventClient.getDefault().createEvent();
        eb.addTags("lexicon");
        try {

            this.addDict("library/default.dic");
            this.addDict("library/position.txt");
            this.addDict("library/company.txt");

            eb.setMessage(String.format("Default dict load completed")).addData("tags", getTags());

        } catch (Exception e) {
            eb.setMessage(String.format("Default dict load error")).setException(e);

        } finally {
            eb.asyncSubmit();
        }
    }
}
