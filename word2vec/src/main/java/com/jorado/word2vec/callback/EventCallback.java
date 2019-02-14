package com.jorado.word2vec.callback;

import com.hankcs.hanlp.mining.word2vec.TrainingCallback;
import com.jorado.logger.EventClient;

/**
 * 基于日志的训练进度回调
 */
public class EventCallback implements TrainingCallback {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;
    private long timeStart;
    private int lastPrecent = 0;
    private int lastTrainingPrecent = 0;

    public EventCallback() {
        timeStart = System.currentTimeMillis();
    }

    public void corpusLoading(float percent) {
        int p = (int) percent;
        if (p > lastPrecent) {
            lastPrecent = p;
            EventClient.getDefault().asyncSubmitLog(String.format("加载训练语料：%.2f%%", percent));
        }
    }

    public void corpusLoaded(int vocWords, int trainWords, int totalWords) {
        EventClient.getDefault().asyncSubmitLog(String.format("词表大小：%d,训练词数：%d,语料词数：%d", vocWords, trainWords, totalWords));
    }

    public void training(float alpha, float progress) {
        int p = (int) progress;
        if (p > lastTrainingPrecent) {
            lastTrainingPrecent = p;
            String event = String.format("学习率：%.6f  进度：%.2f%%", alpha, progress);
            long timeNow = System.currentTimeMillis();
            long costTime = timeNow - timeStart + 1;
            progress /= 100;
            String etd = humanTime((long) (costTime / progress * (1.f - progress)));
            if (etd.length() > 0) event += String.format("  剩余时间：%s", etd);
            EventClient.getDefault().asyncSubmitLog(event);
        }
    }

    static String humanTime(long ms) {
        StringBuffer text = new StringBuffer();
        if (ms > DAY) {
            text.append(ms / DAY).append(" d ");
            ms %= DAY;
        }
        if (ms > HOUR) {
            text.append(ms / HOUR).append(" h ");
            ms %= HOUR;
        }
        if (ms > MINUTE) {
            text.append(ms / MINUTE).append(" m ");
            ms %= MINUTE;
        }
        if (ms > SECOND) {
            long s = ms / SECOND;
            if (s < 10) {
                text.append('0');
            }
            text.append(s).append(" s ");
        }
        return text.toString();
    }
}