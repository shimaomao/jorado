package com.jorado.lexicon.postag;

import com.jorado.logger.util.IOUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量测试
 * 自己跑自己
 */
public class PosTaggerSeftTest {

    @Test
    public void batchRecognition() {
        int com = 0;
        List<String> datas = IOUtils.readResourceLines("default.dic");
        System.out.println(datas.size());

        List<String> datas1 = new ArrayList<>();
        for (String data : datas) {
            String[] arrs = data.split("\t");
            String tag = arrs[1];
            if (tag.startsWith("com"))
                datas1.add(arrs[0]);
        }

        System.out.println(String.format("公司词量:%s", datas1.size()));

        for (String data : datas1) {

            if (isCompany(data)) {
                com++;
            }
        }

        System.out.println(datas1.size() - com);
    }

    private boolean isCompany(String content) {
        return PosTagger.getDefault().isCompany(content);
    }
}