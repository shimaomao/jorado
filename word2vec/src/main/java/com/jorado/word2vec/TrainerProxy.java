package com.jorado.word2vec;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.mining.word2vec.NeuralNetworkType;
import com.hankcs.hanlp.mining.word2vec.TrainingCallback;
import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.logger.exception.CoreException;
import com.jorado.word2vec.callback.DefaultCallback;
import com.jorado.word2vec.callback.EventCallback;
import com.jorado.word2vec.callback.NullCallback;
import com.jorado.word2vec.exception.InitModelException;

import java.io.IOException;

/**
 * 训练器代理
 *
 *
 *  -train 训练数据
 *  -output 结果输入文件，即每个词的向量
 *  -cbow 是否使用cbow模型，0表示使用skip-gram模型，1表示使用cbow模型，默认情况下是skip-gram模型，cbow模型快一些，skip-gram模型效果好一些
 *  -size 表示输出的词向量维数
 *  -window 为训练的窗口大小，8表示每个词考虑前8个词与后8个词（实际代码中还有一个随机选窗口的过程，窗口大小<=5)
 *  -negative 表示是否使用NEG方，0表示不使用，其它的值目前还不是很清楚
 *  -hs 是否使用HS方法，0表示不使用，1表示使用
 *  -sample 表示 采样的阈值，如果一个词在训练样本中出现的频率越大，那么就越会被采样
 *  -binary 表示输出的结果文件是否采用二进制存储，0表示不使用（即普通的文本存储，可以打开查看），1表示使用，即vectors.bin的存储类型
 *
 *  除了上面所讲的参数，还有：
 *  -alpha 表示 学习速率
 *  -min-count 表示设置最低频率，默认为5，如果一个词语在文档中出现的次数小于该阈值，那么该词就会被舍弃
 *  -classes 表示词聚类簇的个数，从相关源码中可以得出该聚类是采用k-means
 *
 */
public class TrainerProxy {

    private String modelFile;
    private String trainFile;
    private Word2VecTrainer trainer;
    private EventBuilder eventBuilder;

    public TrainerProxy(String trainFile, String modelFile) {
        this.eventBuilder = EventClient.getDefault().createEvent().addTags("word2vec");
        this.modelFile = modelFile;
        this.trainFile = trainFile;
        this.trainer = new Word2VecTrainer();
        this.setCallBack(new NullCallback()).setCbow(false);
    }

    /**
     * 设置训练回调
     *
     * @param callBack
     * @return
     */
    public TrainerProxy setCallBack(TrainingCallback callBack) {
        this.trainer.setCallback(callBack);
        this.eventBuilder.addData("trainer.callback", callBack.getClass().getName());
        return this;
    }

    /**
     * 设置默认训练回调（基于控制台打印）
     *
     * @return
     */
    public TrainerProxy setDefaultCallBack() {
        return this.setCallBack(new DefaultCallback());
    }

    /**
     * 设置训练回调（基于日志打印）
     *
     * @return
     */
    public TrainerProxy setEventCallBack() {
        return this.setCallBack(new EventCallback());
    }

    /**
     * 设置神经网络维度（默认200）
     *
     * @param layerSize
     * @return
     */
    public TrainerProxy setLayerSize(int layerSize) {
        this.trainer.setLayerSize(layerSize);
        this.eventBuilder.addData("trainer.layerSize", layerSize);
        return this;
    }

    /**
     * 设置窗口大小（默认=5）
     *
     * @param windowSize
     * @return
     */
    public TrainerProxy setWindowSize(int windowSize) {
        this.trainer.setWindowSize(windowSize);
        this.eventBuilder.addData("trainer.windowSize", windowSize);
        return this;
    }

    /**
     * 设置线程数（默认=核数）
     *
     * @param threads
     * @return
     */
    public TrainerProxy setThreads(int threads) {
        this.trainer.useNumThreads(threads);
        this.eventBuilder.addData("trainer.numThreads", threads);
        return this;
    }

    /**
     * 设置神经网络类型，默认使用skip_gram
     *
     * @param cbow
     * @return
     */
    public TrainerProxy setCbow(boolean cbow) {
        if (!cbow) {
            this.trainer.type(NeuralNetworkType.SKIP_GRAM);
            this.eventBuilder.addData("trainer.neuralNetworkType", "SKIP_GRAM");
        } else {
            this.trainer.type(NeuralNetworkType.CBOW);
            this.eventBuilder.addData("trainer.neuralNetworkType", "CBOW");
        }
        return this;
    }

    /**
     * 设置最小词频（默认=5，取值范围0-100）
     *
     * @param minCount
     * @return
     */
    public TrainerProxy setMinCount(int minCount) {
        this.trainer.setMinVocabFrequency(minCount);
        this.eventBuilder.addData("trainer.minCount", "minCount");
        return this;
    }

    /**
     * 开始训练
     *
     * @return 向量模型
     * @throws InitModelException
     */
    public Word2Vec train() throws InitModelException {
        try {

            if (IOUtil.isFileExisted(this.modelFile)) {
                return new Word2Vec(this.modelFile);
            }

            if (!IOUtil.isFileExisted(this.trainFile)) {
                throw new CoreException("Can not load valid train file!!!");
            }

            WordVectorModel model = this.trainer.train(this.trainFile, this.modelFile);
            this.eventBuilder.setMessage("Train over").asyncSubmit();
            return new Word2Vec(model);

        } catch (IOException ex) {
            this.eventBuilder.setException(ex).setMessage("Train error").asyncSubmit();
            throw new InitModelException(ex);
        }
    }
}
