package com.jorado.word2vec;

import org.junit.Before;
import org.junit.Test;
import org.nlpcn.commons.lang.util.tuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultTest {

    private String trainFile = "d:/train.txt";
    private String modelFile = "d:/model1.txt";
    private Word2Vec word2Vec;

    @Before
    public void init() {
        TrainerProxy trainerProxy = new TrainerProxy(trainFile, modelFile);
        word2Vec = trainerProxy.setEventCallBack().setLayerSize(200).setCbow(true).train();
    }

    @Test
    public void word2Vec() {
        List<Map.Entry<String, Float>> results = word2Vec.nearest("java", 20);
        for (Map.Entry entry : results) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @Test
    public void word2Vec1() {

        float result = word2Vec.similarity("java工程师", "java软件工程师");

        System.out.println(result);

        result = word2Vec.similarity("java工程师", "电商客服");

        System.out.println(result);
    }

    @Test
    public void word2Vec2() {

        List<String> whats = new ArrayList<>();
        whats.add("java");
        whats.add("java工程师");
        whats.add("java测试");

        List<String> withs = new ArrayList<>();
        withs.add("java工程师");
        withs.add("java软件工程师");
        withs.add("java工程师");

        List<Triplet<String, String, Float>> result = word2Vec.similarity(whats, withs);
        for (Triplet<String, String, Float> t : result) {
            System.out.println(t.getValue0() + "-" + t.getValue1() + "-" + t.getValue2());
        }
    }
}