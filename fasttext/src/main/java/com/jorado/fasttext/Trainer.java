package com.jorado.fasttext;

import com.hankcs.hanlp.corpus.io.IOUtil;
import com.jorado.fasttext.filter.SegmentFilter;
import com.jorado.logger.exception.CoreException;

import java.util.List;
import java.util.Map;

/**
 * 命令行训练
 */
public class Trainer {

    /**
     * java -jar fasttext.jar /data/train.txt /data/model
     *
     * @param args
     */
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
                testWord = "2019年应届毕业生（上海）,其他,银行,一、岗位要求：<br>（一）身心健康、工作踏实、责任心强，能吃苦耐劳，有一定抗压能力和风险意识，具备良好的职业道德操守和团队协作精神；<br>（二）全日制硕士及以上学历2019年应届毕业生，海外留学归国人员应有国家教育部颁发的国外学历学位认证证书；<br>（三）计算机、通信工程、信息安全、自动化、数学等计算机相关专业，或者金融、经济、统计等金融相关专业；<br>（四）曾在省部级及以上期刊、会议上发表过技术论文或参加过校级（含）以上计算机相关领域的技能竞赛并获得荣誉称号的优先考虑；具有相关领域方向的金融、IT实习经验者或获得相关IT认证证书者优先考虑；党员、学生干部或在校期间有相关社团活动组织经验者优先考虑。<br>二、工作方向：软件研发、大数据研发、大数据分析与处理、人工智能应用及系统、网络、应用运维管理等。<br>三、工作地点：上海<br>";
            }

            TrainerProxy trainerProxy = new TrainerProxy(trainFile, modelFile);

            FastTexter fastTexter = trainerProxy.train();

            FilterManager filterManager = new FilterManager();

            filterManager.addFilter(new SegmentFilter());

            String[] words = filterManager.filter(testWord).split(" ");
            List<Map.Entry<String, Float>> datas = fastTexter.predictJobType(words);
            for (Map.Entry<String, Float> entry : datas) {
                System.out.println(entry.getKey() + "->" + entry.getValue());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
