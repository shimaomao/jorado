package com.jorado.fasttext;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.mayabot.mynlp.fasttext.FastText;
import com.mayabot.mynlp.fasttext.FastTextTrain;
import com.mayabot.mynlp.fasttext.ModelName;
import com.mayabot.mynlp.fasttext.TrainArgs;
import com.jorado.fasttext.exception.InitModelException;
import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.logger.exception.CoreException;

import java.io.File;

/**
 * 训练器代理
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
 *
 */
public class TrainerProxy {

    private String modelFile;
    private String trainFile;
    private ModelName model;
    private TrainArgs trainArgs;
    private EventBuilder eventBuilder;

    public TrainerProxy(String trainFile, String modelFile) {
        this.eventBuilder = EventClient.getDefault().createEvent().addTags("fasttext");
        this.modelFile = modelFile;
        this.trainFile = trainFile;
        this.trainArgs = new TrainArgs();
        this.setLayerSize(200).setThreads(Runtime.getRuntime().availableProcessors()).useSupModel();
    }

    public TrainerProxy setModel(ModelName modelName) {

        this.model = modelName;
        return this;
    }

    public TrainerProxy useSgModel() {
        return this.setModel(ModelName.sg);
    }

    public TrainerProxy useSupModel() {
        return this.setModel(ModelName.sup);
    }

    public TrainerProxy useCbowModel() {
        return this.setModel(ModelName.cbow);
    }

    /**
     * 设置神经网络维度（默认200）
     *
     * @param layerSize
     * @return
     */
    public TrainerProxy setLayerSize(int layerSize) {
        this.trainArgs.setDim(layerSize);
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
        this.trainArgs.setWs(windowSize);
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
        this.trainArgs.setThread(threads);
        this.eventBuilder.addData("trainer.numThreads", threads);
        return this;
    }

    /**
     * 训练迭代次数，1个epoch等于使用训练集中的全部样本训练一次
     *
     * @param epoch
     * @return
     */
    public TrainerProxy setEpoch(int epoch) {
        this.trainArgs.setEpoch(epoch);
        this.eventBuilder.addData("trainer.epoch", epoch);
        return this;
    }

    /**
     * 开始训练
     *
     * @return 向量模型
     * @throws InitModelException
     */
    public FastTexter train() throws InitModelException {

        try {

            if (IOUtil.isFileExisted(this.modelFile + "/input.matrix")) {
                return new FastTexter(this.modelFile);
            }

            if (!IOUtil.isFileExisted(this.trainFile)) {
                throw new CoreException("Can not load valid train file!!!");
            }

            FastTextTrain trainer = new FastTextTrain();
            FastText fastText = trainer.train(new File(this.trainFile), this.model, this.trainArgs);
            fastText.saveModel(this.modelFile);
            this.eventBuilder.setMessage("Train over").asyncSubmit();
            return new FastTexter(this.modelFile);

        } catch (Exception ex) {
            this.eventBuilder.setException(ex).setMessage("Train error").asyncSubmit();
            throw new InitModelException(ex);
        }
    }
}
