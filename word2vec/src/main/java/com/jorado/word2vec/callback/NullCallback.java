package com.jorado.word2vec.callback;

import com.hankcs.hanlp.mining.word2vec.TrainingCallback;


public class NullCallback implements TrainingCallback {

    @Override
    public void corpusLoading(float percent) {

    }

    @Override
    public void corpusLoaded(int vocWords, int trainWords, int totalWords) {

    }

    @Override
    public void training(float alpha, float progress) {

    }
}