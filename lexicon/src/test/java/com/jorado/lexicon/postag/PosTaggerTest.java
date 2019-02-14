package com.jorado.lexicon.postag;

import com.jorado.lexicon.model.NatureUnit;
import com.jorado.lexicon.postag.impl.HanlpRecognizer;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PosTaggerTest {

    String content = "自如网我知道自如中国人民东方雨虹新东方销售代表销售经理销售经理公司";

    @Test
    public void recognition() {
        content = "徐州盾安重工机械制造";
        List<NatureUnit> result = PosTagger.getDefault().recognition(content);
        for (NatureUnit s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void getTags() {
        Set<String> result = PosTagger.getDefault().getTags();
        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void getTagMap() {
        Map<String, Integer> result = PosTagger.getDefault().getTagMap(content);
        for (Map.Entry<String, Integer> s : result.entrySet()) {
            System.out.println(s.getKey() + "->" + s.getValue());
        }
    }

    @Test
    public void getTopTag() {
        String result = PosTagger.getDefault().getTopTag(content);
        System.out.println(result);
    }

    @Test
    public void isCompany() {
        boolean result = PosTagger.getDefault().isCompany("自如");
        System.out.println(result);
    }

    @Test
    public void isCompany1() {
        PosTagger posTagger = new PosTagger(new HanlpRecognizer());
        boolean result = posTagger.isCompany("自如");
        System.out.println(result);
    }
}