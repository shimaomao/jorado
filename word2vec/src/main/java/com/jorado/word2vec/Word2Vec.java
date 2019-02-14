package com.jorado.word2vec;

import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.Vector;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import org.nlpcn.commons.lang.util.tuples.Triplet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 词向量的训练与应用
 *
 * -train 训练数据
 * -output 结果输入文件，即每个词的向量
 * -cbow 是否使用cbow模型，0表示使用skip-gram模型，1表示使用cbow模型，默认情况下是skip-gram模型，cbow模型快一些，skip-gram模型效果好一些
 * -size 表示输出的词向量维数
 * -window 为训练的窗口大小，8表示每个词考虑前8个词与后8个词（实际代码中还有一个随机选窗口的过程，窗口大小<=5)
 * -negative 表示是否使用NEG方，0表示不使用，其它的值目前还不是很清楚
 * -hs 是否使用HS方法，0表示不使用，1表示使用
 * -sample 表示 采样的阈值，如果一个词在训练样本中出现的频率越大，那么就越会被采样
 * -binary 表示输出的结果文件是否采用二进制存储，0表示不使用（即普通的文本存储，可以打开查看），1表示使用，即vectors.bin的存储类型
 *
 * 除了上面所讲的参数，还有：
 * -alpha 表示 学习速率
 * -min-count 表示设置最低频率，默认为5，如果一个词语在文档中出现的次数小于该阈值，那么该词就会被舍弃
 * -classes 表示词聚类簇的个数，从相关源码中可以得出该聚类是采用k-means
 *
 */
public class Word2Vec {

    private static final int SIZE = 20;

    /**
     * 模型对象
     */
    private WordVectorModel vectorModel;

    public Word2Vec(String modelFile) throws IOException {
        this(new WordVectorModel(modelFile));
    }

    public Word2Vec(WordVectorModel vectorModel) {
        this.vectorModel = vectorModel;
    }

    /**
     * 根据向量获取20个相似词
     *
     * @param vector
     * @return
     */
    public List<Map.Entry<String, Float>> nearest(Vector vector) {
        return this.nearest(vector, SIZE);
    }

    /**
     * 根据向量获取相似词，可以设置返回数量
     *
     * @param vector
     * @param size
     * @return
     */
    public List<Map.Entry<String, Float>> nearest(Vector vector, int size) {
        return this.vectorModel.nearest(vector, size);
    }

    /**
     * 获取与输入词相似的20个词
     *
     * @param word
     * @return
     */
    public List<Map.Entry<String, Float>> nearest(String word) {
        return this.nearest(word, SIZE);
    }

    /**
     * 获取与输入词相似的词，可以设置返回数量
     *
     * @param word
     * @return
     */
    public List<Map.Entry<String, Float>> nearest(String word, int size) {
        return this.vectorModel.nearest(word, size);
    }

    /**
     * 返回两个词的相似度
     *
     * @param what
     * @param with
     * @return
     */
    public float similarity(String what, String with) {
        return this.vectorModel.similarity(what, with);
    }

    /**
     * 返回两组词的相似度
     *
     * @param whats
     * @param withs
     * @return
     */
    public List<Triplet<String, String, Float>> similarity(String[] whats, String[] withs) {
        List<Triplet<String, String, Float>> results = new ArrayList<>();
        for (int i = 0, j = whats.length; i < j; i++) {
            Triplet<String, String, Float> tuple = new Triplet<>(whats[i], withs[i], this.vectorModel.similarity(whats[i], withs[i]));
            results.add(tuple);
        }
        return results;
    }

    /**
     * 返回两组词的相似度
     *
     * @param whats
     * @param withs
     * @return
     */
    public List<Triplet<String, String, Float>> similarity(Collection<String> whats, Collection<String> withs) {
        return similarity(whats.toArray(new String[whats.size()]), withs.toArray(new String[withs.size()]));
    }

    /**
     * 获取输入词的向量
     *
     * @param word
     * @return
     */
    public Vector vector(String word) {
        return this.vectorModel.vector(word);
    }

    /**
     * 根据输入文档获取相似文档
     *
     * @param document
     * @return
     */
    public List<Map.Entry<Integer, Float>> nearestDocument(String document) {
        DocVectorModel docVectorModel = new DocVectorModel(this.vectorModel);
        return docVectorModel.nearest(document);
    }

    /**
     * 根据输入文档获取相似文档
     *
     * @param document
     * @return
     */
    public List<Map.Entry<Integer, Float>> nearestDocument(String document, Collection<String> documents) {
        DocVectorModel docVectorModel = new DocVectorModel(this.vectorModel);
        int i = 0;
        for (String doc : documents) {
            docVectorModel.addDocument(i, doc);
            i++;
        }
        return docVectorModel.nearest(document);
    }

    /**
     * 获取向量模型
     *
     * @return
     */
    public WordVectorModel getVectorModel() {
        return vectorModel;
    }
}
