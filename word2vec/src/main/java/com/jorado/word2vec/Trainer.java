package com.jorado.word2vec;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.jorado.logger.exception.CoreException;

import java.util.List;
import java.util.Map;

/**
 * 命令行训练
 */
public class Trainer {

    public static void main(String[] args) {

        try {

            if (args.length < 2) {
                throw new CoreException("Parameter not valid");
            }

            String trainFile = args[0];
            String modelFile = args[1];

            if (!IOUtil.isFileExisted(trainFile)) {
                throw new CoreException("Can not load valid train file!!!");
            }

            String testWord;

            if (args.length > 2) {
                testWord = args[2];
            } else {
                testWord = "电话销售";
            }

            TrainerProxy trainerProxy = new TrainerProxy(trainFile, modelFile);

            Word2Vec word2Vec = trainerProxy.setEventCallBack().setLayerSize(200).setCbow(true).train();

            FilterManager filterManager = new FilterManager();

            String word = filterManager.filter(testWord);

            List<Map.Entry<String, Float>> results = word2Vec.nearest(word, 20);

            for (Map.Entry entry : results) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
