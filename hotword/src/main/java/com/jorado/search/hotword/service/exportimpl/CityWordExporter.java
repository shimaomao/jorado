package com.jorado.search.hotword.service.exportimpl;

import com.jorado.search.hotword.model.Word;
import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.basedata.BaseDataUtils;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Created by len.zhang on 2018/3/30.
 * 城市热词数据导出器
 */
@Description("城市热词数据导出器")
@Service
public final class CityWordExporter extends BaseWordExporter {

    @Override
    public HotWordType getWordType() {
        return HotWordType.CITY;
    }

    @Override
    protected List<Word> initWord() {
        List<Word> words = new ArrayList<>();
        if (null == BaseDataUtils.getLocationMap()) {
            return words;
        }
        for (Map.Entry<String, String> entry : BaseDataUtils.getLocationMap().entrySet()) {
            words.add(assemblyWord(entry.getKey()));
        }
        return words;
    }
}
