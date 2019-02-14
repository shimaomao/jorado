package position.wltea.analyzer.seg;

import position.wltea.analyzer.seg.model.WordModel;
import position.wltea.analyzer.seg.service.SegService;
import position.wltea.analyzer.seg.service.imp.SegServiceImpl;

import java.io.IOException;

/**
 * Created by len.zhang on 2018/9/17.
 */
public class SegMain {
    public static void main(String[] args) throws IOException {
        SegService wordSegService = new SegServiceImpl();
        WordModel wordModel = wordSegService.smartSegWord("欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!");
        System.out.println(wordModel.getWord());
        System.out.println(wordModel.getAlterword());
        System.out.println(wordModel.getContent());
    }
}
