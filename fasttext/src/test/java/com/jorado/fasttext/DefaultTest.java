package com.jorado.fasttext;

import com.jorado.fasttext.filter.DefaultFilter;
import com.jorado.fasttext.filter.SegmentFilter;
import com.jorado.logger.util.StringUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class DefaultTest {

    @Test
    public void test() {
        FilterManager filterManager = new FilterManager();
        filterManager.removeFilter(DefaultFilter.class);

    }

    @Test
    public void test1() {
        String trainFile = "d:/fasttext/train.txt";
        String modelFile = "d:/fasttext/model";
        FastTexter fastTexter;
        FilterManager filterManager;
        filterManager = new FilterManager();
        filterManager.addFilter(new SegmentFilter());
        TrainerProxy trainerProxy = new TrainerProxy(trainFile, modelFile);
        fastTexter = trainerProxy.train();

        String input = "工程力学 矿业大学  工程 力学  矿业大学";
        List<String> words = StringUtils.split(filterManager.filter(input), " ");

        List<Map.Entry<String, Float>> datas = fastTexter.predictJobType(words);
        for (Map.Entry<String, Float> entry : datas) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
    }

}