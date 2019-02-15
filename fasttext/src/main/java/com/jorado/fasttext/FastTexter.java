package com.jorado.fasttext;

import com.mayabot.mynlp.fasttext.FastText;
import com.mayabot.mynlp.fasttext.FloatStringPair;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 词向量的训练与应用
 * <p>
 * input_file              训练文件路径（必须）
 * output                  输出文件路径（必须）
 * lr                      学习率 default 0.05
 * lr_update_rate          学习率更新速率 default 100
 * dim                     训练的词向量维度 default 100
 * ws                      上下文窗口大小 default 5
 * epoch                   epochs 数量 default 5
 * min_count               最低出现的词频 default 5
 * word_ngrams             n-grams 设置
 * loss                    损失函数 {ns,hs,softmax} default ns
 * minn                    最小字符长度 default 3
 * maxn                    最大字符长度 default 6
 * thread                  线程数量 default 12
 * t                       采样阈值 default 0.0001
 * silent                  禁用 c++ 扩展日志输出 default 1
 * encoding                指定 input_file 的编码 default utf-8
 */
public class FastTexter {

    private static final String LABEL_FLAG = "__label__";

    private FastText fastText;

    public FastTexter(String modelFile) {

        this.fastText = FastText.loadModel(modelFile, true);
    }

    public List<Map.Entry<String, Float>> predict(Iterable<String> words) {
        return this.predict(10, words);
    }

    public List<Map.Entry<String, Float>> predict(int k, Iterable<String> words) {
        List<Map.Entry<String, Float>> results = new ArrayList<>();
        List<FloatStringPair> pairs = fastText.predict(words, k);
        for (FloatStringPair pair : pairs) {
            results.add(new AbstractMap.SimpleEntry<>(pair.second.replace(LABEL_FLAG, ""), pair.first));
        }
        return results;
    }

    public List<Map.Entry<String, Float>> predict(String... words) {
        return this.predict(10, words);
    }

    public List<Map.Entry<String, Float>> predict(int k, String... words) {
        List<String> list = new ArrayList<>();
        for (String s : words) {
            list.add(s);
        }
        return this.predict(k, list);
    }

    public List<Map.Entry<String, Float>> predictJobType(int k, Iterable<String> words) {

        List<FloatStringPair> pairs = fastText.predict(words, k);

        List<Map.Entry<String, Float>> results = new ArrayList<>();
        for (FloatStringPair pair : pairs) {
            results.add(new AbstractMap.SimpleEntry<>(DictUtils.getSubJobTypeName(pair.second.replace(LABEL_FLAG, "")), pair.first));
        }
        return results;
    }

    public List<Map.Entry<String, Float>> predictJobType(Iterable<String> words) {
        return predictJobType(10, words);
    }

    public List<Map.Entry<String, Float>> predictJobType(String... words) {
        return predictJobType(10, words);
    }

    public List<Map.Entry<String, Float>> predictJobType(int k, String... words) {
        List<String> list = new ArrayList<>();
        for (String s : words) {
            list.add(s);
        }
        return this.predictJobType(k, list);
    }

    public FastText getFastText() {
        return fastText;
    }
}
