package position.wltea.analyzer.seg.service;

import position.wltea.analyzer.seg.model.WordModel;

/**
 * Created by len.zhang on 2018/9/17.
 * 自定义分词接口
 */
public interface SegService {

    /**
     * 智能分词
     *
     * @param input
     * @return
     */
    WordModel smartSegWord(String input);

    /**
     * 分词
     *
     * @param input
     * @return
     */
    WordModel segWord(String input);

}
